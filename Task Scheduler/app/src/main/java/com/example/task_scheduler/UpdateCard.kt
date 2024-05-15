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
import com.example.task_scheduler.databinding.ActivityUpdateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {
    private lateinit var db: NotesDatabaseHelper
    private lateinit var binding: ActivityUpdateCardBinding
    private var pos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        pos = intent.getIntExtra("id", -1)
        if (pos == -1) {
            finish()
            return
        }
        val note = db.getNoteByID(pos) // Retrieve the note by its ID

        binding.createTitle.setText(note.title)
        binding.createPriority.setText(note.priority)

        binding.deleteButton.setOnClickListener {
            GlobalScope.launch {
                db.deleteNote(pos)
            }
            myIntent()
        }

        binding.updateButton.setOnClickListener {
            val title = binding.createTitle.text.toString()
            val priority = binding.createPriority.text.toString()

            GlobalScope.launch {
                val updatedNote = Note(pos, title, priority)
                db.updateNote(updatedNote)
            }

            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            myIntent()
        }

        hideSystemUI()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }


    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
