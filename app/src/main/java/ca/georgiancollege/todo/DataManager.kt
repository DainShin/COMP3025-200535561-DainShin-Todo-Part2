package ca.georgiancollege.todo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.util.Log
import kotlinx.coroutines.tasks.await

class DataManager private constructor()
{
    private val db: FirebaseFirestore = Firebase.firestore

    companion object {
        private val TAG = "DataManager"

        @Volatile
        private var m_instance: DataManager? = null

        fun instance(): DataManager {
            if (m_instance == null) {
                synchronized(this) {
                    if (m_instance == null) {
                        m_instance = DataManager()
                    }
                }
            }
            return m_instance!!
        }
    }

    // create
    suspend fun insert(task: Task) {
        try {
            db.collection("tasks").document(task.id).set(task).await()
        }
        catch (e: Exception) {
            Log.e(TAG, "Error inserting Task: ${e.message}", e)
        }
    }

    // update
    suspend fun update(task: Task) {
        try {
            db.collection("tasks").document(task.id).set(task).await()
        }
        catch (e: Exception) {
            Log.e(TAG, "Error updating Task: ${e.message}", e)
        }
    }

    // delete
    suspend fun delete(task: Task) {
        try {
            db.collection("tasks").document(task.id).delete().await()
        }
        catch (e: Exception) {
            Log.e(TAG, "Error deleting Task: ${e.message}", e)
        }
    }

    // get all Tasks
    suspend fun getAllTasks(): List<Task> {
        return try {
            val result = db.collection("tasks").get().await()
            result?.toObjects(Task::class.java) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting all Tasks: ${e.message}", e)
            emptyList()
        }
    }

    // get a Task by ID
    suspend fun getTaskById(id: String) : Task? {
        return try {
            val result = db.collection("tasks").document(id).get().await()
            result?.toObject(Task::class.java)
        }
        catch (e: Exception) {
            Log.e(TAG, "Error getting Task by ID: ${e.message}", e)
            null
        }
    }
}