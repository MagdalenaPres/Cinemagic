package com.example.cinemagic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.database.FirebaseDatabase
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicketManagementTest : TestCase()
{
    private val baseUrl = "https://cinemagic-13105-default-rtdb.europe-west1.firebasedatabase.app/"

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testTicketReservation()
    {
        deleteAllTicketsFromDatabase()

        Thread.sleep(1300)
        onView(withId(R.id.your_tickets)).perform(ViewActions.click())
        onView(withId(R.id.ticketsFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(1300)
        onView(withId(R.id.rvTickets)).check(matches(hasChildCount(0)))

        Thread.sleep(500)
        onView(withId(R.id.movie)).perform(ViewActions.click())
        onView(withId(R.id.mainFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.moviePoster)))

        onView(withId(R.id.movieDetailsFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.rvHoursDetails))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.hourDetailsTxt)))

        onView(withId(R.id.seatSelectionFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.next_seats)).perform(ViewActions.click())
        onView(withId(R.id.paymentFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.next_payment)).perform(ViewActions.click())
        onView(withId(R.id.confirmationFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.yes_confirm)).perform(ViewActions.click())
        onView(withId(R.id.successFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.main_page)).perform(ViewActions.click())
        onView(withId(R.id.mainFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.your_tickets)).perform(ViewActions.click())
        onView(withId(R.id.ticketsFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.rvTickets)).check(matches(hasChildCount(1)))
    }

    @Test
    fun testTicketCancellation()
    {
        deleteAllTicketsFromDatabase()

        Thread.sleep(1300)
        onView(withId(R.id.your_tickets)).perform(ViewActions.click())
        onView(withId(R.id.ticketsFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(1300)
        onView(withId(R.id.rvTickets)).check(matches(hasChildCount(0)))

        Thread.sleep(500)
        onView(withId(R.id.movie)).perform(ViewActions.click())
        onView(withId(R.id.mainFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.moviePoster)))

        onView(withId(R.id.movieDetailsFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.rvHoursDetails))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.hourDetailsTxt)))

        onView(withId(R.id.seatSelectionFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.next_seats)).perform(ViewActions.click())
        onView(withId(R.id.paymentFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.next_payment)).perform(ViewActions.click())
        onView(withId(R.id.confirmationFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.yes_confirm)).perform(ViewActions.click())
        onView(withId(R.id.successFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.main_page)).perform(ViewActions.click())
        onView(withId(R.id.mainFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.your_tickets)).perform(ViewActions.click())
        onView(withId(R.id.ticketsFragment)).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.rvTickets)).check(matches(hasChildCount(1)))

        Thread.sleep(500)
        onView(withId(R.id.rvTickets))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.cancel)))

        onView(withId(R.id.rvTickets)).check(matches(hasChildCount(0)))
    }

    private fun deleteAllTicketsFromDatabase()
    {
        val database = FirebaseDatabase.getInstance(baseUrl).getReference("tickets")
        database.removeValue()
    }

    inner class ClickOnButtonView(private val id: Int) : ViewAction {
        internal var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            click.perform(uiController, view.findViewById(id))
        }
    }
}