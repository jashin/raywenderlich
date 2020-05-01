package com.example.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListViewHolder(taskView : View) : RecyclerView.ViewHolder(taskView) {
    var taskNumberTextView = taskView.findViewById(R.id.taskNumber) as TextView
    var taskNameTextView = taskView.findViewById(R.id.taskName) as TextView
}