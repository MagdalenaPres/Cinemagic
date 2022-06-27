package com.example.cinemagic.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemagic.Movie.Movie
import com.example.cinemagic.R
import com.squareup.picasso.Picasso


class MovieMainAdapter(private val list: List<Movie>): RecyclerView.Adapter<MovieMainAdapter.ProductViewHolder>()
{
    private lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return ProductViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
        val currentItem = list[position]
        Picasso.get().load(currentItem.mainPhoto).fit().into(holder.imageView)
        holder.titleView.text = currentItem.title
        holder.typeView.text = currentItem.type
        holder.durationView.text = currentItem.duration
    }

    override fun getItemCount() = list.size

    class ProductViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.moviePoster)
        val titleView: TextView = itemView.findViewById(R.id.movieTitle)
        val typeView: TextView = itemView.findViewById(R.id.movieType)
        val durationView: TextView = itemView.findViewById(R.id.movieDuration)
        var hours: ListView = itemView.findViewById(R.id.hoursMain)

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