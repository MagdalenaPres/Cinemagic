package com.example.cinemagic

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import java.util.*
import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.contrib.*;
import org.hamcrest.Matchers
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class MainFragmentTest: TestCase(){

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSoonButton ()
    {
        onView(withId(R.id.soonMain)).perform(click())
        onView(withId(R.id.soonFragment)).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    @Test
    fun testCalendarButton ()
    {
        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        onView(withId(R.id.pickDateMain)).perform(click())

        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
                            .perform(PickerActions.setDate(year, month, day))
    }

    @Test
    fun testMovieButton ()
    {
        Thread.sleep(1300)
        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.moviePoster)))

        onView(withId(R.id.movieDetailsFragment)).check(matches(isDisplayed()))
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