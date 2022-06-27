package com.example.cinemagic.Fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.cinemagic.Movie.Movie
import com.example.cinemagic.Movie.TicketMovie
import com.example.cinemagic.R
import kotlinx.android.parcel.RawValue
import java.util.ArrayList

class SeatsSelectionFragment : Fragment()
{
    private lateinit var receivedMovie: TicketMovie
    private lateinit var bundle: Bundle
    private lateinit var title: TextView
    private lateinit var date: TextView
    private lateinit var hour: TextView
    private lateinit var option: Spinner
    private lateinit var next_button: Button
    private lateinit var seat: String
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seats_selection, container, false)

        val options = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                                "20", "21", "22", "23","24", "25","26", "27","28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
                                "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55",
                            "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73",
                            "74", "75", "76", "77", "78", "79", "80", "81", "82", "83","84", "85","86", "87")

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bundle = this.requireArguments()
        receivedMovie = bundle.getParcelable("movieTicket")!!

        title = view.findViewById(R.id.buy_title)
        hour = view.findViewById(R.id.buy_hour)
        date = view.findViewById(R.id.buy_date)
        option = view.findViewById(R.id.seat_selection)
        next_button = view.findViewById(R.id.next_seats)
        seat = ""

        title.text = receivedMovie.title
        hour.text = receivedMovie.hour
        date.text = receivedMovie.date
        id = receivedMovie.id

        option.adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, options) }
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                seat = options[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        next_button.setOnClickListener {
            val paymentFragment = PaymentFragment()
            val d = date.text

            val movie = TicketMovie(id, receivedMovie.title, receivedMovie.mainPhoto,
                d as String, hour.text.toString(), seat)

            Log.d("hour", hour.text.toString())

            val bundle = Bundle()

            bundle.putParcelable("movieTicketPayment", movie)
            paymentFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, paymentFragment)?.commit()
        }

        return view
    }

}