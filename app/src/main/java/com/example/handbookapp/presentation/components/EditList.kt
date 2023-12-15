package com.example.handbookapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun EditList(
    enabled: Boolean = true,
    idState : MutableState<String>,
    brandState : MutableState<String>,
    cashPriceState: MutableState<String>,
    cashlessPriceState : MutableState<String>,
    isOnHandState : MutableState<Boolean>
) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {

        InputField(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth(0.7f),
            valueState = idState,
            keyboardType = KeyboardType.Number,
            label = "Порядковый номер",
            enabled = enabled
        )

        Spacer(modifier = Modifier.height(3.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {

            InputField(
                modifier = Modifier
                    .padding(3.dp)
                    .weight(0.5f),
                valueState = cashPriceState,
                keyboardType = KeyboardType.Number,
                label = "Цена (нал.)",
                enabled = true
            )

            Spacer(modifier = Modifier.width(5.dp))


            InputField(
                modifier = Modifier
                    .padding(3.dp)
                    .weight(0.5f),
                valueState = cashlessPriceState,
                keyboardType = KeyboardType.Number,
                label = "Цена (безнал.)",
                enabled = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        InputField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            valueState = brandState,
            label = "Брэнд",
            enabled = true
        )

        Spacer(modifier = Modifier.height(3.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                enabled = enabled,
                selected = isOnHandState.value,
                onClick = { isOnHandState.value = !isOnHandState.value }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = if (isOnHandState.value) "В наличии:" else "Нет в наличии")
        }

        Spacer(modifier = Modifier.height(3.dp))

        //SexPicker(selectedIndState = sexState)

    }
}

