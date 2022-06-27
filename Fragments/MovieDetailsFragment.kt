package com.example.beautyme.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import android.text.method.ScrollingMovementMethod
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemagic.Adapters.ImageDetailsAdapter
import com.example.cinemagic.Adapters.MovieMainAdapter
import com.example.cinemagic.Fragments.MainFragment
import com.example.cinemagic.Movie.Movie
import com.example.cinemagic.R
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.google.firebase.database.*
import android.widget.RelativeLayout

import com.google.android.youtube.player.internal.v

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Color

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinemagic.Adapters.HoursDetailsAdapter
import com.example.cinemagic.Fragments.SeatsSelectionFragment
import com.example.cinemagic.MainActivity
import com.example.cinemagic.Movie.TicketMovie
import com.google.android.youtube.player.YouTubeIntents.createPlayVideoIntent
import java.util.*


class MovieDetailsFragment : Fragment()
{
    private lateinit var id: String
    private lateinit var title: TextView
    private lateinit var photo: ImageView
    private lateinit var duration: TextView
    private lateinit var description: TextView
    private lateinit var premiere: TextView
    private lateinit var video: TextView
    private lateinit var receivedMovie: Movie
    private lateinit var bundle: Bundle
    private lateinit var backSign: ImageView
    private lateinit var play_button: ImageView
    private lateinit var images: Array<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonCalendar: ImageView
    private lateinit var displayDate: EditText
    private lateinit var dates: Map<String, String>
    private lateinit var recyclerViewHours: RecyclerView
    private lateinit var hoursList: ArrayList<String>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        hoursList = arrayListOf()
        title = view.findViewById(R.id.details_title)
        photo = view.findViewById(R.id.details_photo)
        description = view.findViewById(R.id.details_description)
        duration = view.findViewById(R.id.details_duration)
        premiere = view.findViewById(R.id.details_premiere)
        backSign = view.findViewById(R.id.back_sign)
        play_button = view.findViewById(R.id.play_sign)
        video = TextView(context)
        buttonCalendar = view.findViewById(R.id.pickDateDetails)
        displayDate = view.findViewById(R.id.displayDateDetails)

        recyclerView = view.findViewById(R.id.rvImages)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        recyclerViewHours = view.findViewById(R.id.rvHoursDetails)
        recyclerViewHours.layoutManager = GridLayoutManager(activity, 3)
        recyclerViewHours.setHasFixedSize(true)

        bundle = this.requireArguments()
        receivedMovie = bundle.getParcelable("movieInfo")!!

        id = receivedMovie.id
        title.text = receivedMovie.title
        val mainPhoto = receivedMovie.mainPhoto
        Picasso.get().load(mainPhoto).fit().into(photo)
        description.text = receivedMovie.description
        description.movementMethod = ScrollingMovementMethod()
        duration.text = receivedMovie.duration
        premiere.text = receivedMovie.premiere
        video.text = receivedMovie.video
        images = receivedMovie.images
        dates = receivedMovie.hours

        val adapter = ImageDetailsAdapter(images)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object: ImageDetailsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int)
            {
                val cancel: ImageView
                val image: ImageView
                val alertCustomdialog = LayoutInflater.from(context).inflate(R.layout.customdialogwithimage, null)
                val clickedItem: String = images[position]
                val alert: AlertDialog.Builder = AlertDialog.Builder(context)

                alert.setView(alertCustomdialog)
                cancel = alertCustomdialog.findViewById<View>(R.id.closeZoom) as ImageView
                image = alertCustomdialog.findViewById(R.id.imageZoom) as ImageView

                Picasso.get().load(clickedItem).fit().into(image)

                val dialog: AlertDialog = alert.create()

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.show()
                cancel.setOnClickListener { dialog.dismiss() }
            }
        })

        description.movementMethod = ScrollingMovementMethod()

        backSign.setOnClickListener {
            val mainFragment = MainFragment()

            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, mainFragment)?.commit()
        }

        play_button.setOnClickListener {
            val intent = YouTubeStandalonePlayer.createVideoIntent(activity, getString(R.string.developer_key),
                video.text as String, 0, true, false)
            startActivity(intent)
        }

        val cal= Calendar.getInstance()
        displayDate.setText("09" + "/" + "05" + "/" + cal.get(Calendar.YEAR))
        createHoursList()

        buttonCalendar.setOnClickListener{
            val calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, year, monthOfYear, dayOfMonth ->
                        var d = dayOfMonth.toString()
                        var m = (monthOfYear+1).toString()

                        if(dayOfMonth < 10)
                        {
                            d = "0".plus(d)
                        }
                        if(monthOfYear < 10){
                            m = "0".plus(m)
                        }
                        displayDate.setText("$d/$m/$year")
                        createHoursList()
                    }, year, month, day
                )
            }
            datePickerDialog?.show()
        }
        return view
    }
    private fun createHoursList()
    {
        hoursList.clear()

        for (item in dates)
        {
            val date = (displayDate.text).take(5)
            val date2 = date.toString().replace("/", "")

            if(item.key == date2)
            {
                val hoursString = item.value.replace("[", "")
                val hoursString2 = hoursString.replace("]", "")

                val separator = ","
                val list = hoursString2.split(separator)

                for(i in list)
                {
                    if(i != "null"){
                        hoursList.add(i)
                    }
                }
                break;
            }
        }

        val hoursAdapter = HoursDetailsAdapter(hoursList)
        recyclerViewHours.adapter = hoursAdapter

        hoursAdapter.setOnItemClickListener(object: HoursDetailsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val chosenHour: String = hoursList[position]
                val movie = TicketMovie(id, receivedMovie.title, receivedMovie.mainPhoto,
                    displayDate.text.toString(), chosenHour, "")

                val seatSelectionFragment = SeatsSelectionFragment()
                val bundle = Bundle()

                bundle.putParcelable("movieTicket", movie)
                seatSelectionFragment.arguments = bundle

                fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, seatSelectionFragment)?.commit()
            }
        })
    }

}