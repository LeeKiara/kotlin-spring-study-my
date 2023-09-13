package com.example.myapp.project

data class ProjectCreateRequest (
    val title: String,
    val description: String?,
    val startDate: String,
    val endDate: String,
//    val creatorUser: String,
    val image: String?
)

fun ProjectCreateRequest.validate() =
    this.title.isNotEmpty()

data class ProjectResponse (
    val pid: Long,
    val title: String,
    val description: String?,
    val startDate: String,
    val endDate: String,
    val image: String?,
    val status: String,
    val creatorUser: Long,
    var createdTime: String,
    var mname: String?
) {
    // 생성자 1: 모든 필수 속성을 인수로 받음
    constructor(
        pid: Long,
        title: String,
        description: String?,
        startDate: String,
        endDate: String,
        image: String?,
        status: String,
        creatorUser: Long,
        createdTime: String
    ) : this(
        pid,
        title,
        description,
        startDate,
        endDate,
        image,
        status,
        creatorUser,
        createdTime,
        null
    )

}

data class ProjectModifyRequest (
    val pid: Long,
    val title: String,
    val description: String?,
    val startDate: String,
    val endDate: String,
    val image: String?,
    val status: String
)

fun ProjectModifyRequest.validate() =
    !(this.title.isEmpty() || this.status.isEmpty())