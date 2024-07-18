package ca.georgiancollege.todo_part1

import androidx.recyclerview.widget.RecyclerView
import ca.georgiancollege.todo_part1.databinding.TextRowItemBinding

class TaskViewHolder(private val binding: TextRowItemBinding): RecyclerView.ViewHolder(binding.root)
{
    fun bind(task: Task)
    {
        binding.checkBox.text = task.title
        binding.dueDate.text = task.dueDate
        binding.warningText.text = task.dueDate
    }
}