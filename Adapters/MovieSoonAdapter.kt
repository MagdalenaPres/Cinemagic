package com.example.cinemagic.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemagic.Movie.Movie
import com.example.cinemagic.R
import com.squareup.picasso.Picasso


class MovieSoonAdapter(private val list: List<Movie>): RecyclerView.Adapter<MovieSoonAdapter.ProductViewHolder>()
{
    private lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_soon, parent, false)
        return ProductViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
        val currentItem = list[position]
        Picasso.get().load(currentItem.mainPhoto).fit().into(holder.imageView)
        holder.titleView.text = currentItem.title
        holder.premiereView.text = currentItem.premiere
    }

    override fun getItemCount() = list.size

    class ProductViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.movieSoonPoster)
        val titleView: TextView = itemView.findViewById(R.id.movieSoonTitle)
        val premiereView: TextView = itemView.findViewById(R.id.movieSoonPremiere)

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