package com.example.listmaker

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {
    fun saveList(list: TaskList) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        // here sharedPrefs is a editor impl
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
//        sharedPrefs.putStringSet(list.name, HashSet<String>())
        sharedPrefs.apply()
    }

    fun readList(): ArrayList<TaskList> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPrefs.all
        val taskLists =  ArrayList<TaskList>()

        for (taskList in contents) {
            val taskItems = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, taskItems)
//            val list = TaskList(taskList.key)
            taskLists.add(list)
        }

        return taskLists
    }
}