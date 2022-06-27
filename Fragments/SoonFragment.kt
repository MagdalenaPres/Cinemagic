package com.example.cinemagic.Fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemagic.Adapters.MovieSoonAdapter
import com.example.cinemagic.Movie.Movie
import com.example.cinemagic.R
import com.google.firebase.database.*
import com.example.cinemagic.DatabaseURL.DataBaseURL

class SoonFragment : Fragment() {
    private lateinit var onScreen: TextView
    private lateinit var soon: TextView
    private val baseUrl = DataBaseURL.URL
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieslist: ArrayList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_soon, container, false)
        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        soon = view.findViewById(R.id.soonSoon)
        onScreen = view.findViewById(R.id.onScreenSoon)

        recyclerView = view.findViewById(R.id.rvMoviesSoon)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.setHasFixedSize(true)

        onScreen.setOnClickListener {
            val mainFragment = MainFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fragment_container, mainFragment).commit()
        }

        movieslist = arrayListOf()
        createMoviesList()
        return view
    }

    private fun createMoviesList()
    {
        databaseRef = FirebaseDatabase.getInstance(baseUrl).getReference("soon")
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for(ProductSnapshot in snapshot.children)
                    {
                        val id = ProductSnapshot.child("id").value.toString()
                        val title = ProductSnapshot.child("title").value.toString()
                        val type = ProductSnapshot.child("type").value.toString()
                        val premiere = ProductSnapshot.child("premiere").value.toString()
                        val mainPhoto = ProductSnapshot.child("photo").value.toString()

                        val item = Movie(id, title, type, "", "", premiere, "", mainPhoto, mapOf(), arrayOf()
                        )
                        movieslist.add(item)
                    }
                    val adapter = MovieSoonAdapter(movieslist)
                    recyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object: MovieSoonAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val clickedItem: Movie = movieslist[position]
                            Toast.makeText(activity, "item: "+ clickedItem.title, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}