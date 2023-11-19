package com.bookshop.admin.order

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PutMapping

@Service
class OrderService(private val database: Database) {

    //에러 로그 확인을 위해
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    // 배송 처리
    fun modifyOrderBatchStatus(orderId: Long) {

        println("<<< OrderService 배송 완료 처리 >>>")

        val o = Orders;

        // id에 해당 레코드가 없으면 return
        transaction {
            o.select { (o.id eq orderId) }.firstOrNull()
        } ?: return;

        // 배송 처리 : batch-status("1")로 업데이트 처리 => 배송 중
        transaction {
            o.update({ o.id eq orderId }) {
                it[batchStatus] = "1";
            }
        }
    }

    // 주문 판매수량 업데이트
//    fun updateOrderSales(orderItemStock: List<OrderSalesRequest>) {
    fun updateOrderSales(orderItemStock: List<OrderSalesItem>) {
        println("\n<<< OrderService createOrderAddress >>>")
        println("request Data (주문 항목) ==> ")
        println("주문 항목:")

        transaction {
            try {
                for (reqItem in orderItemStock) {
                    println("** 주문 판매수량 업데이트 도서 ID: ${reqItem.productId}, 수량: ${reqItem.quantity}")

                    // select id from order_stock where item_id = 1;
                    val query = OrderSalesTable.select {
                        (OrderSalesTable.itemId eq reqItem.productId)
                    }

                    if (query.count() > 0) {
                        // 이미 해당 도서의 판매정보가 등록되어 있으면 기존 판매수에 수량을 더한다
                        // SQL
                        // UPDATE order_stock SET book_stock = book_stock + 2 WHERE item_id = 1;
                        OrderSalesTable.update({ OrderSalesTable.itemId eq reqItem.productId }) {
                            it[bookSales] = bookSales + reqItem.quantity.toInt()
                        }
                    } else {
                        // 신규이면 새로 등록한다.
                        // SQL
                        // select id from books where item_id = :itemId;
                        val bookId = Books.select {
                            (Books.itemId eq reqItem.productId)
                        }.first()
                            .let { it[Books.id] }

                        // INSERT INTO order_stock( book_stock, item_id, status, book_id)
                        // values (?, ?, ?,  (select id from books where item_id =?))
                        OrderSalesTable.insert {
                            it[bookSales] = reqItem.quantity
                            it[itemId] = reqItem.productId
                            it[status] = "1"
                            it[OrderSalesTable.bookId] = bookId

                        } get OrderSalesTable.id
                    }

                }
            } catch (e: Exception) {
                rollback()
                //에러메세지 확인
                logger.error(e.message)
                return@transaction 0
            }
        }

    }


}