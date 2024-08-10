package ca.georgiancollege.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ca.georgiancollege.todo.databinding.TextRowItemBinding

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: TaskAdapter
 * File Description: This is a RecyclerView adapter that binds a list of tasks to the RecyclerView, handles item click events, and updates the task status with a strikethrough effect when a task is marked as finished.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
class TaskListAdapter(private val onItemClicked: (Task) -> Unit, private val onItemCheckedChanged: (Task) -> Unit) : ListAdapter<Task, TaskViewHolder>(TaskComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TextRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, onItemCheckedChanged)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
    }
}
