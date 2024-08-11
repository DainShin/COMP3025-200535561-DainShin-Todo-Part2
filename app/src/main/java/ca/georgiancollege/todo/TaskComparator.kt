package ca.georgiancollege.todo

import androidx.recyclerview.widget.DiffUtil


/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: TaskComparator
 * File Description: This class compares the identity and content differences of Task items for use in RecyclerView's ListAdapter.
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: August 11st, 2024
 * Version: 1.0
 * Description: This is a To do List application with which user can manage and organise schedule
 */
class TaskComparator : DiffUtil.ItemCallback<Task>()
{
    /**
     * Checks if two items represent the same task based on their unique IDs.
     *
     * @param oldItem The old Task item.
     * @param newItem The new Task item.
     * @return True if the IDs of both items are the same
     */
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Checks if the contents of two tasks are the same.
     *
     * @param oldItem The old Task item.
     * @param newItem The new Task item.
     * @return True if all properties of the old item are the same as the new item
     */
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}