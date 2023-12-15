package com.example.handbookapp.domain

import com.example.handbookapp.data.model.OrderProductEntity
import kotlin.reflect.full.memberProperties


data class OrderProduct(
    override val id: Int,
    val orderId: Int?,
    val amount: Int?,
    val productName: String?
): Pojo

fun OrderProductEntity.toOrderProduct() = OrderProduct(
    id = id,
    orderId = orderId,
    amount = amount,
    productName = productName
)

fun OrderProduct.toOrderProductEntity() = OrderProductEntity(
    id = id,
    orderId = orderId,
    amount = amount,
    productName = productName
)