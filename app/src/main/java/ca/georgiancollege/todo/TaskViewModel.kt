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
                } else {
                    Log.d("TaskViewModel", "Current data: null")
                    m_tasks.postValue(emptyList())
                }
            }
    }

    // details
    fun loadTaskById(id: String) {
        viewModelScope.launch {
            m_task.value = dataManager.getTaskById(id)
        }
    }

    // save
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

    // delete
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dataManager.delete(task)
            loadAllTasks()
        }
    }

    // update
    fun updateTask(task: Task) {
        viewModelScope.launch {
            val taskDocumentRef = firestore.collection("tasks").document(task.id)
            taskDocumentRef.set(task)
                .addOnSuccessListener {
                    Log.d("TaskViewModel", "Task successfully updated!")
                }
                .addOnFailureListener { e ->
                    Log.w("TaskViewModel", "Error updating task", e)
                }
            loadAllTasks()
        }
    }
}