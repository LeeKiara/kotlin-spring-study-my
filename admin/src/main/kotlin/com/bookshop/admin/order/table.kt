package com.bookshop.admin.order

import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration


//ALTER TABLE Orders AUTO_INCREMENT = 2023123456789;
// 주문 테이블
object Orders : Table("orders") {
    val id = long("id").autoIncrement().uniqueIndex()

    // 주문일자
    val orderDate = datetime("order_date")

    // 결제수단
    val paymentMethod = varchar("payment_method", 1)

    // 결제금액
    val paymentPrice = integer("payment_price")

    // 주문상태 (1: 완료, 2:취소)
    val orderStatus = varchar("order_status", 1)

//    val profileId = reference("profile_id", Profiles)

    // 주문 key
    override val primaryKey = PrimaryKey(Orders.id)
}


//테이블 생성
@Configuration
class OrderTableSetUp(private val database: Database) {
    @PostConstruct
    fun migrateSchema() {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Orders)
        }
    }
}

