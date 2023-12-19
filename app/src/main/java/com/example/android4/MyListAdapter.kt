package com.example.android4

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MyListAdapter(): ListAdapter<DBTodo, MyListAdapter.LAViewHolder>(TodosDiffCallback()) {

    class LAViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val importanceIcon: ImageView = itemView.findViewById(R.id.ivImportanceIcon)
        val mainTitle: TextView = itemView.findViewById(R.id.tvItemMainTitle)
        val subTitle: TextView = itemView.findViewById(R.id.tvItemSubTitle)
        val categoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
    }
    private class TodosDiffCallback: DiffUtil.ItemCallback<DBTodo>() {
        override fun areItemsTheSame(oldItem: DBTodo, newItem: DBTodo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DBTodo, newItem: DBTodo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LAViewHolder {
        return LAViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LAViewHolder, position: Int) {
        val currentTodo = getItem(position)
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
                        val repo = MyRepository.getInstance(holder.itemView.context)
                        repo.deleteItem(currentTodo)
                        submitList(repo.getData())
                    }.setNegativeButton("Cancel") {dialog, which -> }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                return true
            }
        })
    }

    companion object {
        private var ADAPTER_INSTANCE: MyListAdapter? = null
        fun getInstance(): MyListAdapter {
            if (ADAPTER_INSTANCE == null) {
                ADAPTER_INSTANCE = MyListAdapter()
            }
            return ADAPTER_INSTANCE as MyListAdapter
        }
    }
}