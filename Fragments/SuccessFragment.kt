package com.example.cinemagic.Fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cinemagic.R

class SuccessFragment : Fragment()
{
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_success, container, false)

        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        button = view.findViewById(R.id.main_page)

        button.setOnClickListener {
            val mainFragment = MainFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, mainFragment)?.commit()
        }

        return view
    }

}