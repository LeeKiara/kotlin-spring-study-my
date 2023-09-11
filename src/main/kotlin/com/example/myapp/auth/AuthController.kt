package com.example.myapp.auth

import com.example.myapp.auth.util.HashUtil
import com.example.myapp.auth.util.JwtUtil
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/auth")
class AuthController(private val service: AuthService) {
//    @PostMapping(value = ["/signup"])
//    fun signUp(@RequestBody req: SignupRequest): ResponseEntity<Long> {
//        println(req)
//
//        // 1. Validation
//        // 입력값 검증
//        // 패스워드없거나, 닉네임, 이메일 없음...
//        // 필수값은 SingupRequest에서 자동으로 검증
//
//        // 2. Buisness Logic(데이터 처리)
//        // profile, login 생성 트랜잭션 처리
//        val profileId = service.createIdentity(req)
//        if(profileId > 0) {
//            // 3. Response
//            // 201: created
//            return ResponseEntity.status(HttpStatus.CREATED).body(profileId)
//        } else {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(profileId)
//        }
//    }
    @PostMapping(value = ["/signup"])
    fun signUp(@RequestBody req: SignupRequest): ResponseEntity<Long> {
        println(req)
        // 기존에 있는 계정인지 확인
        val record = transaction {
            Members.select{
                Members.username eq req.username
            }.singleOrNull()
        }
        if(record != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(0)
        }

        val secret = HashUtil.createHash(req.password)

        val memberId = transaction {
            // transaction 내부에서 예외처리 발생하면 자동 rollback
            // 기본적으로 auto-commit

            // try-catch 구문이 transaction 내부에 있으면
            // 예외처리 발생시에 catch로 가버림
            // transaction 함수에서는 예외처리 발생하 않은것으로 봄
            // 수동으로 catch 구문에서 rollback() 처리 해줘야 함

            // 1. Member 정보를 insert
            val memberId = Members.insertAndGetId {
                it[this.username] = req.username
                it[this.secret] = secret
                it[this.mname] = req.mname
                it[this.email] = req.email
            }

            return@transaction memberId.value
        }

        // 3. Response -> 201: created
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId)

    }

    //1. (브라우저) 로그인 요청
    // [RequestLine]
    //   HTTP 1.1 POST 로그인주소
    // [RequestHeader]
    //   content-type: www-form-urlencoded
    // [Body]
    //   id=...&pw=...
    //2. (서버) 로그인 요청을 받고 인증처리 후 쿠키 응답 및 웹페이지로 이동
    // HTTP Status 302 (리다이렉트)
    // [Response Header]
    //   Set-Cookie: 인증키=키........; domain=.naver.com
    //   Location: "리다이렉트 주소"
    //3. (브라우저) 쿠키를 생성(도메인에 맞게)
    @PostMapping(value = ["/signin"])
    fun signIn(
        @RequestParam username: String,
        @RequestParam password: String,
        res: HttpServletResponse,
    ): ResponseEntity<*> {
        println(username)
        println(password)

        val (result, message) = service.authenticate(username, password)
        if(result) {
            // 3. cookie와 헤더를 생성한후 리다이렉트
            val cookie = Cookie("token", message)
            cookie.path = "/"
            cookie.maxAge = (JwtUtil.TOKEN_TIMEOUT / 1000L).toInt() // 만료시간
            cookie.domain = "localhost" // 쿠키를 사용할 수 있 도메인

            // 응답헤더에 쿠키 추가
            res.addCookie(cookie)

            // 웹 첫페이지로 리다이렉트
            return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(
                    ServletUriComponentsBuilder
                        .fromHttpUrl("http://localhost:5500")
                        .build().toUri()
                )
                .build<Any>()
        }

        // 오류 메시지 반환
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(
                ServletUriComponentsBuilder
                    .fromHttpUrl("http://localhost:5500/login.html?err=$message")
                    .build().toUri()
            )
            .build<Any>()
    }
}
