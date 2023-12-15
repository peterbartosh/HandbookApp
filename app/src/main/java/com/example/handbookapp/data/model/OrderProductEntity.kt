package com.example.handbookapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "orders_products",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class OrderProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderId: Int?,
    val amount: Int?,
    val productName: String?
)