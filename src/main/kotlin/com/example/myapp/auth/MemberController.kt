package com.example.myapp.auth

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/member")
class MemberController {

    // 이메일 정보로 사용자 정보 가져오기
    @PutMapping("/{email}")
    fun getMember(@PathVariable email: String) = transaction() {

        println("입력값 확인 : $email")

        var m = Members

        Members
            .select { m.email eq email}
            .map {r -> MemberResponse(
                r[m.id].value,
                r[m.mname],
                r[m.username],
                r[m.email]
            )
            }
    }

    @Auth
    @GetMapping("/getUserInfo")
    fun getAuthProfile(@RequestAttribute authProfile: AuthProfile): ResponseEntity<AuthProfile> {
        println("authProfile :$authProfile")
        return ResponseEntity.status(HttpStatus.OK).body(authProfile)
    }
}
