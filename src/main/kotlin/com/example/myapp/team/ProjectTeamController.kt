package com.example.myapp.team

import com.example.myapp.auth.Auth
import com.example.myapp.auth.AuthProfile
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/project/member")
class ProjectTeamController {

    // 프로젝트 팀 멤버 조회 : GET project/member
    @Auth
    @GetMapping
    fun getProjectTeam(@RequestParam pid: Long, @RequestParam mid: Long
                       , @RequestAttribute authProfile: AuthProfile)
    = transaction(){

        println("<< ProjectTeamController.getProjectTeam >>")
        println("-- 입력값 확인 : pid=$pid, mid=$mid")

        val ptm = ProjectTeamMembers

        ProjectTeamMembers
            .select( where = (ptm.pid eq pid) and (ptm.mid eq mid) )
            .orderBy( ptm.ptid to SortOrder.DESC)
            .map {
                ProjectTeamMemberResponse(
                    it[ptm.ptid],
                    it[ptm.pid],
                    it[ptm.mid],
                    it[ptm.createdTime].toString()
                ) }
    }
}
