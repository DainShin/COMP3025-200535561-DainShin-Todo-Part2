package ca.georgiancollege.todo

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch


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

    // Function to load a specific TVShow by ID from the DataManager
    fun loadTaskById(id: String) {
        viewModelScope.launch {
            m_task.value = dataManager.getTaskById(id)
        }
    }

    // Function to save or update a TVShow in the DataManager
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

    // Function to delete a TVShow from the DataManager
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dataManager.delete(task)
            loadAllTasks()
        }
    }

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