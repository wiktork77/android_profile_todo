package com.example.android4

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescriptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var data: Todo
    private var position: Int? = 0

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getSerializable("item") as Todo
        position = arguments?.getInt("position")
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
        (views["titleEdit"] as EditText).setText(data.title)
        (views["subtitle"] as TextView).text = data.subTitle
        (views["subtitleEdit"] as EditText).setText(data.subTitle)
        (views["description"] as TextView).text = data.description
        (views["categoryDesc"] as Spinner).setSelection(Category.valueOf(data.category.toString()).ordinal)
        Utilities.applyCategoryIcon((views["categoryIcon"] as ImageView), data.category)
        (views["importanceDesc"] as TextView).text = Utilities.getImportanceString(data.importance)
        Utilities.applyImportanceIcon((views["importanceIcon"] as ImageView), data.importance)
        (views["importanceBar"] as SeekBar).progress = data.importance - 1
        handlePaid(data.paid, views["paidIcon"] as ImageView, views["paidSwitch"] as Switch, views["paidText"] as TextView)
        (views["date"] as TextView).text = data.dueTo
    }

    private fun bindViews(view: View) {
        val views = retrieveViews(view)
        val adapter: TodoAdapter = arguments?.getSerializable("adapter") as TodoAdapter




        (views["dateText"] as TextView).setOnClickListener { _ ->
            Utilities.handleDate(parentFragmentManager, views["dateText"] as TextView)
        }

        (views["dateImg"] as ImageView).setOnClickListener{ _ ->
            Utilities.handleDate(parentFragmentManager, views["dateText"] as TextView)
        }

        (views["categoryDesc"] as Spinner).onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, option: Int, p3: Long) {
                Utilities.applyCategoryIcon((views["categoryIcon"] as ImageView), Category.values()[option])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        (views["importanceBar"] as SeekBar).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, importance: Int, p2: Boolean) {
                (views["importanceDesc"] as TextView).text = Utilities.getImportanceString(importance + 1)
                Utilities.applyImportanceIcon((views["importanceIcon"] as ImageView), importance + 1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        (views["paidSwitch"] as Switch).setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, checked: Boolean) {
                handlePaid(checked, views["paidIcon"] as ImageView, views["paidSwitch"] as Switch, views["paidText"] as TextView)
            }
        })

        (views["backBtn"] as Button).setOnClickListener { _ ->
            val bundle = Bundle().apply {
                putSerializable("adapter", adapter)
            }
            Navigation.findNavController(view).navigate(R.id.descriptionToTodo, bundle)
        }

        (views["saveBtn"] as Button).setOnClickListener { _ ->
            val updatedViews = retrieveViews(view)
            val updatedTodo = Todo(
                (updatedViews["titleEdit"] as TextView).text.toString(),
                (updatedViews["subtitleEdit"] as TextView).text.toString(),
                Utilities.parseCategory((updatedViews["categoryDesc"] as Spinner).selectedItem.toString()),
                (updatedViews["description"] as TextView).text.toString(),
                (updatedViews["date"] as TextView).text.toString(),
                (updatedViews["importanceBar"] as SeekBar).progress + 1,
                (updatedViews["paidSwitch"] as Switch).isChecked
            )
            adapter.editTodo(position!!, updatedTodo)
            val bundle = Bundle().apply {
                putSerializable("adapter", adapter)
            }
            Navigation.findNavController(view).navigate(R.id.descriptionToTodo, bundle)
        }
    }

    private fun retrieveViews(view: View): Map<String, View> {
        return mapOf<String, View>(
            "title" to view.findViewById(R.id.tvTitle),
            "titleEdit" to view.findViewById(R.id.etTitleDesc),
            "subtitle" to view.findViewById(R.id.tvSubtitle),
            "subtitleEdit" to view.findViewById(R.id.etSubtitleDesc),
            "description" to view.findViewById(R.id.etDescription),
            "categoryDesc" to view.findViewById(R.id.spCategoryDesc),
            "categoryIcon" to view.findViewById(R.id.ivCategoryDesc),
            "importanceDesc" to view.findViewById(R.id.tvImportanceDesc),
            "importanceIcon" to view.findViewById(R.id.ivImportanceDesc),
            "importanceBar" to view.findViewById(R.id.sbImportanceBarDesc),
            "paidSwitch" to view.findViewById(R.id.swPaidDesc),
            "paidIcon" to view.findViewById(R.id.ivPaidDesc),
            "paidText" to view.findViewById(R.id.tvPaidDesc),
            "date" to view.findViewById(R.id.tvDueToDesc),
            "dateImg" to view.findViewById(R.id.ivDueToDesc),
            "dateText" to view.findViewById(R.id.tvDueToDesc),
            "backBtn" to view.findViewById(R.id.btnBackDesc),
            "saveBtn" to view.findViewById(R.id.btnSaveDesc)
        )
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DescriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
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