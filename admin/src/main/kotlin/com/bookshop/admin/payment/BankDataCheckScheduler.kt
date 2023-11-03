package com.bookshop.admin.payment

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@EnableScheduling
@Component
class BankDataCheckScheduler(
        private val paymentController: PaymentController,
        private val paymentService: PaymentService,
        private val redisTemplate: RedisTemplate<String, String>
) {

    //에러 로그 확인을 위해
    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val REDIS_KEY = "bank-deposit"

    // Object <-> JSON
    private val mapper = jacksonObjectMapper()


    // 결제방법이 온라인입금인 주문정보 조회 후 redis 에 저장
    // 처리 간격 : 30분
    @Scheduled(cron = "0 */30 * * * *")
    fun scheduledSaveBankDeposit() {
        println("======= 온라인입금으로 주문한 정보 redis 캐시에 정장(30분 간격)  ${Date().time} =======")

        try {
            // 결제방법이 온라인입금인 주문정보 조회
            val result = paymentController.fetchOrderInBankDeposit()

            println("*** 결제방법이 온라인입금인 주문정보 조회 *** $result")

            // RedisTemplate<key=String, value=String>
            // default: localhost:6379
            redisTemplate.delete(REDIS_KEY) // 캐시 데이터 삭제

            // 캐시 데이터 생성
            redisTemplate.opsForValue()
                    .set(REDIS_KEY, mapper.writeValueAsString(result))

        } catch (e: Exception) {
            //에러메세지 확인
            logger.error(e.message)
        }

    }

    // redis cache 정보를 조회 후 주문상태를 완료 처리함
    // 처리 간격 : 1시간
    @Scheduled(cron = "0 0 */1 * * *")
    fun scheduledFetchBankDeposit() {
        println("======= redis cache 정보를 조회 후 주문상태를 완료 처리함(1시간 간격) ${Date().time} =======")

        try {

            // redis cache 정보 조회
            val result = redisTemplate.opsForValue().get(REDIS_KEY)

            if (result != null) {
                // value(json) -> List<TopProductResponse>
                val resultToJson: List<BankDepositResponse> = mapper.readValue(result)

                // 주문상태를 완료 처리
                paymentService.updateOrdersStatus(resultToJson);
            }

        } catch (e: Exception) {
            //에러메세지 확인
            logger.error(e.message)
        }

    }

    fun getCachedBankDeposit(): List<BankDepositResponse> {

        val result = redisTemplate.opsForValue().get(REDIS_KEY)
        if (result != null) {
            // value(json) -> List<TopProductResponse>
            val list: List<BankDepositResponse> = mapper.readValue(result)
            return list
        } else {
            // 빈배열 반환
            return listOf()
        }
    }


}