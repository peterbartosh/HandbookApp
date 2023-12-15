package com.example.handbookapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handbookapp.presentation.theme.Gold

@Composable
fun RowScope.TitleCell(
    weight: Float,
    fieldName: String,
    showArrows: Boolean = true,
    onAscClick: (Boolean) -> Unit,
    onDescClick: (Boolean) -> Unit
) {

    var isAscApplied by remember() {
        mutableStateOf(false)
    }
    var isDescApplied by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .height(50.dp)
            .weight(weight)
            .background(Color.LightGray)
            .border(1.dp, Color.Black.copy(alpha = 0.7f)),
    ){

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                modifier = Modifier.padding(top = 3.dp),
                text = fieldName,
                fontSize = 12.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (showArrows)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    modifier = Modifier
                        .weight(weight / 2)
                        .clickable {
                            isAscApplied = !isAscApplied
                            isDescApplied = false
                            onAscClick(isAscApplied)
                        },
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = if (isAscApplied) Gold else Color.DarkGray
                )

                Divider(modifier = Modifier
                    .width(3.dp)
                    .height(20.dp),
                        thickness = 3.dp
                )

                Icon(
                    modifier = Modifier
                        .weight(weight / 2)
                        .clickable {
                            isAscApplied = false
                            isDescApplied = !isDescApplied
                            onDescClick(isDescApplied)
                        },
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = if (isDescApplied) Gold else Color.DarkGray
                )

            }
            else
                Box(modifier = Modifier.weight(weight/2))


        }
    }
}