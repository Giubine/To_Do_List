package com.example.todolist.UI

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.dataSource.TaskDataSource
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tipTitle.text = it.title
                binding.tipData.text = it.date
                binding.tipHora.text = it.hour
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.tipData.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tipData.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")


            binding.tipHora.editText?.setOnClickListener {
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .build()
                timePicker.addOnPositiveButtonClickListener {
                    val minute =
                        if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                    val hour =
                        if (timePicker.minute in 0..9) "0${timePicker.hour}" else timePicker.hour
                    binding.tipHora.text = "$hour:$minute"
                }
                timePicker.show(supportFragmentManager, null)
            }
            binding.btCancel.setOnClickListener {
                finish()
            }
            binding.btNewTask.setOnClickListener {
                val task = Task(
                    title = binding.tipTitle.text,
                    date = binding.tipData.text,
                    hour = binding.tipHora.text,
                    id = intent . getItemExtra (TASK_ID, 0)
                )
                TaskDataSource.insertTask(task)
                setResult(Activity.RESULT_OK)
                finish()
                Log.e("tag", "insertListeners: " + TaskDataSource.getList())
            }

        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}