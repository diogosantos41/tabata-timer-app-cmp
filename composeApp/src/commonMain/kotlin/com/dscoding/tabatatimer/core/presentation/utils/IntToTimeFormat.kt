package com.dscoding.tabatatimer.core.presentation.utils

fun Int.toTimeFormat(): String {
    val minutes = this / 60
    val seconds = this % 60
    val mm = minutes.toString().padStart(2, '0')
    val ss = seconds.toString().padStart(2, '0')
    return "$mm:$ss"
}