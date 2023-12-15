package com.example.handbookapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.handbookapp.data.model.OrderEntity
import com.example.handbookapp.data.model.OrderProductEntity

@Database(
    entities = [OrderEntity::class, OrderProductEntity::class],
    version = 8,
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getDao(): LocalDao
}