package com.example.cinemagic.Fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.cinemagic.Movie.TicketMovie
import com.example.cinemagic.R

class PaymentFragment : Fragment()
{
    private lateinit var title: TextView
    private lateinit var date: TextView
    private lateinit var hour: TextView
    private lateinit var receivedMovie: TicketMovie
    private lateinit var bundle: Bundle
    private lateinit var seat: TextView
    private lateinit var next_button: Button
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        title = view.findViewById(R.id.order_title)
        date = view.findViewById(R.id.order_date)
        hour = view.findViewById(R.id.order_hour)
        seat = view.findViewById(R.id.payment_seat)
        next_button = view.findViewById(R.id.next_payment)

        bundle = this.requireArguments()
        receivedMovie = bundle.getParcelable("movieTicketPayment")!!

        title.text = receivedMovie.title
        hour.text = receivedMovie.hour
        date.text = receivedMovie.date
        seat.text = receivedMovie.seat
        id = receivedMovie.id

        next_button.setOnClickListener {
            val confirmationFragment = ConfirmationFragment()
            val bundle = Bundle()

            val movie = TicketMovie(id, receivedMovie.title, receivedMovie.mainPhoto,
                receivedMovie.date, receivedMovie.hour, receivedMovie.seat)

            bundle.putParcelable("movieTicketConfirm", movie)
            confirmationFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, confirmationFragment)?.commit()
        }

        return view
    }

}