package com.example.android4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTodoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cancelBtn: Button = view.findViewById(R.id.btnCancelTodo)
        val addBtn: Button = view.findViewById(R.id.btnAddTodo)
        val adapter: TodoAdapter = arguments?.getSerializable("adapter") as TodoAdapter
        cancelBtn.setOnClickListener { _ ->
            val bundle = Bundle().apply {
                putSerializable("adapter", adapter)
            }
            Navigation.findNavController(view).navigate(R.id.addToTodos, bundle)
        }
        addBtn.setOnClickListener { _ ->
            val title: String = view.findViewById<EditText?>(R.id.etAddTodoTitle).text.toString()
            val subtitle: String = view.findViewById<EditText?>(R.id.etAddTodoSubtitle).text.toString()
            val category: Category = parseCategory(view.findViewById<Spinner?>(R.id.spAddTodoSpinner).selectedItem.toString())
            val date: String = view.findViewById<EditText?>(R.id.etAddTodoDueTo).text.toString()
            val importance: Int = view.findViewById<SeekBar?>(R.id.sbAddTodoImportance).progress + 1
            val isPaid: Boolean = view.findViewById<Switch?>(R.id.swAddTodoIsPaid).isChecked
            val description: String = view.findViewById<EditText?>(R.id.etAddTodoDescription).text.toString()
            val newTodo: Todo = Todo(title, subtitle, category, description, date, importance, isPaid)
            if (newTodo.isValid()) {
                val bundle = Bundle().apply {
                    adapter.addTodo(newTodo)
                    putSerializable("adapter", adapter)
                }
                Navigation.findNavController(view).navigate(R.id.addToTodos, bundle)
            } else {
                Toast.makeText(requireContext(), "You must fill all inputs!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTodoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddTodoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun parseCategory(cat: String): Category {
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
}