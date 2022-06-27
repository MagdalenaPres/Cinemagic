package com.example.cinemagic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher


@RunWith(AndroidJUnit4::class)
class MovieDetailsFragmentTest : TestCase()
{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testImageDialog()
    {
        Thread.sleep(1300)
        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.moviePoster)))

        Thread.sleep(1300)
        onView(withId(R.id.movieDetailsFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(1300)

        onView(withId(R.id.movieDetailsFragment)).perform(ViewActions.swipeUp());

        Thread.sleep(1300)
        onView(withId(R.id.rvImages))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, ClickOnButtonView(R.id.image_in_slider)))

        Thread.sleep(1300)
        onView(withId(R.id.customImageDialog)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
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