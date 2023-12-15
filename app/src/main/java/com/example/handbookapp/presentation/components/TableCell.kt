package com.example.handbookapp.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handbookapp.data.utils.ContentType
import com.example.handbookapp.data.utils.showErrorToast
import com.example.handbookapp.presentation.theme.Gold

@OptIn(ExperimentalComposeUiApi::class)
@Composable
inline fun RowScope.TableCell(
    initText: String,
    weight: Float,
    enabled: Boolean = false,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    contentType: ContentType,
    dropDownMenuItems: List<String> = emptyList(),
    dropDownMenuItemsExplanations: List<String> = emptyList(),
    crossinline onNotifyCurCell: () -> Unit = {},
    crossinline onConfirm: (String) -> Unit,
    crossinline validationRule: (String) -> Boolean = { true }
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var shouldSaveWhenUserLeaves by remember {
        mutableStateOf(false)
    }

    var textFieldState by remember {
        mutableStateOf(initText)
    }

    var statusText by remember {
        mutableStateOf(initText)
    }

    var dateText by remember {
        mutableStateOf(initText)
    }

    LaunchedEffect(key1 = enabled){

        if (shouldSaveWhenUserLeaves) {

            keyboardController?.hide()

            if (!validationRule(textFieldState)) {
                context.showErrorToast()
                textFieldState = initText
            } else {
                onConfirm(textFieldState)
            }

            shouldSaveWhenUserLeaves = false
        }
    }

    when (contentType){
        ContentType.TextField -> {

            Box(
                modifier = Modifier
                    //.wrapContentHeight()
                    .weight(weight)
                    .fillMaxHeight()
                    .border(1.dp, if (enabled) Gold else Color.DarkGray)
                    .background(Color.LightGray)
                    .clickable {
                        onNotifyCurCell()
                    },
                contentAlignment = Alignment.Center
            ) {

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.LightGray),
                    singleLine = singleLine,
                    enabled = enabled,
                    textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 11.sp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = keyboardType
                    ),
                    keyboardActions = KeyboardActions {
                        if (!validationRule(textFieldState)) {
                            context.showErrorToast()
                            textFieldState = initText
                            return@KeyboardActions
                        }
                        onConfirm(textFieldState)
                        keyboardController?.hide()
                    },
                    value = textFieldState,
                    onValueChange = { newVal ->
                        textFieldState = newVal
                        shouldSaveWhenUserLeaves = true
                    }
                )
            }
        }
        ContentType.DropDownMenu -> {

            var expanded by remember {
                mutableStateOf(false)
            }
            Box(
                modifier = Modifier
                    //.wrapContentHeight()
                    .weight(weight)
                    .fillMaxHeight()
                    .border(1.dp, if (enabled) Gold else Color.DarkGray)
                    .background(Color.LightGray)
                    .clickable {
                        if (enabled)
                            expanded = true
                        else
                            onNotifyCurCell()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = statusText, fontSize = 12.sp, color = Color.Black)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                dropDownMenuItems.forEachIndexed { index, ddmItem ->
                    DropdownMenuItem(
                        modifier = Modifier.wrapContentSize(),
                        text = {
                            Column {
                                Text(modifier = Modifier.padding(5.dp), text = ddmItem + " (" + dropDownMenuItemsExplanations[index] + ")")
                                Divider()
                            }
                        },
                        onClick = {
                            statusText = ddmItem
                            onConfirm(statusText)
                        }
                    )
                }
            }
        }
        ContentType.DatePicker -> {

            var expanded by remember {
                mutableStateOf(false)
            }

            Box(
                modifier = Modifier
                    .weight(weight)
                    .fillMaxHeight()
                    .border(1.dp, if (enabled) Gold else Color.DarkGray)
                    .background(Color.LightGray)
                    .clickable {
                        if (enabled)
                            expanded = true
                        else
                            onNotifyCurCell()
                    },
                contentAlignment = Alignment.Center
            ){
                Text(text = dateText, color = Color.Black, fontSize = 10.sp, modifier = Modifier.padding(3.dp))
            }

            if (expanded)
                MyDatePickerDialog(
                    onDismiss = { expanded = false }
                ) { date ->
                    Log.d("D_A_T_TAG", "TableCell1: $date")
                    if (date.isEmpty()){
                        context.showErrorToast()
                        return@MyDatePickerDialog
                    }
                    dateText = date
                    Log.d("D_A_T_TAG", "TableCell2: $dateText")
                    onConfirm(dateText)
                }
        }
    }
}