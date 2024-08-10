package ca.georgiancollege.todo

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.Date

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: Task
 * File Description: This is a model class which defines a task with properties for its ID, title, due date, whether it is overdue, and whether it is finished
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */

@IgnoreExtraProperties
data class Task(
    @DocumentId val id: String = "",
    val name: String,
    val notes: String,
    val dueDate: Date? = Date(),
    val hasDueDate: Boolean,
    var isCompleted: Boolean
)
{
    constructor() : this("", "", "", Date(), false, false)
}
