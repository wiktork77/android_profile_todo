package com.example.android4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val data: MutableList<Todo>
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val importanceIcon: ImageView = itemView.findViewById(R.id.ivImportanceIcon)
        val mainTitle: TextView = itemView.findViewById(R.id.tvItemMainTitle)
        val subTitle: TextView = itemView.findViewById(R.id.tvItemSubTitle)
        val categoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
        val date: TextView = itemView.findViewById(R.id.tvItemDate)
        val paidIcon: ImageView = itemView.findViewById(R.id.ivPaid)
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
            date.text = currentTodo.dueTo
            applyImportanceIcon(importanceIcon, currentTodo.importance)
            applyPaidIcon(paidIcon, currentTodo.paid)
            applyCategoryIcon(categoryIcon, currentTodo.category)
        }

        holder.itemView.setOnClickListener { _ ->
            println("nr $position")
            data.removeAt(position)
            notifyDataSetChanged()
        }
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

    private fun applyPaidIcon(iv: ImageView, paid:Boolean) {
        if (paid) {
            iv.setImageResource(R.drawable.ic_money_icon)
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
}