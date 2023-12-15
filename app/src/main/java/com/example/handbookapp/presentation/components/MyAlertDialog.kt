package com.example.handbookapp.presentation.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun MyAlertDialog(
    text: String,
    expandDeleteDialog: MutableState<Boolean>,
    onConfirmCLick: () -> Unit
) {
    if (expandDeleteDialog.value)
        AlertDialog(
            modifier = Modifier.wrapContentSize(),
            text = { Text(text = text) },
            title = { Text(text = "Предупреждение") },
            dismissButton = { Button(onClick = { expandDeleteDialog.value = false }) {
                Text(text = "Нет")
            }
            },
            confirmButton = { Button(onClick = {
                onConfirmCLick()
                expandDeleteDialog.value = false
            }) {
                Text(text = "Да")
            }
            },
            onDismissRequest = { expandDeleteDialog.value = false }
        )
}