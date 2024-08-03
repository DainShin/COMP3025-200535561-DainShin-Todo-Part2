package ca.georgiancollege.todo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ca.georgiancollege.todo.databinding.ActivityDetailsBinding
import com.google.android.gms.tasks.Task
import java.util.Calendar
import java.util.Date
import java.util.UUID

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: DetailsActivity
 * File Description: This file is for the Details View where user can edit or delete the item
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: July 21st, 2024
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
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val selectedDateInMillis = calendar.timeInMillis

            // 현재 날짜와 비교하여 과거 날짜 선택 방지
            if (selectedDateInMillis >= today.timeInMillis) {
                selectedDate = calendar.time
                binding.detailsDueDate.text = selectedDate.toString()
            } else {
                Toast.makeText(this, "오늘 이후의 날짜만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.task.observe(this) { task ->
            task?.let {
                binding.editTaskTitle.setText(it.title)
                binding.editDetails.setText(it.details)
                binding.detailsDueDate.text = it.dueDate?.toString() ?: ""
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


        // Calendar
        // hide calendarView by default
        binding.calendarView.visibility = View.GONE

        // click event
        binding.dueDateSection.setOnClickListener {
            // Toggle visibility of CalendarView
            if (binding.calendarView.visibility == View.GONE) {
                binding.calendarView.visibility = View.VISIBLE
            } else {
                binding.calendarView.visibility = View.GONE
            }
        }
    }

    private fun saveTask(selectedDate: Date?)
    {
        val title = binding.editTaskTitle.text.toString()
        val details = binding.editDetails.text.toString()
        val dueDate = selectedDate
        val isOverdue = dueDate?.let { it.before(Date()) } ?: false
        val isFinished = false

        if (title.isNotEmpty())
        {
            val task = Task(
                id = taskId ?: UUID.randomUUID().toString(),
                title = title,
                details = details,
                dueDate = dueDate,
                isOverdue = isOverdue,
                isFinished = isFinished
            )
            viewModel.saveTask(task)
            Toast.makeText(this,"Task Saved",  Toast.LENGTH_SHORT).show()
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