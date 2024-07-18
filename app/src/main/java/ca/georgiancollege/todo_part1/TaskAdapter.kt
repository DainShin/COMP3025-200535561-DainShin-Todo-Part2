package ca.georgiancollege.todo_part1

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import ca.georgiancollege.todo_part1.databinding.TextRowItemBinding

class TaskAdapter(private val taskList: List<Task>, private val itemClickListener: (Task) -> Unit)
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
        viewHolder.binding.checkBox.text = currentItem.title
        viewHolder.binding.dueDate.text = currentItem.dueDate
        viewHolder.binding.warningText.visibility = if (currentItem.isOverdue) View.VISIBLE else View.GONE

        viewHolder.binding.checkBox.isChecked = currentItem.isFinished
        applyStrikethrough(viewHolder.binding.checkBox, currentItem.isFinished)

        viewHolder.binding.checkBox.setOnCheckedChangeListener { _, isFinished ->
            currentItem.isFinished = isFinished
            applyStrikethrough(viewHolder.binding.checkBox, isFinished)
        }

        viewHolder.binding.root.setOnClickListener {
            itemClickListener(currentItem)
        }
    }

    private fun applyStrikethrough(checkBox: CheckBox, isChecked: Boolean) {
        if (isChecked) {
            checkBox.paintFlags = checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            checkBox.paintFlags = checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount() = taskList.size
}