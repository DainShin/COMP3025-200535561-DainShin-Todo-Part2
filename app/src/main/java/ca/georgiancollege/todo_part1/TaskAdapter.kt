package ca.georgiancollege.todo_part1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.georgiancollege.todo_part1.databinding.TextRowItemBinding

class TaskAdapter(private val taskList: List<Task>)
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>()
{
    class ViewHolder(val binding: TextRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = TextRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        val currentItem = taskList[position]
        viewHolder.binding.title.text = currentItem.title
        viewHolder.binding.dueDate.text = currentItem.dueDate
        viewHolder.binding.warningText.visibility = if (currentItem.isOverdue) View.VISIBLE else View.GONE
    }


    override fun getItemCount() = taskList.size
}