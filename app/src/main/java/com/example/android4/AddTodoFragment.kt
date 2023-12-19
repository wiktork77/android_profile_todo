package com.example.android4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cancelBtn: Button = view.findViewById(R.id.btnCancelTodo)
        val addBtn: Button = view.findViewById(R.id.btnAddTodo)
        val datePicker: ImageView = view.findViewById(R.id.ivDatePicker)
        val dateContainer: TextView = view.findViewById(R.id.tvAddDueToContainer)
        val itemArg: DBTodo? = arguments?.getSerializable("editItem") as DBTodo?

        if (itemArg != null) {
            fillData(view, itemArg)
        }

        datePicker.setOnClickListener {
            Utilities.handleDate(parentFragmentManager, dateContainer)
        }
        cancelBtn.setOnClickListener { _ ->
            if (itemArg != null) {
                val bundle = Bundle().apply {
                    putSerializable("item", itemArg)
                }
                Navigation.findNavController(view).navigate(R.id.addToDescription, bundle)
            } else {
                Navigation.findNavController(view).navigate(R.id.addToTodos)
            }

        }
        addBtn.setOnClickListener { _ ->
            val title: String = view.findViewById<EditText?>(R.id.etAddTodoTitle).text.toString()
            val subtitle: String = view.findViewById<EditText?>(R.id.etAddTodoSubtitle).text.toString()
            val category: Category = Utilities.parseCategory(view.findViewById<Spinner?>(R.id.spAddTodoSpinner).selectedItem.toString())
            val date: String = dateContainer.text.toString()
            val importance: Int = view.findViewById<SeekBar?>(R.id.sbAddTodoImportance).progress + 1
            val isPaid: Boolean = view.findViewById<Switch?>(R.id.swAddTodoIsPaid).isChecked
            val description: String = view.findViewById<EditText?>(R.id.etAddTodoDescription).text.toString()
            val newTodo = DBTodo(title, subtitle, category, description, date, importance, isPaid)
            if (newTodo.isValid()) {
                val repo = MyRepository.getInstance(requireContext())
                if (itemArg != null) {
                    itemArg.changeTo(newTodo)
                    repo.updateItem(itemArg)
                } else {
                    repo.addItem(newTodo)
                }
                MyListAdapter.getInstance().submitList(repo.getData())
                Navigation.findNavController(view).navigate(R.id.addToTodos)
            } else {
                Toast.makeText(requireContext(), "Fill all inputs!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun fillData(view: View, item: DBTodo) {
        view.findViewById<TextView>(R.id.tvAddTodoHeader).text = "Modify ${item.title}"
        view.findViewById<Button>(R.id.btnAddTodo).text = "Save"
        view.findViewById<EditText?>(R.id.etAddTodoTitle).setText(item.title)
        view.findViewById<EditText?>(R.id.etAddTodoSubtitle).setText(item.subTitle)
        view.findViewById<Spinner?>(R.id.spAddTodoSpinner).setSelection(item.category.ordinal)
        view.findViewById<TextView>(R.id.tvAddDueToContainer).setText(item.dueTo)
        view.findViewById<SeekBar?>(R.id.sbAddTodoImportance).progress = item.importance - 1
        view.findViewById<Switch?>(R.id.swAddTodoIsPaid).isChecked = item.paid
        view.findViewById<EditText?>(R.id.etAddTodoDescription).setText(item.description)
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

}