package com.example.todolist.extensions

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "Br")

fun Date.format(): String{
    return java.text.SimpleDateFormat("dd/mm/aaaa", locale).format(this)
}

var TextInputLayout.text: String
get() = editText?.text?.toString() ?: ""
set(value) {
    editText?.setText(value)
}