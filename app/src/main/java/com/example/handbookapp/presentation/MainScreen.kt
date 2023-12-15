package com.example.handbookapp.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.handbookapp.data.utils.PojoType
import com.example.handbookapp.domain.Order
import com.example.handbookapp.domain.OrderProduct
import com.example.handbookapp.presentation.components.Table
import com.example.handbookapp.presentation.theme.Gold

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {

    val data = mainViewModel.data.collectAsState()

    Scaffold(
        topBar = { Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = "Бартош Пётр Евгеньевич\n4 курс, 4 группа, 2023") },
        floatingActionButton = {
            FloatingActionButton(onClick = { mainViewModel.add(
                if (mainViewModel.currentPojoType === PojoType.Order)
                    Order(mainViewModel.lastId + 1, null, null, null, null, null)
                else
                    OrderProduct(mainViewModel.lastId + 1, null, null, null)
            ) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 10.dp)
        ) {

            Column(Modifier.fillMaxSize()) {

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1.0f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(2.dp, if (mainViewModel.currentPojoType === PojoType.Order) Gold else Color.DarkGray),
                        onClick = {
                            mainViewModel.currentPojoType = PojoType.Order
                            mainViewModel.getData()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text(text = "Таблица заказов", color = Color.White)
                    }

                    Button(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1.0f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(2.dp, if (mainViewModel.currentPojoType === PojoType.OrderProduct) Gold else Color.DarkGray),
                        onClick = {
                            mainViewModel.currentPojoType = PojoType.OrderProduct
                            mainViewModel.getData()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text(text = "Таблица заказанных продуктов", textAlign = TextAlign.Center, color = Color.White)
                    }

                }

                Table(
                    isLoading = mainViewModel.isLoading,
                    tableData = data.value,
                    currentEntityType = mainViewModel.currentPojoType,
                    onEditOrder = mainViewModel::edit,
                    onDeleteOrder = mainViewModel::delete,
                    onAddProp = mainViewModel::addProp,
                    onRemoveProp = mainViewModel::removeProp,
                    initDB = if (mainViewModel.currentPojoType === PojoType.Order)
                        mainViewModel::fillOrdersTable
                    else
                        mainViewModel::fillOrdersProductsTable
                )

            }
        }

    }


//    if (showDatePicker)
//        DatePickerDialog(
//            onDismissRequest = { showDatePicker = false },
//            confirmButton = { Button(onClick = {  }) {
//                Text(text = "Confirm")
//            } }
//        ) {
//
//        }



}