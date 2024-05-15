package com.example.task_scheduler

import android.R
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.task_scheduler.databinding.ActivityCreateCardBinding // Import the generated binding class
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCard : AppCompatActivity() {
    private lateinit var  db: NotesDatabaseHelper
    private lateinit var binding: ActivityCreateCardBinding // Declare the binding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCardBinding.inflate(layoutInflater) // Initialize the binding variable
        setContentView(binding.root) // Set the content view to the root of the binding class

        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener { // Use binding to reference the save button
            val title = binding.createTitle.text.toString().trim() // Use binding to reference the title EditText
            val priority = binding.createPriority.text.toString().trim() // Use binding to reference the priority EditText

            if (title.isNotEmpty() && priority.isNotEmpty()) {
                GlobalScope.launch {
                    val note = Note(0,title,priority)
                    db.insertNote(note)
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Optionally, show a toast message if the fields are empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
        hideSystemUI()
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView.findViewById(R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
