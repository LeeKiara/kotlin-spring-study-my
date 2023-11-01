package com.bookshop.admin.publisher

data class BookMessageRequest(
    val id: Long,
    val publisher: String,
    val title: String,
    val author: String,
    val pubDate: String,
    val isbn: String,
    val categoryName: String,
    val priceStandard: String,
    val quantity: String,
    val imageUuidName: String,
)
