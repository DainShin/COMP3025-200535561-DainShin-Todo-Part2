package ca.georgiancollege.todo_part1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ca.georgiancollege.todo_part1.databinding.ActivityDetailsBinding

class DetailsActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}