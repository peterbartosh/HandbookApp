package com.example.handbookapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val status: Int?,
    val longtitude: Double?,
    val lattitude: Double?,
    val date: String?,
    val preferencesComment: String?,
)
