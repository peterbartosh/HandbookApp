package com.example.handbookapp.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.handbookapp.data.local.LocalDao
import com.example.handbookapp.data.model.OrderEntity
import com.example.handbookapp.data.model.OrderProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepository @Inject constructor(private val localDao: LocalDao) {


    fun getOrders() = localDao.getOrders().flowOn(Dispatchers.IO)

    suspend fun addOrder(orderEntity: OrderEntity) = withContext(Dispatchers.IO) {
        localDao.addOrder(orderEntity)
    }
    suspend fun editOrder(orderEntity: OrderEntity) = withContext(Dispatchers.IO) {
        localDao.editOrder(orderEntity)
    }

    suspend fun deleteOrder(orderEntity: OrderEntity) = withContext(Dispatchers.IO) {
        localDao.deleteOrder(orderEntity)
    }

    fun getOrdersProducts() = localDao.getOrdersProducts().flowOn(Dispatchers.IO)

    suspend fun addOrderProduct(orderProductEntity: OrderProductEntity) = withContext(Dispatchers.IO) {
        localDao.addOrderProduct(orderProductEntity)
    }

    suspend fun editOrderProduct(orderProductEntity: OrderProductEntity) = withContext(Dispatchers.IO) {
        localDao.editOrderProduct(orderProductEntity)
    }

    suspend fun deleteOrderProduct(orderProductEntity: OrderProductEntity) = withContext(Dispatchers.IO) {
        localDao.deleteOrderProduct(orderProductEntity)
    }



}