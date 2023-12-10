package com.example.android4

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class TodoAdapter(
    private val data: MutableList<Todo>
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(), Serializable {


    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val importanceIcon: ImageView = itemView.findViewById(R.id.ivImportanceIcon)
        val mainTitle: TextView = itemView.findViewById(R.id.tvItemMainTitle)
        val subTitle: TextView = itemView.findViewById(R.id.tvItemSubTitle)
        val categoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
//        val date: TextView = itemView.findViewById(R.id.tvItemDate)
//        val paidIcon: ImageView = itemView.findViewById(R.id.ivPaid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = data[position]
        holder.apply {
            mainTitle.text = currentTodo.title
            subTitle.text = currentTodo.subTitle
//            date.text = currentTodo.dueTo
            Utilities.applyImportanceIcon(importanceIcon, currentTodo.importance)
//            applyPaidIcon(paidIcon, currentTodo.paid)
            applyCategoryIcon(categoryIcon, currentTodo.category)
        }

        holder.itemView.setOnClickListener { _ ->
            val bundle = Bundle().apply {
                putInt("position", position)
                putSerializable("item", currentTodo)
                putSerializable("adapter", this@TodoAdapter)
            }
            Navigation.findNavController(holder.itemView.parent as View).navigate(R.id.todoToDescription, bundle)
        }
    }

    fun addTodo(todo: Todo) {
        data.add(todo)
        notifyDataSetChanged()
    }

    fun editTodo(position: Int, todo: Todo) {
        data[position] = todo
        notifyDataSetChanged()
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
}