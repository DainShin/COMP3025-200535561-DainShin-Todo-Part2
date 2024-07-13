package ca.georgiancollege.todo_part1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


/*
1. Your Todo List Screen should include the following components (20 Marks: GUI):

    a. A List of any current Todos (tasks) is displayed in a RecyclerView (5 Marks: GUI).  --> ok

    b. Each Todo Cell should include a Switch (or other UI Control) that will indicate whether it
       has been completed or not.
       When a Todo is completed, it will be either greyed out or crossed out.
       Optional: use a gesture such as swiping from the left instead (5 Marks: GUI).

    c. Each Todo Cell should include an Edit Button (or other UI Control) that will link the user
       to the Todo Details Activity. Optional: allow the user to edit the Todo by pressing on an
       existing list item in the RecyclerView. (5 Marks: GUI). --> ok

    d. If an optional Due Date has been selected on the Todo Details Activity (see below) then
       the Due Date should appear as smaller text below the Task Name in the same Todo Cell.
       The Due Date area may be used to indicate a late Todo (5 Marks: GUI)  -->

-------------------------------------------------------------------------------------------------

2. Your Todo Details Activity should include the following components (32 Marks: GUI):

    a. The Todo Name TextView (a short description of the task) and an associated Label
       should appear on this screen. (5 Marks: GUI).

    b. Include a Notes TextView (a longer description of the task) and an associated Label (6
       Marks: GUI)

    c. Include a CalendarView (or other UI Control), an associated Label and associated Switch
       control that allows the user to select an optional Due Date for the Todo (6 Marks: GUI)

    d. Include a Label and an associated Switch control that allows the user to mark the Todo
       as completed. (5 Marks: GUI)

    e. Include an Update Button, a Delete Button and a Cancel Button below the Name and
       Notes Text Views. (10 Marks: GUI).
* */
