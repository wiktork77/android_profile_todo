package com.example.android4

class Todo(
    val title: String,
    val subTitle: String,
    val category: Category,
    val description: String,
    val dueTo: String,
    val importance: Int,
    val paid: Boolean
) {
}

enum class Importance {
    URGENT,
    MEDIUM,
    NOT_URGENT
}

enum class Category {
    CAR,
    LAUNDRY,
    GROCERIES,
    CLEANING,
    EXERCISE,
    MEETING,
    OTHER
}