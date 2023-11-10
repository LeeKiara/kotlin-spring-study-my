package com.bookshop.admin.order

import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.dao.id.LongIdTable
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

// 주문 판매 정보(도서별 판매량)
object OrderSalesTable : Table("order_sales") {
    val id = long("id").autoIncrement().uniqueIndex()

    // 도서별 판매수
    val bookSales = integer("book_sales")

    // 도서 Item Id
    val itemId = integer("item_id")

    val status = varchar("status", 1)

    // 도서 Id
    var bookId = reference("book_id", Books.id)

    // key
    override val primaryKey = PrimaryKey(OrderSalesTable.id)
}

//책 테이블
object Books : LongIdTable("books") {
    val itemId = integer("item_id")

//    override val primaryKey = PrimaryKey(id)
}

//테이블 생성
@Configuration
class OrderTableSetUp(private val database: Database) {
    @PostConstruct
    fun migrateSchema() {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Orders, OrderSalesTable, Books)
        }
    }
}

