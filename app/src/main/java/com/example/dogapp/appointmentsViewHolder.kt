package com.example.dogapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class appointmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.tv_item_name)
    val description = view.findViewById<TextView>(R.id.tv_item_description)
    val order = view.findViewById<TextView>(R.id.tv_item_order)
    fun render(appointmentsModel: appointments) {
        name.text = appointmentsModel.name
        description.text = appointmentsModel.description
        order.text = appointmentsModel.order
    }
}