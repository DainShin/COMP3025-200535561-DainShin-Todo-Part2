package ca.georgiancollege.todo

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import ca.georgiancollege.todo.databinding.TextRowItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: TaskViewHolder
 * File Description: This class binds Task data to the views in TextRowItemBinding and applies strikethrough styling based on the task's finished state.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
class TaskViewHolder(private val binding: TextRowItemBinding, private val onItemCheckedChanged: (Task) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val today = Calendar.getInstance()

    fun bind(task: Task) {
        binding.checkBox.text = task.name
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        binding.dueDate.text = task.dueDate?.let { dateFormat.format(it) } ?: ""
        binding.checkBox.isChecked = task.isCompleted

        binding.warningText.visibility = View.INVISIBLE

        if (task.dueDate?.before(today.time) == true && !binding.checkBox.isChecked) {
            binding.warningText.visibility = View.VISIBLE
        } else {
            binding.warningText.visibility = View.INVISIBLE
        }

        binding.checkBox.setOnCheckedChangeListener(null)

        applyStrikethrough(binding.checkBox, task.isCompleted)

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            applyStrikethrough(binding.checkBox, isChecked)
            onItemCheckedChanged(task)
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