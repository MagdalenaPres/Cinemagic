package com.example.cinemagic.Fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.cinemagic.Movie.TicketMovie
import com.example.cinemagic.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.cinemagic.DatabaseURL.DataBaseURL

class ConfirmationFragment : Fragment()
{
    private lateinit var title: TextView
    private lateinit var date: TextView
    private lateinit var hour: TextView
    private lateinit var receivedMovie: TicketMovie
    private lateinit var bundle: Bundle
    private lateinit var seat: TextView
    private lateinit var id: String
    private lateinit var yes_button: Button
    private lateinit var no_button: Button
    private lateinit var database: DatabaseReference
    private val base_url = DataBaseURL.URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_confirmation, container, false)

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        title = view.findViewById(R.id.confirm_title)
        date = view.findViewById(R.id.confirm_date)
        hour = view.findViewById(R.id.confirm_hour)
        seat = TextView(context)
        yes_button = view.findViewById(R.id.yes_confirm)
        no_button = view.findViewById(R.id.no_confirm)

        bundle = this.requireArguments()
        receivedMovie = bundle.getParcelable("movieTicketConfirm")!!

        title.text = receivedMovie.title
        hour.text = receivedMovie.hour
        date.text = receivedMovie.date
        seat.text = receivedMovie.seat
        id = receivedMovie.id

        yes_button.setOnClickListener {
            addTicketToDatabase(receivedMovie)

            val successFragment = SuccessFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, successFragment)?.commit()
        }

        no_button.setOnClickListener {
            val mainFragment = MainFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, mainFragment)?.commit()
        }

        return view
    }

    fun addTicketToDatabase(ticket: TicketMovie){
        val time = System.currentTimeMillis()
        ticket.id = time.toString()
        database = FirebaseDatabase.getInstance(base_url).getReference("tickets")
        database.child(time.toString()).setValue(ticket)
    }

}