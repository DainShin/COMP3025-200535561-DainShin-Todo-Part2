package ca.georgiancollege.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgiancollege.todo.databinding.ActivityMainBinding

import com.google.firebase.firestore.FirebaseFirestore


/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: MainActivity
 * File Description:  This class manages a RecyclerView displaying a list of tasks and a button for adding new tasks, and navigates to a details screen when a task item is clicked.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()

    private lateinit var dataManager: DataManager

    // Adapter for the RecyclerView, with a click listener to open the DetailsActivity
    private val adapter = TaskListAdapter(
        onItemClicked = { task: Task ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("taskId", task.id)
            }
            startActivity(intent)
        },
        onItemCheckedChanged = { task: Task ->
            viewModel.updateTask(task) // call ViewModel's updateTask function
        }
    )

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The saved state of the activity, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore and DataManager
        FirebaseFirestore.setLoggingEnabled(true)
        dataManager = DataManager.instance()

        // RecyclerView
        binding.firstRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.firstRecyclerView.adapter = adapter

        viewModel.tasks.observe(this) { tasks ->
            adapter.submitList(tasks)
        }

        viewModel.taskCount.observe(this) { count ->
            binding.totalTask.text = "$count task(s)"
        }

        viewModel.loadAllTasks()

        binding.addButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Called when the activity is resumed and reloads s all tasks when the activity comes back to the foreground
     *
     */
    override fun onResume()
    {
        super.onResume()
        viewModel.loadAllTasks()
    }
}
