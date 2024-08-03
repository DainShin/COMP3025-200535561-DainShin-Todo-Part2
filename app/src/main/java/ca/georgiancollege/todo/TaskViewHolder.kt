package ca.georgiancollege.todo

import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import ca.georgiancollege.todo.databinding.TextRowItemBinding
import java.text.SimpleDateFormat
import java.util.Locale


/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: TaskViewHolder
 * File Description: This class binds Task data to the views in TextRowItemBinding and applies strikethrough styling based on the task's finished state.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: July 21st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
class TaskViewHolder(
    private val binding: TextRowItemBinding,
    private val onItemClicked: (Task) -> Unit,
    //private val viewModel: TaskViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        binding.checkBox.text = task.title
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        binding.dueDate.text = task.dueDate?.let { dateFormat.format(it) } ?: ""
        binding.checkBox.isChecked = task.isFinished
        binding.warningText.visibility = if (task.isOverdue) View.VISIBLE else View.GONE
        applyStrikethrough(binding.checkBox, task.isFinished)

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isFinished = isChecked
            applyStrikethrough(binding.checkBox, isChecked)
            Log.i("체크박스", "태스크완료")
        }

        itemView.setOnClickListener {
            onItemClicked(task)
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