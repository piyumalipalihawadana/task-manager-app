package com.example.task_scheduler

import android.R
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task_scheduler.databinding.ActivityMainBinding // Import the generated binding class
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var  db: NotesDatabaseHelper
    private lateinit var binding: ActivityMainBinding // Declare the binding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Initialize the binding variable
        setContentView(binding.root) // Set the content view to the root of the binding class

        db = NotesDatabaseHelper(this)

        binding.add.setOnClickListener { // Use binding to reference the add button
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        binding.deleteAll.setOnClickListener { // Use binding to reference the deleteAll button
            GlobalScope.launch {

            }
            setRecycler()
        }

        setRecycler()
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

    private fun setRecycler() {
        binding.recyclerView.adapter = Adapter(db.getAllNotes()) // Use binding to reference the RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }
}
