package com.example.handbookapp.data.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.reflect.KProperty1

fun Long.convertMillisToString() = SimpleDateFormat("dd.MM.yyyy").format(Date(this)).toString()

fun convertStringDateToMillis(dateAndTime: String?) =
    dateAndTime?.let {
        SimpleDateFormat("dd.MM.yyyy").parse(it).time
    }

fun Context.showErrorToast() = Toast.makeText(this, "Введены некорректные данные", Toast.LENGTH_LONG).show()

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun readInstanceProperty(instance: Any, propertyName: String): Comparable<*>? {
    val property = instance::class.members
        .first { it.name == propertyName } as KProperty1<Any, *>
    return property.get(instance) as Comparable<*>?
}

