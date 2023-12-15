package com.example.handbookapp.domain

import com.example.handbookapp.data.model.OrderEntity
import com.example.handbookapp.data.utils.convertMillisToString
import com.example.handbookapp.data.utils.convertStringDateToMillis
import java.sql.Date
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties


data class Order(
    override val id: Int,
    val status: Int?,
    val longtitude: Double?,
    val lattitude: Double?,
    val date: Long?,
    val preferencesComment: String?
): Pojo

fun OrderEntity.toOrder() = Order(
    id, status, longtitude, lattitude, convertStringDateToMillis(date), preferencesComment
)

fun Order.toOrderEntity() = OrderEntity(
    id = id,
    status = status,
    longtitude = longtitude,
    lattitude = lattitude,
    date = date?.convertMillisToString(),
    preferencesComment = preferencesComment
)