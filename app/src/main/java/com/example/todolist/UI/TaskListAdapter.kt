package com.example.todolist.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task

class TaskListAdapter :
    ListAdapter<Task, TaskListAdapter, TaskListAdapter.TaskViewHolder>(DiffCallBack()) {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHoder(hoder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))

    }


    class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvData.text = "${item.date} ${item.hour}"
            binding.ivMore.setOnClickListener {
                showPopUp(item)
            }
        }

        private fun showPopUp() {
            val ivMore = binding.ivMore
            val popUpMenu = PopupMenu(ivMore.context, ivMore)
            popUpMenu.menuInflater.inflate(R.menu.popupmenu, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popUpMenu.show()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}

