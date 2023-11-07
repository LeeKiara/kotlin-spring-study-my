package com.bookshop.admin.payment

import com.bookshop.admin.order.Orders
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaymentService(private val database: Database) {

    //에러 로그 확인을 위해
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    // 온라인 입금여부가 Y로 들어오면 주문완료로 처리한다.
    fun updateOrdersStatus(bankDepositList: List<BankDepositResponse>) {
        println("\n┌─────────────────────────────────────────────────────────────┐");
        println("│               온라인 입금으로 결제 건 주문완료 처리.              │");
        println("├─────────────────────────────────────────────────────────────┤");



        transaction {
            for (depositList in bankDepositList) {

                // select * from orders WHERE id = :id
                val query =
                        Orders.select {
                            (Orders.id eq depositList.orderId)
                        }

                if (query.count() > 0) {
                    // 해당 주문건의 무통장 입금여부가 Y로 들어와서 주문완료로 처리한다.
                    // SQL
                    // UPDATE orders SET order_status = 1 WHERE id = :id

                    Orders.update({ Orders.id eq depositList.orderId }) {
                        it[orderStatus] = "1";
                    }

                    println(" │ 주문번호 : ${depositList.orderId}, 주문완료: }")

                } else {
                    println("├ [ 주문대상 건수 : 0 ]                                         ┤");
                    println("└─────────────────────────────────────────────────────────────┘");
                }
            }
        }

    }


}


