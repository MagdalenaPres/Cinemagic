package com.example.cinemagic.Fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyme.fragments.MovieDetailsFragment
import com.example.cinemagic.Adapters.MovieMainAdapter
import com.example.cinemagic.Movie.Movie
import com.example.cinemagic.R
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.cinemagic.DatabaseURL.DataBaseURL


class MainFragment : Fragment() {
    private lateinit var buttonCalendar: ImageView
    lateinit var displayDate: EditText
    private lateinit var onScreen: TextView
    private lateinit var soon: TextView
    private val baseUrl = DataBaseURL.URL
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieslist: ArrayList<Movie>
    private lateinit var recyclerViewHours: RecyclerView
    private lateinit var listView: ListView
    private lateinit var chosenDate: String

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val view1 = inflater.inflate(R.layout.movie, container, false)

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonCalendar = view.findViewById(R.id.pickDateMain)
        displayDate = view.findViewById(R.id.displayDateMain)
        soon = view.findViewById(R.id.soonMain)
        onScreen = view.findViewById(R.id.onScreenMain)

        recyclerView = view.findViewById(R.id.rvMovies)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        recyclerView.setHasFixedSize(true)

        recyclerViewHours = view1.findViewById(R.id.rvHoursMain)
        recyclerViewHours.layoutManager = GridLayoutManager(activity, 3)
        recyclerViewHours.setHasFixedSize(true)

        chosenDate = ""

        listView = view1.findViewById(R.id.hoursMain)

        val cal= Calendar.getInstance()
        var d = cal.get(Calendar.DAY_OF_MONTH)
        var m = (cal.get(Calendar.MONTH) + 1)
        var d1 = d.toString()
        var m1 = m.toString()

        if(d < 10)
        {
            d1 = "0".plus(d)
        }
        if(m < 10){
            m1 = "0".plus(m)
        }

        displayDate.setText(d1 + "/" + m1 + "/" + cal.get(Calendar.YEAR))

        soon.setOnClickListener {
            val soonFragment = SoonFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_container, soonFragment).commit()
        }

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
                        createMoviesList()
                    }, year, month, day
                )
            }
            datePickerDialog?.show()
        }

        movieslist = arrayListOf()
        createMoviesList()

        return view
    }

    private fun createMoviesList()
    {
        movieslist.clear()
        databaseRef = FirebaseDatabase.getInstance(baseUrl).getReference("onScreen")
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for(ProductSnapshot in snapshot.children)
                    {
                        val id = ProductSnapshot.child("id").value.toString()
                        val title = ProductSnapshot.child("title").value.toString()
                        val type = ProductSnapshot.child("type").value.toString()
                        val duration = ProductSnapshot.child("duration").value.toString()
                        val description = ProductSnapshot.child("description").value.toString()
                        val premiere = ProductSnapshot.child("premiere").value.toString()
                        val video = ProductSnapshot.child("video").value.toString()
                        val mainPhoto = ProductSnapshot.child("photos").child("1").value.toString()
                        val hours = mutableMapOf<String, String>()
                        val images: Array<String> = arrayOf(ProductSnapshot.child("photos").child("2").value.toString(),
                            ProductSnapshot.child("photos").child("3").value.toString(),
                            ProductSnapshot.child("photos").child("4").value.toString())

                        for (item in ProductSnapshot.child("dates").children)
                        {
                            hours[item.key.toString()] = item.value.toString()
                        }
                        
                        val movie = Movie(id, title, type, duration, description, premiere, video, mainPhoto, hours, images)

                        for (item in ProductSnapshot.child("dates").children)
                        {
                            val date = (displayDate.text).take(5)
                            val date2 = date.toString().replace("/", "")

                            if(item.key.toString() == date2)
                            {
                                movieslist.add(movie)
                                break;
                            }
                        }
                    }

                    val adapter = MovieMainAdapter(movieslist)
                    recyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object: MovieMainAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val clickedItem: Movie = movieslist[position]

                            val movieDetailsFragment = MovieDetailsFragment()
                            val bundle = Bundle()
                            bundle.putParcelable("movieInfo", clickedItem)
                            movieDetailsFragment.arguments = bundle

                            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, movieDetailsFragment)?.commit()
                            recyclerView.adapter?.notifyItemChanged(position)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}