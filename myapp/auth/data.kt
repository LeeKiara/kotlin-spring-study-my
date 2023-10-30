package com.example.myapp.auth

import org.jetbrains.exposed.dao.id.EntityID

data class SignupRequest (
     val username: String,
     val password: String,
     val mname: String,
     val email: String,
)

data class AuthProfile(
    val id: Long, // 프로필 id
    val mname: String, // 프로필 별칭
    val username: String // 로그인 사용자이름
)

data class MemberResponse(
    val id: Long, // 프로필 id
    val mname: String, // 프로필 별칭
    val username: String, // 로그인 사용자이름
    val email: String
)