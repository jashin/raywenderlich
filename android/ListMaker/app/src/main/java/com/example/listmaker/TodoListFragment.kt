package com.example.listmaker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {

    private lateinit var todoListRecyclerView: RecyclerView
    private lateinit var listDataManager: ListDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.let {
            listDataManager = ViewModelProviders.of(it).get(ListDataManager::class.java)
        }
        val lists = listDataManager.readList()
        todoListRecyclerView = view.findViewById(R.id.lists_recyclerview)
        todoListRecyclerView.layoutManager = LinearLayoutManager(activity)
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }
    }

    interface OnFragmentInteractionListener {
        fun onTodoListClicked(list: TaskList)
    }

    companion object {
        @JvmStatic
        fun newInstance() : TodoListFragment {
            return TodoListFragment()
        }
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
    }

    fun addList(list: TaskList) {
        listDataManager.saveList(list)
        val todoAdapter = todoListRecyclerView.adapter as TodoListAdapter
        todoAdapter.addList(list)
    }

    private fun updateList() {
        var lists = listDataManager.readList()
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateList()
    }

    private fun showCreateTodoListDialog() {
        activity?.let {
            val dialogTitle = getString(R.string.name_of_list)
            val positiveButtonTitle = getString(R.string.create_list)
            val myDialog = AlertDialog.Builder(it)
            val todoTitleEditText = EditText(it)
            todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            myDialog.setTitle(dialogTitle)
            myDialog.setView(todoTitleEditText)

            myDialog.setPositiveButton(positiveButtonTitle)  {
                    dialog, _ ->
//                val adapter = todoListRecyclerView.adapter as TodoListAdapter
                val list = TaskList(todoTitleEditText.text.toString())
//                listDataManager.saveList(list)
//                adapter.addList(list)
                addList(list)
                dialog.dismiss()
                showTaskListItems(list)
            } // the curly braces are for passing a listener
            // and you pass _ because you don't care which button is clicked

            myDialog.create().show()
        }
    }

    private fun showTaskListItems(list: TaskList){
        view?.let {
            val action = TodoListFragmentDirections.actionTodoListFragmentToTaskDetailFragment(list.name)
            it.findNavController().navigate(action)
        }
    }
}
