package com.example.cinemagic.Fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemagic.Adapters.TicketAdapter
import com.example.cinemagic.Movie.TicketMovie
import com.example.cinemagic.R
import com.google.firebase.database.*
import com.example.cinemagic.DatabaseURL.DataBaseURL

class TicketsFragment : Fragment()
{
    private lateinit var ticketslist: ArrayList<TicketMovie>
    private val baseUrl = DataBaseURL.URL
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_tickets, container, false)

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ticketslist = arrayListOf()

        recyclerView = view.findViewById(R.id.rvTickets)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        recyclerView.setHasFixedSize(true)

        getTickets()
        return view
    }

    fun deleteFromTickets(ticket: TicketMovie)
    {
        databaseRef = FirebaseDatabase.getInstance(baseUrl).getReference("tickets")
        databaseRef.child(ticket.id).removeValue()
    }

    private fun getTickets()
    {
        databaseRef = FirebaseDatabase.getInstance(baseUrl).getReference("tickets")
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var shouldRefresh = false
                if(snapshot.exists())
                {
                    for(ProductSnapshot in snapshot.children)
                    {
                        val id = ProductSnapshot.child("id").value.toString()
                        val title = ProductSnapshot.child("title").value.toString()
                        val mainPhoto = ProductSnapshot.child("mainPhoto").value.toString()
                        val seat = ProductSnapshot.child("seat").value.toString()
                        val hour = ProductSnapshot.child("hour").value.toString()
                        val date = ProductSnapshot.child("date").value.toString()

                        val ticket = TicketMovie(id, title, mainPhoto, date, hour, seat)

                        if(!ticketslist.contains(ticket)){
                            ticketslist.add(ticket)
                            shouldRefresh = true
                        }
                        if(shouldRefresh)
                        {
                            recyclerView.adapter = TicketAdapter(ticketslist)
                        }
                    }

                    val adapter = TicketAdapter(ticketslist)
                    recyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object: TicketAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}