package com.example.listmaker

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private val taskList: TaskList) : RecyclerView.Adapter<TaskListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_view_holder, parent, false)

        return TaskListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.tasks.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.taskNumberTextView.text = (position + 1).toString()
        holder.taskNameTextView.text = taskList.tasks[position]
    }

    fun addNewTask(task: String) {
        taskList.tasks.add(task)
    }
}