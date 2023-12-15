package com.example.handbookapp.data.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaField

fun Long.convertMillisToString() = SimpleDateFormat("dd.MM.yyyy").format(Date(this)).toString()

fun convertStringDateToMillis(dateAndTime: String?) =
    SimpleDateFormat("dd.MM.yyyy").parse(dateAndTime).time


fun Context.showErrorToast() = Toast.makeText(this, "Incorrect input", Toast.LENGTH_LONG).show()

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun readInstanceProperty(instance: Any, propertyName: String): Comparable<*>? {
    val property = instance::class.members
        .first { it.name == propertyName } as KProperty1<Any, *>
    return property.get(instance) as Comparable<*>?
}

