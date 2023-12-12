package com.example.android4

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class TodoAdapter(
    private val data: MutableList<DBTodo>,
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(), Serializable {


    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val importanceIcon: ImageView = itemView.findViewById(R.id.ivImportanceIcon)
        val mainTitle: TextView = itemView.findViewById(R.id.tvItemMainTitle)
        val subTitle: TextView = itemView.findViewById(R.id.tvItemSubTitle)
        val categoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
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
            Utilities.applyImportanceIcon(importanceIcon, currentTodo.importance)
            Utilities.applyCategoryIcon(categoryIcon, currentTodo.category)
        }

        holder.itemView.setOnClickListener { _ ->
            val bundle = Bundle().apply {
                putSerializable("item", currentTodo)
            }
            Navigation.findNavController(holder.itemView.parent as View).navigate(R.id.todoToDescription, bundle)
        }

        holder.itemView.setOnLongClickListener(object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                val builder: AlertDialog.Builder = AlertDialog.Builder(
                    requireNotNull(p0?.context)
                )
                builder.setTitle("Are you sure you want to delete?")
                    .setPositiveButton("Yes") {dialog, which ->
                        removeItem(currentTodo, position, holder.itemView.context)
                    }.setNegativeButton("Cancel") {dialog, which -> }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                return true
            }
        })
    }

    private fun removeItem(item: DBTodo, index: Int, context:Context) {
        data.removeAt(index)
        val repo = MyRepository.getInstance(context)
        repo.deleteItem(item)
        notifyItemRemoved(index)
    }

}