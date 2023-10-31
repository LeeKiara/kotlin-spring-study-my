package com.example.commerce.product

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

data class TopProductResponse(
    val id: Int,
    val name: String,
    val image: String
)

data class OrderResponse(
    val itemId: Int,
    val orderItems: MutableList<OrderItemsResponse>
)
data class OrderItemsResponse(
    val title: String,
    val cover: String
)

//@FeignClient(name="productClient", url="http://192.168.0.5:8082/products")
@FeignClient(name="productClient", url="http://192.168.100.204:8082/products")
interface ProductClient {
    @GetMapping("/top-promotion")
    fun getTopPromotion() : List<TopProductResponse>

    @GetMapping("/order-products")
    fun getOrderProducts() : List<OrderResponse>
}