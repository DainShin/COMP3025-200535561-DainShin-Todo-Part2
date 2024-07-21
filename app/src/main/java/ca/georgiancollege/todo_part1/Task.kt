package ca.georgiancollege.todo_part1

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: Task
 * File Description: This is a model class which defines a task with properties for its ID, title, due date, whether it is overdue, and whether it is finished
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: July 21st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
data class Task(
    val id: Int = 0,
    val title: String,
    val dueDate: String,
    val isOverdue: Boolean,
    var isFinished: Boolean
)
