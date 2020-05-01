package com.example.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(val lists: ArrayList<TaskList>) : RecyclerView.Adapter<TodoListViewHolder>() {

//    ddprivate var todoList = mutableListOf("Android Development", "House Work", "Errands", "Shopping")
//
//    fun addNewItem(listName: String) {
//        if (listName.isEmpty()) {
//            todoList.add("Todo List " + (todoList.size + 1))
//        } else {
//            todoList.add(listName)
//        }
//        notifyDataSetChanged() // this tells the recyclerview to reolad the data
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder, parent, false)

        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text =  lists[position].name
    }

    fun addList(list: TaskList) {
        lists.add(list)
        notifyItemInserted(lists.size - 1)
    }
}