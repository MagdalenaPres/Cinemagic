package com.example.cinemagic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.cinemagic.Fragments.AccountFragment
import com.example.cinemagic.Fragments.MainFragment
import com.example.cinemagic.Fragments.TicketsFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mainFragment = MainFragment()
    private val accountFragment = AccountFragment()
    private val ticketsFragment = TicketsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        replaceFragment(mainFragment)

        this.bottom_nav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.movie -> replaceFragment(mainFragment)
                R.id.your_tickets -> replaceFragment(ticketsFragment)
                R.id.account -> replaceFragment(accountFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}