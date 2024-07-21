package ca.georgiancollege.todo_part1

import androidx.recyclerview.widget.DiffUtil

class TaskComparator : DiffUtil.ItemCallback<Task>()
{
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}