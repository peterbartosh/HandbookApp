package com.example.handbookapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.handbookapp.data.model.OrderEntity
import com.example.handbookapp.data.model.OrderProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    @Query("Select * From orders Order By id")
    fun getOrders(): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(orderEntity: OrderEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editOrder(orderEntity: OrderEntity)

    @Delete
    suspend fun deleteOrder(orderEntity: OrderEntity)

    @Query("Select * From orders_products Order By id")
    fun getOrdersProducts() : Flow<List<OrderProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrderProduct(orderProductEntity: OrderProductEntity)

    @Update
    suspend fun editOrderProduct(orderProductEntity: OrderProductEntity)

    @Delete
    suspend fun deleteOrderProduct(orderProductEntity: OrderProductEntity)

}