package ca.georgiancollege.todo_part1

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.georgiancollege.todo_part1.databinding.TextRowItemBinding

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: TaskAdapter
 * File Description: This is a RecyclerView adapter that binds a list of tasks to the RecyclerView, handles item click events, and updates the task status with a strikethrough effect when a task is marked as finished.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: July 21st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
class TaskAdapter(private val onItemClicked: (Task) -> Unit)  :
    ListAdapter<Task, TaskViewHolder>(TaskComparator())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder
    {
        val binding = TextRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int)
    {
        val current = getItem(position)
        holder.bind(current)

        holder.binding.warningText.visibility = if (current.isOverdue) View.VISIBLE else View.GONE
        holder.applyStrikethrough(holder.binding.checkBox, current.isFinished)

        holder.binding.checkBox.setOnCheckedChangeListener { _, isFinished ->
            current.isFinished = isFinished
            holder.applyStrikethrough(holder.binding.checkBox, isFinished)
        }

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
    }

    private fun applyStrikethrough(checkBox: CheckBox, isChecked: Boolean) {
        if (isChecked) {
            checkBox.paintFlags = checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            checkBox.paintFlags = checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}