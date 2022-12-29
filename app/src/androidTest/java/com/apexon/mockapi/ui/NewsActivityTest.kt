package com.apexon.mockapi.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.apexon.mockapi.R
import com.apexon.mockapi.common.CountingIdlingResourceSingleton
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NewsActivityTest {

    @get:Rule
    val rule = activityScenarioRule<NewsActivity>()


    @Before
    fun init(){
        IdlingRegistry.getInstance().register(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        onView(ViewMatchers.withId(R.id.rvNews)).perform(
            RecyclerViewActions.scrollTo<NewsAdapter.VH>(
                ViewMatchers.hasDescendant(
                    ViewMatchers.withText("not in the listX")
            )
        ))
    }

    @Test
    fun testSelectedItem(){

        onView(withId(R.id.rvNews)).perform(
            RecyclerViewActions.actionOnItemAtPosition<NewsAdapter.VH>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.tvNews)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    "Elizabeth Dwoskin"
                )
            )
        )
    }
}