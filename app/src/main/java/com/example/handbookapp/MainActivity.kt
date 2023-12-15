package com.example.handbookapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.handbookapp.data.utils.readInstanceProperty
import com.example.handbookapp.domain.Order
import com.example.handbookapp.domain.Pojo
import com.example.handbookapp.presentation.MainScreen
import com.example.handbookapp.presentation.theme.HandbookAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandbookAppTheme {
                MainScreen()
            }
        }
    }
}