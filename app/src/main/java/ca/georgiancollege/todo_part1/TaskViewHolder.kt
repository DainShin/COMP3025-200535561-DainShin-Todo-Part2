package ca.georgiancollege.todo_part1

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import ca.georgiancollege.todo_part1.databinding.TextRowItemBinding

class TaskViewHolder(val binding: TextRowItemBinding): RecyclerView.ViewHolder(binding.root)
{
    fun bind(task: Task)
    {
        binding.checkBox.text = task.title
        binding.dueDate.text = task.dueDate
        binding.warningText.visibility = if (task.isOverdue) View.VISIBLE else View.GONE
        binding.checkBox.isChecked = task.isFinished
        applyStrikethrough(binding.checkBox, task.isFinished)
    }

    fun applyStrikethrough(checkBox: CheckBox, isChecked: Boolean) {
        if (isChecked) {
            checkBox.paintFlags = checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            checkBox.paintFlags = checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}