package com.bookshop.admin.order

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class BankDepositResponse(
    val orderId: Long,
    val deposit: String
)

@RestController
@RequestMapping("/payment")
class OrderPaymentController {


    val bankDeposit = listOf(
        BankDepositResponse(2023123456804, "Y"),
        BankDepositResponse(2023123456803, "Y"),
        BankDepositResponse(30000, "Y"),
    )

    @RequestMapping("/bank-deposit")
    fun getPaymentDeposit() : List<BankDepositResponse> {
        println("<<<<< OrderPaymentController getPaymentDeposit >>>>>>>>>")

        return bankDeposit;
    }
}