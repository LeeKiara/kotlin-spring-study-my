package com.example.myapp.team

import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration

object ProjectTeamMembers : Table("project_team_member") {
    // val 필드명 = 컬럼타입("컬럼명")
    val ptid         = long("ptid").autoIncrement()
    val pid = long("pid")
    val mid = long("mid")
    val createdTime = datetime("created_time")

    // primary key 설정
    override val primaryKey = PrimaryKey(ptid, name = "pk_project_team_member_id")
}

// 테이블 생성 코드
@Configuration
class PostTableSetup(private val database: Database) {

    // migrate(이주하다): 코드 -> DB

    // 의존성 객체 생성 및 주입이 완료된 후에 실행할 코드를 작성
    // 스프링 환경구성이 끝난 후에 실행
    @PostConstruct
    fun migrateSchema() {
        // expose 라이브러리에서는 모든 SQL 처리는
        // transaction 함수의 statement 람다함수 안에서 처리를 해야 함
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(ProjectTeamMembers)
        }
    }

}