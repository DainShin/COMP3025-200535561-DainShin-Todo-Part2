package ca.georgiancollege.todo

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: TaskViewModel
 * File Description: TaskViewModel manages loading, saving, updating, and deleting tasks using Firebase Firestore and DataManager, providing real-time data updates to the UI via LiveData
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
class TaskViewModel : ViewModel() {
    private val dataManager = DataManager.instance()

    private val m_tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = m_tasks

    private val m_task = MutableLiveData<Task?>()
    val task: LiveData<Task?> get() = m_task

    // Firestore instance
    private val firestore = FirebaseFirestore.getInstance()
    private var tasksListener: ListenerRegistration? = null

    private val _taskCount = MutableLiveData<Int>()
    val taskCount: LiveData<Int> get() = _taskCount

    /**
     * Loads all tasks from Firestore and updates LiveData objects.
     * Sets up a snapshot listener to observe changes in the tasks collection.
     */
    fun loadAllTasks() {
        // Remove the previous listener if it exists
        tasksListener?.remove()

        tasksListener = firestore.collection("tasks")
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w("TaskViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val taskList = snapshot.toObjects(Task::class.java)
                    m_tasks.postValue(taskList)
                    _taskCount.postValue(taskList.size)
                } else {
                    Log.d("TaskViewModel", "Current data: null")
                    m_tasks.postValue(emptyList())
                    _taskCount.postValue(0)
                }
            }
    }

    /**
     * Loads a specific task by its ID and updates the LiveData object.
     *
     * @param id The ID of the task to load.
     */
    fun loadTaskById(id: String) {
        viewModelScope.launch {
            m_task.value = dataManager.getTaskById(id)
        }
    }

    /**
     * Saves or updates a task to Firestore.
     *
     * @param task The task to save.
     */
    fun saveTask(task: Task) {
        viewModelScope.launch {
            if (task.id.isEmpty()) {
                dataManager.insert(task)
            } else {
                dataManager.update(task)
            }
            loadAllTasks()
        }
    }

    /**
     * Deletes a task from Firestore and reloads all tasks.
     *
     * @param task The task to delete.
     */
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dataManager.delete(task)
            loadAllTasks()
        }
    }

    /**
     * Updates the completion status of a specific task in Firestore.
     *
     * @param task The task with updated completion status.
     */
    fun updateTask(task: Task) {
        viewModelScope.launch {
            val existingTask = dataManager.getTaskById(task.id)
            if (existingTask != null) {
                val updatedTask = existingTask.copy(isCompleted = task.isCompleted)
                firestore.collection("tasks").document(task.id).set(updatedTask)
                    .addOnSuccessListener {
                        Log.d("TaskViewModel", "Task successfully updated!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TaskViewModel", "Error updating task", e)
                    }
            }
        }
    }
}