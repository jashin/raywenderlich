package com.example.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TodoListFragment.OnFragmentInteractionListener{

//    private lateinit var todoListRecyclerView: RecyclerView
//    private val listDataManager: ListDataManager = ListDataManager(this)
    private var todoListFragment = TodoListFragment.newInstance()

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 111 // this can be any integer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        val lists = listDataManager.readList()
//        todoListRecyclerView = findViewById(R.id.lists_recyclerview)
//        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
//        todoListRecyclerView.adapter = TodoListAdapter(lists, this)

        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, todoListFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LIST_DETAIL_REQUEST_CODE){
            data?.let {
                var list = data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)!!
//                listDataManager.saveList(list)
//                updateList()
                todoListFragment.saveList(list)
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateTodoListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveButtonTitle)  {
            dialog, _ ->
//                val adapter = todoListRecyclerView.adapter as TodoListAdapter
                val list = TaskList(todoTitleEditText.text.toString())
//                listDataManager.saveList(list)
//                adapter.addList(list)
                todoListFragment.addList(list)
                dialog.dismiss()
                showTaskListItems(list)
        } // the curly braces are for passing a listener
        // and you pass _ because you don't care which button is clicked

        myDialog.create().show()
    }

    private fun showTaskListItems(list: TaskList){
        val taskListItem = Intent(this, DetailActivity::class.java)
        taskListItem.putExtra(INTENT_LIST_KEY, list)
        startActivityForResult(taskListItem, LIST_DETAIL_REQUEST_CODE)
    }

    override fun onTodoListClicked(list: TaskList) {
        showTaskListItems(list)
    }
}
