package com.example.android4

class Todo(
    val title: String,
    val subTitle: String,
    val importance: Int,
) {
}

enum class Importance {
    URGENT,
    MEDIUM,
    NOT_URGENT
}

enum class Subject {
    CAR,
    LAUNDRY,
}