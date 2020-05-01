package com.example.listmaker

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager

class ListDataManager(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

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