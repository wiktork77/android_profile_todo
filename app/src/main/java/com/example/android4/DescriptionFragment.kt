package com.example.android4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
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
        val title: TextView = view.findViewById(R.id.tvTitle)
        val subtitle: TextView = view.findViewById(R.id.tvSubtitle)
        val description: TextView = view.findViewById(R.id.tvDescription)
        val categoryDesc: TextView = view.findViewById(R.id.tvCategoryDesc)
        val categoryIcon: ImageView = view.findViewById(R.id.ivCategoryDescIcon)
        val importanceDesc: TextView = view.findViewById(R.id.tvImportance)
        val importanceIcon: ImageView = view.findViewById(R.id.ivImportanceIconDesc)
        val importanceBar: RatingBar = view.findViewById(R.id.rbItemImportance)
        val backBtn: Button = view.findViewById(R.id.btnBackDesc)
        val paid: Switch = view.findViewById(R.id.swPaid)
        val date: TextView = view.findViewById(R.id.tvDateDesc)
        title.text = data.title
        subtitle.text = data.subTitle
        description.text = data.description
        categoryDesc.text = data.category.toString()
        applyCategoryIcon(categoryIcon, data.category)
        importanceDesc.text = getImportanceString(data.importance)
        applyImportanceIcon(importanceIcon, data.importance)
        importanceBar.rating = data.importance.toFloat()
        if (data.paid) {
            paid.isChecked = true
        }
        date.text = data.dueTo
        backBtn.setOnClickListener { _ ->
            val adapter: TodoAdapter = arguments?.getSerializable("adapter") as TodoAdapter
            val bundle = Bundle().apply {
                putSerializable("adapter", adapter)
            }
            Navigation.findNavController(view).navigate(R.id.descriptionToTodo, bundle)
        }

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

    private fun applyCategoryIcon(iv: ImageView, category: Category) {
        var resource = when (category) {
            Category.CAR -> {R.drawable.ic_car_image}
            Category.LAUNDRY -> {R.drawable.ic_laundry_image}
            Category.GROCERIES -> {R.drawable.ic_groceries_image}
            Category.CLEANING -> {R.drawable.ic_cleaning_image}
            Category.EXERCISE -> {R.drawable.ic_excercise_image}
            Category.MEETING -> {R.drawable.ic_meeting_image}
            Category.OTHER -> {R.drawable.ic_other_image}
        }
        iv.setImageResource(resource)
    }
    private fun applyImportanceIcon(iv: ImageView, importance: Int) {
        var resource = R.drawable.ic_not_urgent_todo
        if (importance in 3..4) {
            resource = R.drawable.ic_normal_todo
        } else if (importance == 5) {
            resource = R.drawable.ic_urgent_todo
        }
        iv.setImageResource(resource)
    }

    private fun getImportanceString(importance: Int): String {
        if (importance in 2..4) {
            return "Medium"
        } else if (importance == 5) {
            return "Urgent"
        } else {
            return "Not urgent"
        }
    }

}