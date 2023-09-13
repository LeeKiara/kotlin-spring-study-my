package com.example.myapp.team

data class ProjectTeamMemberRequest (
    val pid: Long,
    val mid: Long,
    val createdTime: String
)

data class ProjectTeamMemberResponse (
    val ptid: Long,
    val pid: Long,
    val mid: Long,
    val createdTime: String
)