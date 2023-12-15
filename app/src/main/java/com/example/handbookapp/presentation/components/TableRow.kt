package com.example.handbookapp.presentation.components

import android.widget.TableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.handbookapp.domain.Pojo
import com.example.handbookapp.data.utils.ContentType
import com.example.handbookapp.data.utils.convertMillisToString
import com.example.handbookapp.data.utils.convertStringDateToMillis
import com.example.handbookapp.domain.Order
import com.example.handbookapp.domain.OrderProduct

@Composable
fun TableRow(
    rowInd: Int,
    pojo: Pojo,
    curActiveCell: Pair<Int, Int>,
    notifyCurActiveCell: (Int, Int) -> Unit,
    onEdit: (Pojo) -> Unit,
    onDelete: (Pojo) -> Unit
) {
    val expandDeleteDialog = remember {
        mutableStateOf(false)
    }

    MyAlertDialog(
        text = "Вы уверены, что хотите удалить эту строку?",
        expandDeleteDialog = expandDeleteDialog
    ) {
        onDelete(pojo)
    }

    if (pojo is Order){
        OrderRow(
            rowInd = rowInd,
            pojo = pojo,
            curActiveCell = curActiveCell,
            notifyCurActiveCell = notifyCurActiveCell,
            onEdit = onEdit,
            onDelete = onDelete
        )
    } else if (pojo is OrderProduct){
        OrderProductRow(
           rowInd = rowInd,
           pojo = pojo,
           curActiveCell = curActiveCell,
           notifyCurActiveCell = notifyCurActiveCell,
           onEdit = onEdit,
           onDelete = onDelete
        )
    }
}

@Composable
private fun OrderRow(
    rowInd: Int,
    pojo: Pojo,
    curActiveCell: Pair<Int, Int>,
    notifyCurActiveCell: (Int, Int) -> Unit,
    onEdit: (Pojo) -> Unit,
    onDelete: (Pojo) -> Unit
) {

    val order = pojo as Order

    val expandDeleteDialog = remember {
        mutableStateOf(false)
    }

    MyAlertDialog(
        text = "Вы уверены, что хотите удалить эту строку?",
        expandDeleteDialog = expandDeleteDialog
    ) {
        onDelete(order)
    }


    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(
            initText = order.status.toString(),
            enabled = curActiveCell.first == rowInd && curActiveCell.second == 0,
            weight = 0.13f,
            contentType = ContentType.DropDownMenu,
            dropDownMenuItems = listOf("1", "2", "3", "4"),
            dropDownMenuItemsExplanations = listOf("Обработан", "В пути", "Принят", "Отменён"),
            onNotifyCurCell = {
                notifyCurActiveCell.invoke(rowInd, 0)
            },
            onConfirm = { statusStr ->
                val status = statusStr.toInt()
                onEdit(Order(pojo.id, status, pojo.longtitude, pojo.lattitude, pojo.date, pojo.preferencesComment))
            }
        )

        TableCell(
            initText = order.date?.convertMillisToString().toString(),
            enabled = curActiveCell.first == rowInd && curActiveCell.second == 1,
            weight = 0.2f,
            contentType = ContentType.DatePicker,
            onNotifyCurCell = {
                notifyCurActiveCell.invoke(rowInd, 1)
            },
            onConfirm = { dateStr ->
                val date = convertStringDateToMillis(dateStr)
                onEdit(Order(pojo.id, pojo.status, pojo.longtitude, pojo.lattitude, date, pojo.preferencesComment))
            }
        )

        TableCell(
            initText = order.longtitude.toString(),
            enabled = curActiveCell.first == rowInd && curActiveCell.second == 2,
            weight = 0.15f,
            keyboardType = KeyboardType.Number,
            contentType = ContentType.TextField,
            validationRule = { text ->
                text.isNotBlank() && try {
                    text.toDouble()
                    true
                } catch (nfe: NumberFormatException){
                    false
                }
            },
            onNotifyCurCell = {
                notifyCurActiveCell.invoke(rowInd, 2)
            },
            onConfirm = { longtitudeStr ->
                val longtitude = longtitudeStr.toDouble()
                onEdit(Order(pojo.id, pojo.status, longtitude, pojo.lattitude, pojo.date, pojo.preferencesComment))
            }
        )

        TableCell(
            initText = order.lattitude.toString(),
            enabled = curActiveCell.first == rowInd && curActiveCell.second == 3,
            weight = 0.15f,
            keyboardType = KeyboardType.Number,
            contentType = ContentType.TextField,
            validationRule = { text ->
                text.isNotBlank() && try {
                    text.toDouble()
                    true
                } catch (nfe: NumberFormatException){
                    false
                }
            },
            onNotifyCurCell = {
                notifyCurActiveCell.invoke(rowInd, 3)
            },
            onConfirm = { lattitudeStr ->
                val lattitude = lattitudeStr.toDouble()
                onEdit(Order(pojo.id, pojo.status, pojo.longtitude, lattitude, pojo.date, pojo.preferencesComment))
            }
        )

        TableCell(
            initText = order.preferencesComment.toString(),
            enabled = curActiveCell.first == rowInd && curActiveCell.second == 4,
            weight = 0.35f,
            singleLine = false,
            contentType = ContentType.TextField,
            validationRule = { true },
            onNotifyCurCell = {
                notifyCurActiveCell.invoke(rowInd, 4)
            },
            onConfirm = { prefComment ->
                onEdit(Order(pojo.id, pojo.status, pojo.longtitude, pojo.lattitude, pojo.date, prefComment))
            }
        )

        IconButton(
            modifier = Modifier
                .size(30.dp)
                .padding(start = 10.dp),
            onClick = { expandDeleteDialog.value = true },
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}


@Composable
private fun OrderProductRow(
    rowInd: Int,
    pojo: Pojo,
    curActiveCell: Pair<Int, Int>,
    notifyCurActiveCell: (Int, Int) -> Unit,
    onEdit: (Pojo) -> Unit,
    onDelete: (Pojo) -> Unit
) {

    val order = pojo as OrderProduct

    val expandDeleteDialog = remember {
        mutableStateOf(false)
    }

    MyAlertDialog(
        text = "Вы уверены, что хотите удалить эту строку?",
        expandDeleteDialog = expandDeleteDialog
    ) {
        onDelete(order)
    }


    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TableCell(
            initText = order.amount.toString(),
            enabled = curActiveCell.first == rowInd && curActiveCell.second == 0,
            weight = 0.3f,
            keyboardType = KeyboardType.Number,
            contentType = ContentType.TextField,
            onNotifyCurCell = {
                notifyCurActiveCell.invoke(rowInd, 0)
            },
            onConfirm = { amountStr ->
                val amount = amountStr.toInt()
                onEdit(OrderProduct(pojo.id, pojo.orderId, amount, pojo.productName))
            }
        )


        TableCell(
            initText = order.productName.toString(),
            enabled = curActiveCell.first == rowInd && curActiveCell.second == 4,
            weight = 0.5f,
            singleLine = false,
            contentType = ContentType.TextField,
            validationRule = { true },
            onNotifyCurCell = {
                notifyCurActiveCell.invoke(rowInd, 4)
            },
            onConfirm = { productName ->
                onEdit(OrderProduct(pojo.id, pojo.orderId, pojo.amount, productName))
            }
        )

        IconButton(
            modifier = Modifier
                .size(30.dp)
                .padding(start = 10.dp),
            onClick = { expandDeleteDialog.value = true },
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}