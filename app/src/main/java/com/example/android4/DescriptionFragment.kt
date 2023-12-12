package com.example.android4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.navigation.Navigation
import me.zhanghai.android.materialratingbar.MaterialRatingBar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DescriptionFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var data: DBTodo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getSerializable("item") as DBTodo
        setupViews(view)
        bindViews(view)
    }


    private fun handlePaid(paid: Boolean, iv:ImageView, sw: Switch, tv: TextView) {
        Utilities.applyPaidIcon(iv, paid)
        sw.isChecked = paid
        if (paid) {
            tv.text = "Yes"
        } else {
            tv.text = "No"
        }
    }

    private fun setupViews(view: View) {
        val views = retrieveViews(view)

        (views["title"] as TextView).text = data.title
        (views["titleEdit"] as TextView).text= data.title
        (views["subtitle"] as TextView).text = data.subTitle
        (views["subtitleEdit"] as TextView).text = data.subTitle
        (views["description"] as TextView).text = data.description
        (views["categoryDesc"] as TextView).text = data.category.toString()
        Utilities.applyCategoryIcon((views["categoryIcon"] as ImageView), data.category)
        (views["importanceDesc"] as TextView).text = Utilities.getImportanceString(data.importance)
        Utilities.applyImportanceIcon((views["importanceIcon"] as ImageView), data.importance)
        (views["importanceBar"] as MaterialRatingBar).rating = data.importance.toFloat()
        handlePaid(data.paid, views["paidIcon"] as ImageView, views["paidSwitch"] as Switch, views["paidText"] as TextView)
        (views["date"] as TextView).text = data.dueTo
    }

    private fun retrieveViews(view: View): Map<String, View> {
        return mapOf<String, View>(
            "title" to view.findViewById(R.id.tvTitle),
            "titleEdit" to view.findViewById(R.id.tvTitleDesc),
            "subtitle" to view.findViewById(R.id.tvSubtitle),
            "subtitleEdit" to view.findViewById(R.id.tvSubtitleDesc),
            "description" to view.findViewById(R.id.tvDescription),
            "categoryDesc" to view.findViewById(R.id.tvCategoryDesc),
            "categoryIcon" to view.findViewById(R.id.ivCategoryDesc),
            "importanceDesc" to view.findViewById(R.id.tvImportanceDesc),
            "importanceIcon" to view.findViewById(R.id.ivImportanceDesc),
            "importanceBar" to view.findViewById(R.id.rbImportanceBar),
            "paidSwitch" to view.findViewById(R.id.swPaidDesc),
            "paidIcon" to view.findViewById(R.id.ivPaidDesc),
            "paidText" to view.findViewById(R.id.tvPaidDesc),
            "date" to view.findViewById(R.id.tvDueToDesc),
            "dateImg" to view.findViewById(R.id.ivDueToDesc),
            "dateText" to view.findViewById(R.id.tvDueToDesc),
            "backBtn" to view.findViewById(R.id.btnBackDesc),
            "modifyBtn" to view.findViewById(R.id.btnModifyDesc),
        )
    }

    private fun bindViews(view: View) {
        val views = retrieveViews(view)

        (views["backBtn"] as Button).setOnClickListener { _ ->
            Navigation.findNavController(view).navigate(R.id.descriptionToTodo)
        }
        (views["modifyBtn"] as Button).setOnClickListener { _ ->
            val bundle = Bundle().apply {
                putSerializable("editItem", data)
            }
            Navigation.findNavController(view).navigate(R.id.descriptionToAdd, bundle)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}