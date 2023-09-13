package com.example.myapp.project

import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.springframework.context.annotation.Configuration
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import java.sql.Blob
import java.sql.Types.BLOB

object Projects : Table("project") {
    // val 필드명 = 컬럼타입("컬럼명")
    val pid         = long("pid").autoIncrement()
    val title       = varchar("title", 100)
    val description = varchar("description", 1000).nullable()
    val startDate   = date("start_date")
    val endDate     = date("end_date")
//    val image       = blob("image").nullable() // ExposedBlob을 사용하여 BLOB 컬럼으로 정의
    val image       = text("image" ).nullable() // ExposedBlob을 사용하여 BLOB 컬럼으로 정의
    val status      = varchar("status", 1)
    val creatorUser = long("creator_user")
    val createdTime = datetime("created_time")

    // primary key 설정
    override val primaryKey = PrimaryKey(pid, name = "pk_project_id")
}

// 테이블 생성 코드
@Configuration
class ProjectTableSetup(private val database: Database) {

    // migrate(이주하다): 코드 -> DB

    // 의존성 객체 생성 및 주입이 완료된 후에 실행할 코드를 작성
    // 스프링 환경구성이 끝난 후에 실행
    @PostConstruct
    internal fun migrateSchema() {
        // expose 라이브러리에서는 모든 SQL 처리는
        // transaction 함수의 statement 람다함수 안에서 처리를 해야 함
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Projects)
        }
    }

}


