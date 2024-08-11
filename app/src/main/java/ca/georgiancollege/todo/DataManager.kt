package ca.georgiancollege.todo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.util.Log
import kotlinx.coroutines.tasks.await

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: DataManager
 * File Description: This is a singleton class that manages CRUD operations for Task objects in Firebase Firestore using Kotlin coroutines for asynchronous tasks.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * App Description: This is a To do List application with which user can manage and organise schedule
 */
class DataManager private constructor()
{
    private val db: FirebaseFirestore = Firebase.firestore

    companion object {
        private val TAG = "DataManager"

        @Volatile
        private var m_instance: DataManager? = null

        /**
         * This ensures that only one instance of DataManager is created using double-checked locking.
         *
         * @return The singleton instance of DataManager.
         */
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

    /**
     * Inserts a new task into the Firestore database.
     *
     * @param task The Task object to be inserted into the database.
     */
    suspend fun insert(task: Task) {
        try {
            db.collection("tasks").document(task.id).set(task).await()
        }
        catch (e: Exception) {
            Log.e(TAG, "Error inserting Task: ${e.message}", e)
        }
    }

    /**
     * Updates an existing task in the database.
     *
     * @param task The Task object with updated values to be saved in the database.
     */
    suspend fun update(task: Task) {
        try {
            db.collection("tasks").document(task.id).set(task).await()
        }
        catch (e: Exception) {
            Log.e(TAG, "Error updating Task: ${e.message}", e)
        }
    }

    /**
     * Deletes a task.
     *
     * @param task The Task object to be deleted from the database.
     */
    suspend fun delete(task: Task) {
        try {
            db.collection("tasks").document(task.id).delete().await()
        }
        catch (e: Exception) {
            Log.e(TAG, "Error deleting Task: ${e.message}", e)
        }
    }

    /**
     * Gets all tasks.
     *
     * @return A list of Task objects retrieved from the database.
     */
    suspend fun getAllTasks(): List<Task> {
        return try {
            val result = db.collection("tasks").get().await()
            result?.toObjects(Task::class.java) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting all Tasks: ${e.message}", e)
            emptyList()
        }
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the Task to retrieve.
     * @return The Task object with the specified ID, or null if not found.
     */
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