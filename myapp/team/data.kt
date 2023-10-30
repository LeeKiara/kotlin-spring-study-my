package com.example.myapp.team

data class ProjectTeamMemberRequest (
    val pid: Long,
    val mid: Long,
    val createdTime: String?
) {
    constructor(
        pid: Long,
        mid: Long
    ) : this(
        pid,
        mid,
        null
    )
}


data class ProjectTeamMemberResponse (
    val ptid: Long,
    val pid: Long,
    val mid: Long,
    val createdTime: String
)