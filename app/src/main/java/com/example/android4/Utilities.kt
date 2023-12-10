package com.example.android4

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class Utilities {
    companion object {
        fun applyImportanceIcon(iv: ImageView, importance: Int) {
            var resource = R.drawable.ic_not_urgent_todo
            var color = "#008000"
            if (importance in 3..4) {
                resource = R.drawable.ic_normal_todo
                color = "#FFA500"
            } else if (importance == 5) {
                resource = R.drawable.ic_urgent_todo
                color = "#FF0000"
            }
            iv.setImageResource(resource)
            iv.setColorFilter(Color.parseColor(color))
        }

        fun getImportanceString(importance: Int): String {
            if (importance in 3..4) {
                return "Medium"
            } else if (importance == 5) {
                return "Urgent"
            } else {
                return "Not urgent"
            }
        }

        fun applyCategoryIcon(iv: ImageView, category: Category) {
            var resource = when (category) {
                Category.CAR -> {
                    R.drawable.ic_car_image
                }

                Category.LAUNDRY -> {
                    R.drawable.ic_laundry_image
                }

                Category.GROCERIES -> {
                    R.drawable.ic_groceries_image
                }

                Category.CLEANING -> {
                    R.drawable.ic_cleaning_image
                }

                Category.EXERCISE -> {
                    R.drawable.ic_excercise_image
                }

                Category.MEETING -> {
                    R.drawable.ic_meeting_image
                }

                Category.OTHER -> {
                    R.drawable.ic_other_image
                }
            }
            iv.setImageResource(resource)
        }

        fun applyPaidIcon(iv: ImageView, paid: Boolean) {
            if (paid) {
                iv.setImageResource(R.drawable.ic_money_icon)
                iv.setColorFilter(Color.parseColor("#FFD700"))
            } else {
                iv.setImageResource(R.drawable.ic_nomoney_image)
                iv.setColorFilter(Color.parseColor("#000000"))
            }
        }

        fun parseCategory(cat: String): Category {
            return when (cat.lowercase()) {
                "car" -> Category.CAR
                "laundry" -> Category.LAUNDRY
                "groceries" -> Category.GROCERIES
                "cleaning" -> Category.CLEANING
                "exercise" -> Category.EXERCISE
                "meeting" -> Category.MEETING
                "other" -> Category.OTHER
                else -> Category.OTHER
            }
        }

        fun handleDate(manager: FragmentManager, container: TextView) {
            val dataSplit = Utilities.dateTextToDate(container.text.toString())
            val picker = DatePickerFragment(
                container,
                dataSplit["year"],
                dataSplit["month"],
                dataSplit["day"]
            )
            picker.show(manager, "datePicker")
        }

        fun dateTextToDate(dateText: String): Map<String, Int> {
            val split = dateText.split(".")
            return mapOf<String, Int>(
                "day" to split[0].toInt(),
                "month" to split[1].toInt(),
                "year" to split[2].toInt()
            )
        }
    }
}