package com.example.android4

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DatePickerFragment(
    val displayContainer: TextView,
    var year: Int?,
    var month: Int?,
    var day: Int?
): DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        if (year == null)
            year = c.get(Calendar.YEAR)
        if (month == null)
            month = c.get(Calendar.MONTH) + 1
        if (day == null)
            day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year!!, month!! - 1, day!!)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        // y, m, d
        val day = if (p3.toString().length < 2) {
            "0$p3"
        } else {
            p3.toString()
        }

        val month = if ((p2 + 1).toString().length < 2) {
            "0${p2+1}"
        } else {
            (p2 + 1).toString()
        }

        val year = p1.toString()

        val dateValue = "$day.$month.$year"
        displayContainer.text = dateValue
    }

}