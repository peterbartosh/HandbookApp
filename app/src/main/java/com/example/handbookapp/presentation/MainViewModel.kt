package com.example.handbookapp.presentation

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handbookapp.data.repository.RoomRepository
import com.example.handbookapp.data.utils.PojoType
import com.example.handbookapp.data.utils.readInstanceProperty
import com.example.handbookapp.domain.Order
import com.example.handbookapp.domain.OrderProduct
import com.example.handbookapp.domain.Pojo
import com.example.handbookapp.domain.toOrder
import com.example.handbookapp.domain.toOrderEntity
import com.example.handbookapp.domain.toOrderProduct
import com.example.handbookapp.domain.toOrderProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SortOption(val propName: String, val isAsc: Boolean)

@HiltViewModel
class MainViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {

    var isLoading by mutableStateOf(false)

    private val _data = MutableStateFlow<List<Pojo>>(emptyList())
    val data: StateFlow<List<Pojo>> = _data

    private val ordersSortingOptions = mutableListOf<SortOption>()
    private val orderProductsSortingOptions = mutableListOf<SortOption>()

    var currentPojoType by mutableStateOf(PojoType.Order)

    var lastId = 0

    init {
        Log.d("INIT_TAG", ": ")
        getData()
    }

    fun fillOrdersTable(){
        val ordersTableData = listOf(
            Order(
                id = 1,
                status = 2,
                longtitude = 51.2423421,
                lattitude = 22.6431232,
                date = 1702504148113,
                preferencesComment = "Some additional client preferences 1. Anything could be written."
            ),
            Order(
                id = 2,
                status = 3,
                longtitude = 51.2423421,
                lattitude = 22.6431232,
                date = 1702504148113,
                preferencesComment = "Some additional client preferences 2. Anything could be written."
            ),
            Order(
                id = 3,
                status = 1,
                longtitude = 51.2423421,
                lattitude = 22.7650122,
                date = 1702304348113,
                preferencesComment = "Some additional client preferences 3. Anything could be written."
            ),
            Order(
                id = 4,
                status = 3,
                longtitude = 11.2344219,
                lattitude = 22.2431232,
                date = 1702204348113,
                preferencesComment = "Some additional client preferences 4. Anything could be written."
            ),
            Order(
                id = 5,
                status = 1,
                longtitude = 21.2423421,
                lattitude = 26.6431232,
                date = 1701504348113,
                preferencesComment = "Some additional client preferences 5. Anything could be written."
            ),
        )


        viewModelScope.launch {
            ordersTableData.forEach {
                roomRepository.addOrder(it.toOrderEntity())
            }
        }
    }

    fun fillOrdersProductsTable(onError: (String) -> Unit){

        val ordersProductsTableData = listOf(
            OrderProduct(
                id = 1,
                orderId = 1,
                amount = 10,
                productName = "Coca-Cola"
            ),
            OrderProduct(
                id = 2,
                orderId = 1,
                amount = 4,
                productName = "Pepsi-Cola"
            ),
            OrderProduct(
                id = 3,
                orderId = 3,
                amount = 38,
                productName = "Bela-Cola"
            ),
            OrderProduct(
                id = 4,
                orderId = 4,
                amount = 29,
                productName = "Mega-Cola"
            ),
            OrderProduct(
                id = 5,
                orderId = 5,
                amount = 17,
                productName = "Rare-Coca-Cola"
            )
        )
        viewModelScope.launch {
            var errorOccurred = false
            launch {
                ordersProductsTableData.forEach {
                    try {
                        roomRepository.addOrderProduct(it.toOrderProductEntity())
                    } catch (sqlE: SQLiteConstraintException) {
                        errorOccurred = true
                    }
                }
            }.join()

            if (errorOccurred)
                onError("Ошибка. Перед заполнением этой таблицы стандартным набором данных из 5 записей, вам следует заполнить таблицу заказов")
        }
    }

    fun getData(shouldDelay: Boolean = false) = viewModelScope.launch {

        val flow = when (currentPojoType){
            PojoType.Order -> roomRepository.getOrders().map { it.map { el -> el.toOrder() } }
            PojoType.OrderProduct -> roomRepository.getOrdersProducts().map { it.map { el -> el.toOrderProduct() } }
        }

        flow.collectLatest { pojos ->

            isLoading = true

            _data.value = createComparator()?.let { comparator ->
                 pojos.sortedWith(comparator)
            } ?: pojos

            try {
                lastId = pojos.maxOf { it.id }
            } catch (_: NoSuchElementException) {}

            if (shouldDelay) delay(100)

            isLoading = false
        }
    }

    fun add(pojo: Pojo, onError: (String) -> Unit = {}, onSuccess: () -> Unit = {}) = viewModelScope.launch {
        try {
            if (pojo is Order)
                roomRepository.addOrder(pojo.toOrderEntity())
            else if (pojo is OrderProduct)
                roomRepository.addOrderProduct(pojo.toOrderProductEntity())
            onSuccess()
        } catch (sqlE: SQLiteConstraintException){
            onError("Заказ не найден")
        }
    }

    fun edit(pojo: Pojo) = viewModelScope.launch {
        if (pojo is Order)
            roomRepository.editOrder(pojo.toOrderEntity())
        else if (pojo is OrderProduct)
            roomRepository.editOrderProduct(pojo.toOrderProductEntity())
    }

    fun delete(pojo: Pojo) = viewModelScope.launch {
        if (pojo is Order)
            roomRepository.deleteOrder(pojo.toOrderEntity()).also { currentPojoType = PojoType.Order }
        else if (pojo is OrderProduct)
            roomRepository.deleteOrderProduct(pojo.toOrderProductEntity()).also { currentPojoType = PojoType.OrderProduct }

    }

    fun addProp(propName: String, isAsc: Boolean) = viewModelScope.launch {
        launch {
            if (currentPojoType === PojoType.Order) {
                ordersSortingOptions.removeIf { it.propName == propName }
                ordersSortingOptions.add(SortOption(propName, isAsc))
            }
            else {
                orderProductsSortingOptions.removeIf { it.propName == propName }
                orderProductsSortingOptions.add(SortOption(propName, isAsc))
            }
        }.join()
        getData(true)
    }

    fun removeProp(propName: String) = viewModelScope.launch {
        launch {
            if (currentPojoType === PojoType.Order)
                ordersSortingOptions.removeIf { it.propName == propName }
            else
                orderProductsSortingOptions.removeIf { it.propName == propName }
        }.join()
        getData(true)
    }

    private fun createComparator(): Comparator<Pojo>? {

        var comparator: Comparator<Pojo>? = null

        val sortingOptions = if (currentPojoType === PojoType.Order)
            ordersSortingOptions
        else
            orderProductsSortingOptions

        sortingOptions.forEachIndexed { _, option ->
            comparator = comparator?.thenComparing { pojo1, pojo2 ->
                compare(pojo1, pojo2, option.propName, option.isAsc)
            } ?: Comparator { pojo1, pojo2 ->
                compare(pojo1, pojo2, option.propName, option.isAsc)
            }
        }

        return comparator
    }

    private fun compare(pojo1: Pojo, pojo2: Pojo, propName: String, isAsc: Boolean) =
        if (isAsc)
            compareValues(
                readInstanceProperty(pojo1, propName),
                readInstanceProperty(pojo2, propName)
            ) else
                compareValues(
                    readInstanceProperty(pojo2, propName),
                    readInstanceProperty(pojo1, propName)
                )
}