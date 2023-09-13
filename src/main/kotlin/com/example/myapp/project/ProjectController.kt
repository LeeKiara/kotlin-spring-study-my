package com.example.myapp.project;

import com.example.myapp.auth.Auth
import com.example.myapp.auth.AuthProfile
import com.example.myapp.auth.Members
import com.example.myapp.team.ProjectTeamMemberRequest
import com.example.myapp.team.ProjectTeamMembers
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Connection
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Tag(name="프로젝트 관리 처리 API")
@RestController
@RequestMapping("/project")
class ProjectController {

    // exposed selectAll -> List<ResultRow>
    // ResultRow는 transaction {} 구문 밖에서 접근 불가능함
    // transaction 구분 외부로 보낼 때는 별도의 객체로 변환해서 내보낸다.
    // 결과값: List<PostResponse>
    @Auth
    @GetMapping("/fetch/{pid}")
    fun fetch(@PathVariable pid: Long, @RequestAttribute authProfile: AuthProfile)
    = transaction {
        // object의 이름을 짧은걸로 변경
        val p = Projects

        Projects
            .select { p.pid eq pid }
            .orderBy(p.pid to SortOrder.DESC)
            .map { r -> ProjectResponse(
                r[p.pid],
                r[p.title],
                r[p.description],
                r[p.startDate].toString(),
                r[p.endDate].toString(),
                r[p.image],
                r[p.status],
                r[p.creatorUser],
                r[p.createdTime].toString()
            )
        }
    }

    // 프로젝트id로 프로젝트 정보 가져오기
    // GET /project/1
    @Operation(summary = "프로젝트 id로 프로젝트 정보 가져오기", security = [SecurityRequirement(name = "bearer-key")])
    @Auth
    @GetMapping("/{pid}")
    fun getProject(@PathVariable pid: Long, @RequestAttribute authProfile: AuthProfile)
    : ResponseEntity<MutableMap<String, Any?>> = transaction() {

        println("<<< ProjectController.getProject >>>")
        println("입력 값 확인")
        println(pid)

        // object의 이름을 짧은걸로 변경
        val p = Projects
        val m = Members

        // SQL :: where 조건 = pid가 동일한 프로젝트 정보
        // SELECT pid, title, description, start_date, end_date, image, status, creator_user, created_time, m.mname
        //  FROM project p inner join member m on p.creator_user = m.id
        //  where pid = 11;
        val result = Projects
            .join(Members, JoinType.INNER, onColumn = Projects.creatorUser, otherColumn = Members.id)
            .slice(p.pid, p.title, p.description, p.startDate, p.endDate, p.image, p.status, p.creatorUser, p.createdTime, m.mname)
            .select { p.pid eq pid }
            .map {
                r -> ProjectResponse(
                r[p.pid],
                r[p.title],
                r[p.description],
                r[p.startDate].toString(),
                r[p.endDate].toString(),
                r[p.image],
                r[p.status],
                r[p.creatorUser],
                r[p.createdTime].toString(),
                r[m.mname]
                )
            }

        val record = result.first()

        println(record)
        println(record.creatorUser)

        val res = mutableMapOf<String, Any?>()

        run {
            // 로그인한 유저가 프로젝트 생성자일 경우
            if(record.creatorUser == authProfile.id) {
                res["role"] = "modify"
                res["role-project"] = "CRUD"
            }
            res["data"] = result
            res["message"] = "FOUND"

            println("** res result **")
            println(res)
            return@transaction ResponseEntity.status(HttpStatus.OK).body(res)
        }
    }

    @Auth
    @PostMapping()
    fun create(@RequestBody request: ProjectCreateRequest,
               @RequestAttribute authProfile: AuthProfile) :
    ResponseEntity<Map<String, Any?>> {

        println("<<< ProjectController >>>")
        println("입력 값 확인")
        println(request.title+","+request.description+","+request.startDate+","+request.endDate)

        // 자바
        // Map<String, Object>
        // Object: nullable, int/long primitive 타입은 안 됨, Integer, Long

        // 코틀린
        // Map<String, Any?>
        // {"key" to null} -> Map<String, Student?>1q
        // {"key" to student} -> Map<String, Student>
        // {"key" to "str"} -> Map<String, String>
        // {"key" to 0L} -> Map<String, Long>
        // Java: Object, class들의 최상위 클래스
        if(!request.validate()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mapOf("error" to "title, startDate, endDate fields are required"))
        }

        // MySQL에서의 날짜 형식에 맞는 DateTimeFormatter 생성
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val (result, response) = transaction {
            val result = Projects.insert { it ->
                it[title] = request.title
                it[description] = request.description
                it[startDate] = LocalDate.parse(request.startDate, formatter)   // String을 LocalDateTime으로 변환
                it[endDate] = LocalDate.parse(request.endDate, formatter)
                it[image] = request.image.toString()
                it[status] = "1"
                it[creatorUser] = authProfile.id
                it[createdTime] = LocalDateTime.now()
            }.resultedValues
                ?: return@transaction Pair(false, null) // ex) Pair(결과타입, 결과객체)

            val record = result.first()
            return@transaction Pair(
                true,record
            )
        }

        // insert를 성공하면
        if(result) {
            return ResponseEntity
                .status(HttpStatus.CREATED).body(mapOf("data" to response))
        }

        // 에러 발생 하면
        return  ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(mapOf("data" to response, "error" to "conflict"))
    }

        // 프로젝트 전체 조회(리스트)
    // GET /project/list-all
    @Operation(summary = "프로젝트 전체 조회(리스트)", security = [SecurityRequirement(name = "bearer-key")])
    @Auth
    @GetMapping("/list-all")
    fun getProjectList() {

    }

    // Connection.TRANSACTION ---------------------------------
    // READ_COMMMITED, REPEATABLE_READ
    // 전체조회중 50건정도
    // 누군가 50건 중에 수정중이거나, 삭제중이거나
    // SELECT 잠시 wait

    // Mission Critical 서비스
    // 금융권, 의료, 제조..., 데이터 정확 잘 맞아야 되는 곳

    // TRANSACTION_READ_UNCOMMITTED
    // insert/update/read 트랜잭션 시장
    // 트랜잭션이 커밋이 안 되어도 조회가 가능
    // insert/update/delete 트랜잭션 상관없이 조회 가능

    // 프로젝트 전체 조회(페이지 단위)
    // GET  /project/paging/search?size=10&page=0&gubun="myproject"&keyword="제목"
    @Operation(summary = "프로젝트 전체 조회(페이지 단위)", security = [SecurityRequirement(name = "bearer-key")])
    @Auth
    @GetMapping("/paging/search")
    fun getProjectPaging(@RequestParam page: Int,
                         @RequestParam size: Int,
                         @RequestParam gubun : String?,
                         @RequestParam keyword : String?,
                         @RequestAttribute authProfile: AuthProfile)
                        : Page<ProjectResponse> = transaction(
                        Connection.TRANSACTION_READ_UNCOMMITTED, readOnly = true)
    {

        println("<<ProjectController.getProjectPaging>>")
        println("** 입력값 확인 ")
        println(" page=${page}, size=${size}, gubun=${gubun}, keyword=${keyword}" )


        // object의 이름을 짧은걸로 변경
        val p = Projects

        // 검색 조건 생성
        val query = when {
            gubun == "myproject" -> p.select { p.creatorUser eq authProfile.id }
            keyword != null -> p.select {
                (p.title like "%${keyword}%") or (p.description like "%${keyword}%" ) }
            else -> p.selectAll()
        }


        // 페이징 조회
        val content = query
            .orderBy(Projects.pid to SortOrder.DESC)
            .limit(size, offset = (size * page).toLong())
            .map {
                r -> ProjectResponse(
                    r[p.pid],
                    r[p.title],
                    r[p.description],
                    r[p.startDate].toString(),
                    r[p.endDate].toString(),
                    r[p.image],
                    r[p.status],
                    r[p.creatorUser],
                    r[p.createdTime].toString()
                )
            }

        // 전체 카운트
        val totalCount = Projects.selectAll().count()

        return@transaction PageImpl(
            content, // Page<ProjectResponse> (컬렉션)
            PageRequest.of(page, size), // Pageable
            totalCount // 전체 건수
        )
    }

    @Auth
    @GetMapping("/list")
    fun getProjectList(@RequestAttribute authProfile: AuthProfile)
            : Page<ProjectResponse> = transaction(
        Connection.TRANSACTION_READ_UNCOMMITTED, readOnly = true)
    {

        // object의 이름을 짧은걸로 변경
        val p = Projects

        // 페이징 조회
        val content = Projects
            .selectAll()
            .orderBy(Projects.pid to SortOrder.DESC)
            .map {
                    r -> ProjectResponse(
                r[p.pid],
                r[p.title],
                r[p.description],
                r[p.startDate].toString(),
                r[p.endDate].toString(),
                r[p.image],
                r[p.status],
                r[p.creatorUser],
                r[p.createdTime].toString()
            )
            }

        return@transaction PageImpl(
            content // Page<ProjectResponse> (컬렉션)
        )
    }

    // 상태값에 따른 프로젝트 조회(리스트)
    // GET /project/list-status?status=1
    @Auth
    @GetMapping("/list-status")
    fun getProjectListByStatus(@RequestParam status: String,
                               @RequestAttribute authProfile: AuthProfile): List<ProjectResponse> =
        transaction(Connection.TRANSACTION_READ_UNCOMMITTED, readOnly = true) {

            // object의 이름을 짧은걸로 변경
            val p = Projects

            // 조회
            val contents = Projects
                .select { p.status eq status }
                .orderBy(p.pid to SortOrder.DESC)
                .map {
                        r -> ProjectResponse(
                    r[p.pid],
                    r[p.title],
                    r[p.description],
                    r[p.startDate].toString(),
                    r[p.endDate].toString(),
                    r[p.image],
                    r[p.status],
                    r[p.creatorUser],
                    r[p.createdTime].toString()
                )
            }

            return@transaction contents
    }

    @Auth
    @PutMapping("/{pid}")
    fun modify(@PathVariable pid : Long,
               @RequestBody request: ProjectModifyRequest,
               @RequestAttribute authProfile: AuthProfile): ResponseEntity<Any> {

        println("<< ProjectController.modiy >>")
        println("-- 입력값 확인 : $request")

        // 필요한 request 값이 빈값이면 400 : Bad request
        if(!request.validate()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mapOf("message" to "title or content are required"))
        }

        val p = Projects

        // id에 해당 레코드가 없으면 404
        transaction {
            p.select{ (p.pid eq pid) }.firstOrNull()
        } ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        // MySQL에서의 날짜 형식에 맞는 DateTimeFormatter 생성
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        transaction {
            p.update({ p.pid eq pid }) {
                // 값이 존재하면 수정
                if(request.title.isNotEmpty()) {
                    it[title] = request.title
                }
                if(!request.description.isNullOrEmpty()) {
                    it[description] = request.description
                }
                if(request.startDate.isNotEmpty()) {
                    it[startDate] = LocalDate.parse(request.startDate, formatter)   // String을 LocalDateTime으로 변환
                }
                if(request.endDate.isNotEmpty()) {
                    it[endDate] = LocalDate.parse(request.endDate, formatter)   // String을 LocalDateTime으로 변환
                }
                if(!request.image.isNullOrEmpty()) {
                    it[image] = request.image
                }
                if(request.status.isNotEmpty()) {
                    it[status] = request.status
                }
            }
        }

        return ResponseEntity.ok().build();
    }

    // 내가 참여한 프로젝트 조회
    // 결과값: List<PostResponse>
    //      exposed selectAll -> List<ResultRow>
    //       ResultRow는 transaction {} 구문 밖에서 접근 불가능함
    //       transaction 구분 외부로 보낼 때는 별도의 객체로 변환해서 내보낸다.
    // GET /project/join
    @Operation(summary = "내가 참여한 프로젝트 조회", security = [SecurityRequirement(name = "bearer-key")])
    @Auth
    @GetMapping(value = ["/join"])
    fun getJoinProject(@RequestBody request: ProjectTeamMemberRequest,
                       @RequestAttribute authProfile: AuthProfile)
            =  transaction(Connection.TRANSACTION_READ_UNCOMMITTED, readOnly = true) {

        println("<<< ProjectController.getProject >>>")
        println("입력 값 확인")
        println("pid : ${request.pid}, mid:${request.mid} ")

        // object의 이름을 짧은걸로 변경
        val p = Projects
        val ptm = ProjectTeamMembers

        // SQL :: where 조건 = pid가 동일한 프로젝트 정보
//        SELECT p.pid, title, description, start_date, end_date, image, status, creator_user, p.created_time,
//        ptm.mid
//        FROM project p inner join project_team_member ptm on p.pid = ptm.pid
//        where ptm.mid = 1;
        val result = Projects
            .join(ProjectTeamMembers, JoinType.INNER, onColumn = Projects.pid, otherColumn = ProjectTeamMembers.pid)
            .slice(p.pid, p.title, p.description, p.startDate, p.endDate, p.image, p.status, p.creatorUser, p.createdTime,
                ptm.mid)
            .select { ptm.mid eq request.mid }
            .map {
                    r -> ProjectResponse(
                r[p.pid],
                r[p.title],
                r[p.description],
                r[p.startDate].toString(),
                r[p.endDate].toString(),
                r[p.image],
                r[p.status],
                r[p.creatorUser],
                r[p.createdTime].toString(),
                r[ptm.mid].toString()
            )
            }
    }
}
