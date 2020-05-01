package com.example.listmaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailFragment : Fragment() {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getParcelable(BUNDLE_KEY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        taskListRecyclerView = view.findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(activity)
        taskListRecyclerView.adapter = TaskListAdapter(list)
    }

    fun addNewTask(task: String) {
        val taskListAdapter = taskListRecyclerView.adapter as TaskListAdapter
        taskListAdapter.addNewTask(task)
    }

    companion object {
        const val BUNDLE_KEY = "list1"
        fun newInstance(list: TaskList) : DetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_KEY, list)
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
