package com.example.lokalassignmentapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignmentapp.R
import com.example.lokalassignmentapp.model.JobTag
import com.example.lokalassignmentapp.model.Result

class JobTagAdapter() :
    RecyclerView.Adapter<JobTagAdapter.JobTagViewHolder>() {

    private var items: List<JobTag> = arrayListOf()

    fun setList(data: List<JobTag>){
        items=data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobTagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.job_tag_item, parent, false)
        return JobTagViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobTagViewHolder, position: Int) {
        val jobTag = items[position]
        holder.bind(jobTag)
    }

    override fun getItemCount(): Int = items.size

    class JobTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val jobTagTextView: TextView = itemView.findViewById(R.id.job_tag_text)

        fun bind(jobTag: JobTag) {
            jobTagTextView.text = jobTag.value

            jobTag.bg_color?.let {
                try {
                    jobTagTextView.setBackgroundColor(Color.parseColor(it))
                } catch (e: IllegalArgumentException) {
                    // Handle the error gracefully
                }
            }

            jobTag.text_color?.let {
                try {
                    jobTagTextView.setTextColor(Color.parseColor(it))
                } catch (e: IllegalArgumentException) {
                    // Handle the error gracefully
                }
            }
        }
    }
}
