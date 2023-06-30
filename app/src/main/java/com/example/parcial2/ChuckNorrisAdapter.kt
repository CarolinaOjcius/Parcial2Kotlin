package com.example.parcial2


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChuckNorrisAdapter(private val categories: ArrayList<String>) :
    RecyclerView.Adapter<ChuckNorrisAdapter.ViewHolder>() {

    lateinit var onItemClickListener: (String) -> Unit

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val category: TextView = view.findViewById(R.id.textViewCategory)

        fun bind(category: String) {
            this.category.text = category.uppercase()

            view.setOnClickListener {
                onItemClickListener(category)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChuckNorrisAdapter.ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChuckNorrisAdapter.ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun submitList(updatedCategories: ArrayList<String>) {
        categories.clear()
        categories.addAll(updatedCategories)
        notifyDataSetChanged()
    }
}











