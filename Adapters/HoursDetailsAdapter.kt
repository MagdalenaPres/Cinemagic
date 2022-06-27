package com.example.cinemagic.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemagic.R
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class HoursDetailsAdapter (private val list: ArrayList<String>): RecyclerView.Adapter<HoursDetailsAdapter.ProductViewHolder>()
{
    private lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hours_details, parent, false)
        return ProductViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
        val currentItem = list[position]

        holder.textView.text = currentItem
    }

    override fun getItemCount() = list.size

    class ProductViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        val textView: TextView = itemView.findViewById(R.id.hourDetailsTxt)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
}