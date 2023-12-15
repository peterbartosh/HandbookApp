package com.example.handbookapp.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handbookapp.data.utils.PojoType
import com.example.handbookapp.domain.Order
import com.example.handbookapp.domain.OrderProduct
import com.example.handbookapp.domain.Pojo
import kotlin.reflect.full.memberProperties


@Composable
fun Table(
    isLoading: Boolean,
    tableData : List<Pojo>,
    currentEntityType: PojoType,
    onEditOrder: (Pojo) -> Unit,
    onDeleteOrder: (Pojo) -> Unit,
    onAddProp: (String, Boolean) -> Unit,
    onRemoveProp: (String) -> Unit,
    initDB: () -> Unit
) {

    var curActiveCell by remember {
        mutableStateOf(Pair(-1, -1))
    }


    if (isLoading) CircularProgressIndicator()

    LazyColumn(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {


        item {

            val realFieldsNames = if (currentEntityType === PojoType.Order)
                listOf("status", "date", "longtitude", "lattitude", "preferencesComment")
            else
                listOf("amount", "productName")

            val (members, weights) = if (currentEntityType === PojoType.Order)
                Pair(listOf("Status", "Date", "Lng", "Lat", "Comment"), listOf(0.13f, 0.2f, 0.15f, 0.15f, 0.35f))
            else
                Pair(listOf("Amount", "Product Name"), listOf(0.3f, 0.5f))


            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .height(50.dp),
            ) {
                members.forEachIndexed { index, fieldName ->
                   TitleCell(
                       weight = weights[index],
                       fieldName = fieldName,
                       onAscClick = { doApply ->
                            if (doApply)
                               onAddProp(realFieldsNames[index], true)
                           else
                               onRemoveProp(realFieldsNames[index])
                       },
                       onDescClick = { doApply ->
                           if (doApply)
                               onAddProp(realFieldsNames[index], false)
                           else
                               onRemoveProp(realFieldsNames[index])
                       }
                   )
                }

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp)
                )

            }
        }

        if (!isLoading)
            itemsIndexed(tableData) { index, pojo ->

                TableRow(
                    rowInd = index + 1,
                    pojo = pojo,
                    curActiveCell = curActiveCell,
                    notifyCurActiveCell = { rowInd, colInd ->
                        curActiveCell = Pair(rowInd, colInd)
                    },
                    onEdit = onEditOrder,
                    onDelete = onDeleteOrder
                )
            }

        if (tableData.isEmpty()){
            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

                    Column(modifier = Modifier.fillMaxWidth().padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Таблица пуста.")
                        Text(
                            modifier = Modifier.clickable { initDB() },
                            text = "Заполнить стандартным набором данных",
                            style = TextStyle(color = Color.Green, textDecoration = TextDecoration.Underline)
                        )
                    }
                }
            }
        }
    }
}