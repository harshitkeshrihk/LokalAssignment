package com.example.lokalassignmentapp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignmentapp.R
import com.example.lokalassignmentapp.model.Result

class JobAdapter() : RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    var onPhoneNoClick: ((String) -> Unit)? = null

    var onItemClick: ((Result) -> Unit)? = null

    var onBookmarkClick: ((Result) -> Unit)? = null

    private var items: List<Result> = arrayListOf()

    fun setList(data: List<Result>){
        items=data
        notifyDataSetChanged()
    }



    // Define the ViewHolder that will hold each item
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTv)
        val salary: TextView = itemView.findViewById(R.id.salary)
        val location: TextView = itemView.findViewById(R.id.location)
        val phoneNo: TextView = itemView.findViewById(R.id.phoneNo)
        val bookMarkCv: ImageView = itemView.findViewById(R.id.bookMarkCV)
        val cardView: CardView = itemView.findViewById(R.id.cardView)

    }

    // Inflate the item layout and create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.job_item_layout, parent, false)
        return ViewHolder(view)
    }

    // Bind the data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title ?: "NotAvailable"
        holder.salary.text = "Salary - " + (items[position].salary_max ?: "NotAvailable")
        holder.location.text = "Location -" + (items[position].company_name + " , " + items[position].job_location_slug  ?: "NotAvailable")
        holder.phoneNo.text = items[position].whatsapp_no ?: "NotAvailable"
        holder.bookMarkCv.setOnClickListener{
            onBookmarkClick?.let { it1-> it1(items[position]) }
        }
        holder.cardView.setOnClickListener{
            onItemClick?.let { it1 -> it1(items[position]) }
        }
        val phoneNo = items[position].whatsapp_no ?: ""
        holder.phoneNo.setOnClickListener{
            onPhoneNoClick?.let { it1 -> it1(phoneNo) }
        }
    }

    // Return the size of your dataset
    override fun getItemCount(): Int {
        return items.size
    }
}