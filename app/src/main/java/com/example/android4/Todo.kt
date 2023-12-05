package com.example.android4

import java.io.Serializable

class Todo(
    val title: String,
    val subTitle: String,
    val category: Category,
    val description: String,
    val dueTo: String,
    val importance: Int,
    val paid: Boolean
): Serializable {
    fun isValid(): Boolean {
        return !title.isEmpty() && !subTitle.isEmpty() && !description.isEmpty()
                && !dueTo.isEmpty()
    }
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