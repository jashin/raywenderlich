package com.example.listmaker

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class TaskDetailFragment : Fragment() {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var listDataManager: ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)

        arguments?.let {
            val args = TaskDetailFragmentArgs.fromBundle(it)
            list = listDataManager.readList().filter { list -> list.name == args.listString}[0]
        }

        activity?.let {
            taskListRecyclerView = view.findViewById(R.id.task_list_recyclerview)
            taskListRecyclerView.layoutManager = LinearLayoutManager(it)
            taskListRecyclerView.adapter = TaskListAdapter(list)
            it.toolbar?.title = list.name

            add_task_button.setOnClickListener {
                showCreateDialog()
            }
        }
    }

    fun addNewTask(task: String) {
        val taskListAdapter = taskListRecyclerView.adapter as TaskListAdapter
        taskListAdapter.addNewTask(task)
    }

    companion object {
        const val BUNDLE_KEY = "list1"
        fun newInstance(list: TaskList) : TaskDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_KEY, list)
            val fragment = TaskDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    fun showCreateDialog() {
        val dialogTitle = "What's your task"
        val positiveButtonTitle = "Create"
        activity?.let {
            val myDialog = AlertDialog.Builder(it)
            var editText = EditText(it)
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            myDialog.setTitle(dialogTitle)
            myDialog.setView(editText)

            myDialog.setPositiveButton(positiveButtonTitle) {
                    dialog, _ ->
                val task = editText.text.toString()
//                val adapter =  taskListRecyclerView.adapter as TaskListAdapter
//                adapter.addNewTask(task)
                list.tasks.add(task)
                listDataManager.saveList(list)
                dialog.dismiss()
            }
            myDialog.create().show()
        }

    }
}
