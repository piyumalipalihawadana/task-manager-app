package com.example.task_scheduler

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task_scheduler.databinding.ViewBinding // Import the generated binding class

class Adapter(var data: List<Note>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = data[position]
        with(holder.binding) {
            when (data[position].priority.lowercase()) {
                "high" -> mylayout.setBackgroundColor(Color.parseColor("#F05454"))
                "medium" -> mylayout.setBackgroundColor(Color.parseColor("#EDC988"))
                else -> mylayout.setBackgroundColor(Color.parseColor("#000000"))
            }

            title.text = data[position].title
            priority.text = data[position].priority
            root.setOnClickListener {
                val intent = Intent(root.context, UpdateCard::class.java) .apply{
               putExtra("id", note.id)
                }
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
