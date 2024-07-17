package ca.georgiancollege.todo_part1

data class Task(
    val id: Int = 0,
    val title: String,
    val dueDate: String,
    val isOverdue: Boolean
)
