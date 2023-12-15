package com.example.handbookapp.data.utils

import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

enum class PojoType{
    Order, OrderProduct, NotSpecified;
}

enum class ContentType{
    TextField, DropDownMenu, DatePicker;
}
