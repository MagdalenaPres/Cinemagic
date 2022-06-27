package com.example.cinemagic.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemagic.Fragments.TicketsFragment
import com.example.cinemagic.Movie.Movie
import com.example.cinemagic.Movie.TicketMovie
import com.example.cinemagic.R
import com.squareup.picasso.Picasso

class TicketAdapter (private val list: MutableList<TicketMovie>): RecyclerView.Adapter<TicketAdapter.ProductViewHolder>()
{
    private lateinit var mListener: OnItemClickListener
    private lateinit var ticketsF: TicketsFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ticket, parent, false)
        return ProductViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
        val currentItem = list[position]
        ticketsF = TicketsFragment()

        Picasso.get().load(currentItem.mainPhoto).fit().into(holder.imageView)
        holder.titleView.text = currentItem.title
        holder.dateView.text = currentItem.date
        holder.hourView.text = currentItem.hour
        holder.seatView.text = currentItem.seat

        holder.cancel.setOnClickListener {
            list.removeAt(holder.bindingAdapterPosition)
            notifyItemRemoved(holder.bindingAdapterPosition)
            ticketsF.deleteFromTickets(currentItem)
        }
    }

    override fun getItemCount() = list.size

    class ProductViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.ticketPoster)
        val titleView: TextView = itemView.findViewById(R.id.ticketTitle)
        val dateView: TextView = itemView.findViewById(R.id.ticketDate)
        val hourView: TextView = itemView.findViewById(R.id.ticketHour)
        val seatView: TextView = itemView.findViewById(R.id.ticketSeat)
        val cancel: TextView = itemView.findViewById(R.id.cancel)

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