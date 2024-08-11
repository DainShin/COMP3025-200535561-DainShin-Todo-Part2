package ca.georgiancollege.todo

import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
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

    private val today: Calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    /**
     * Binds the given Task object to the views in the ViewHolder.
     *
     * @param task The Task object to bind to the views.
     */
    fun bind(task: Task) {
        binding.checkBox.text = task.name
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        binding.dueDate.text = task.dueDate?.let { dateFormat.format(it) } ?: ""
        binding.checkBox.isChecked = task.isCompleted

        updateOverdue(task)
        Log.i("듀데이트", task.dueDate.toString())

        binding.checkBox.setOnCheckedChangeListener(null)

        applyStrikethrough(binding.checkBox, task.isCompleted)

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val updatedTask = task.copy(isCompleted = isChecked)
            applyStrikethrough(binding.checkBox, isChecked)
            updateOverdue(updatedTask)
            onItemCheckedChanged(updatedTask)
        }
    }

    /**
     * Applies or removes strikethrough text style based on the checkbox's checked state.
     *
     * @param checkBox The CheckBox to apply the strikethrough style to.
     * @param isChecked The checked state of the CheckBox.
     */
    private fun applyStrikethrough(checkBox: CheckBox, isChecked: Boolean) {
        if (isChecked) {
            checkBox.paintFlags = checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            checkBox.paintFlags = checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    /**
     * Updates the visibility and color of the overdue warning text based on the task's due date and completion status.
     *
     * @param task The Task object to check for overdue status.
     */
    private fun updateOverdue(task: Task) {
        if (task.dueDate?.before(today.time) == true && !task.isCompleted) {
            binding.warningText.visibility = View.VISIBLE
            binding.dueDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
        } else {
            binding.warningText.visibility = View.INVISIBLE
            binding.dueDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
        }
    }
}