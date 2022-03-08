package com.example.todolist.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.todolist.dataSource.TaskDataSource
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        insertListeners()

    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java))
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
            Log.e("tag","listenerEdit: $it")

        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
            Log.e("tag","listenerDelete $it")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK){
            binding.rvTasks.adapter = adapter
        }

    }

    private fun updateList(){
        val list = TaskDataSource.getList()
        if(list.isEmpty()){
            binding.inIn.emptyState.visibility = View. VISIBLE
        }else{
            View.GONE
        }

        adapter.subimitList(TaskDataSource.getList())

    }
    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}