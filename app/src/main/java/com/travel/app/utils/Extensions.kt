package com.travel.app.utils

inline fun <reified T> List<*>.safeTypedList(): List<T>? {
    return if (all { it is T }) {
        @Suppress("UNCHECKED_CAST")
        this as List<T>
    } else {
        null
    }
}