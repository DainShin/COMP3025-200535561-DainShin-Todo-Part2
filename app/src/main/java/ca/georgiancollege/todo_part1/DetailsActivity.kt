package ca.georgiancollege.todo_part1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ca.georgiancollege.todo_part1.databinding.ActivityDetailsBinding

/**
 * Program Name: COMP3025 – Mobile and Pervasive Computing
 * File Name: DetailsActivity
 * File Description: This file is for the Details View where user can edit or delete the item
 * Student Name: Dain Shin
 * Student Number: 200535561
 * Last Modified: July 21st, 2024
 * Version: 1.0
 * App Description: This is a To do List application with which user can manage and organise schedule
 */
class DetailsActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // hide calendarView by default
        binding.calendarView.visibility = View.GONE

        // click event
        binding.dueDateSection.setOnClickListener {
            Log.i("달력", "달력 클릭")
            // Toggle visibility of CalendarView
            if (binding.calendarView.visibility == View.GONE) {
                binding.calendarView.visibility = View.VISIBLE
                Log.i("달력", "달력 보이기")
            } else {
                binding.calendarView.visibility = View.GONE
                Log.i("달력", "달력 숨기기")
            }
        }
    }
}