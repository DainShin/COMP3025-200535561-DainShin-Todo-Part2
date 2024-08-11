package ca.georgiancollege.todo

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ca.georgiancollege.todo.databinding.ActivityDetailsBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: DetailsActivity
 * File Description: This file is for the Details View where user can edit or delete the item
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * App Description: This is a To do List application with which user can manage and organise schedule
 */
class DetailsActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: TaskViewModel by viewModels()

    private lateinit var dataManager: DataManager
    private var taskId: String? = null
    private val today = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataManager = DataManager.instance()

        taskId = intent.getStringExtra("taskId")

        if(taskId != null)
        {
            viewModel.loadTaskById(taskId!!)
        }
        else
        {
            binding.deleteButton.visibility = View.GONE
        }

        // date
        val calendar: Calendar = Calendar.getInstance()
        var selectedDate: Date? = null
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.time
            binding.detailsDueDate.text = selectedDate?.let { dateFormat.format(it) }

            // Check if the selected date is before today
            if (selectedDate != null) {
                if (selectedDate!!.before(today.time)) {
                    binding.detailsWarning.visibility = View.VISIBLE

                } else {
                    binding.detailsWarning.visibility = View.GONE
                }
            }
        }

        binding.detailsWarning.visibility = View.GONE

        viewModel.task.observe(this) { task ->
            task?.let {
                binding.editTaskTitle.setText(it.name)
                binding.editDetails.setText(it.notes)
                binding.detailsDueDate.text = it.dueDate?.let { date -> dateFormat.format(date) } ?: ""

                // If the due date is before today -> warning message
                if (it.dueDate != null) {
                    val dueDateCalendar = Calendar.getInstance().apply { time = it.dueDate }
                    if (dueDateCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        dueDateCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                        binding.detailsWarning.visibility = View.GONE
                    } else if (it.dueDate.before(today.time) && !it.isCompleted) {
                        binding.detailsWarning.visibility = View.VISIBLE

                    } else {
                        binding.detailsWarning.visibility = View.GONE
                    }
                } else {
                    binding.detailsWarning.visibility = View.GONE
                }

                // isFinished
                binding.checkbox.isChecked = it.isCompleted
                if (it.isCompleted) {
                    binding.editTaskTitle.paintFlags = binding.editTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    binding.editTaskTitle.paintFlags = binding.editTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }

        // checkbox
        binding.checkbox.setOnCheckedChangeListener {_, isChecked ->
            if(isChecked) {
                binding.editTaskTitle.paintFlags = binding.editTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                viewModel.task.value?.isCompleted = true
            } else {
                binding.editTaskTitle.paintFlags = binding.editTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                viewModel.task.value?.isCompleted = false
            }
        }

        binding.saveButton.setOnClickListener {
            saveTask(selectedDate)
        }

        binding.deleteButton.setOnClickListener {
            deleteTask()
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }

        // Calendar: hide calendarView by default
        binding.calendarView.visibility = View.GONE

        // Click event
        binding.dueDateSection.setOnClickListener {
            // Toggle visibility of CalendarView
            if (binding.calendarView.visibility == View.GONE) {
                binding.calendarView.visibility = View.VISIBLE
            } else {
                binding.calendarView.visibility = View.GONE
            }
        }
    }

    private fun saveTask(selectedDate: Date?) {
        val name = binding.editTaskTitle.text.toString()
        val notes = binding.editDetails.text.toString()

        if (name.isNotEmpty()) {
            val dueDate = selectedDate ?: today.time
            val isCompleted = binding.checkbox.isChecked

            val task = Task(
                id = taskId ?: UUID.randomUUID().toString(),
                name = name,
                notes = notes,
                dueDate = dueDate,
                hasDueDate = true,
                isCompleted = isCompleted
            )

            viewModel.saveTask(task)
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun deleteTask()
    {
        taskId?.let { _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this Task?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.task.value?.let {
                        viewModel.deleteTask(it)
                        Toast.makeText(this, "TV Show Deleted", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}