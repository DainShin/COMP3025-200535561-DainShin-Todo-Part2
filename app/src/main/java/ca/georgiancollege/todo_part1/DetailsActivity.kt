package ca.georgiancollege.todo_part1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ca.georgiancollege.todo_part1.databinding.ActivityDetailsBinding

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