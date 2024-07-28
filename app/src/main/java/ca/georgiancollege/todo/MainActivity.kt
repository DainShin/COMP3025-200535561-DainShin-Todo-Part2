package ca.georgiancollege.todo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgiancollege.todo.databinding.ActivityMainBinding


/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: MainActivity
 * File Description:  This class manages a RecyclerView displaying a list of tasks and a button for adding new tasks, and navigates to a details screen when a task item is clicked.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: July 21st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = TaskAdapter { task: Task ->
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("taskId", task.id)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView
        binding.firstRecyclerView.layoutManager = LinearLayoutManager(this)

        // Mock data
        val taskList = listOf(
            Task(1,"Make a clinic reservation", "July 2nd, 2024", true, false),
            Task(2,"Submit Android assignment", "Today", false, true),
            Task(3,"Book a flight ticket", "", false, false)
        )

        adapter.submitList(taskList)
        binding.firstRecyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }
    }
}
