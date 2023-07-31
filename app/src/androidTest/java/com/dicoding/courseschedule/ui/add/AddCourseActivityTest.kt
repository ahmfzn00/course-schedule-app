package com.dicoding.courseschedule.ui.add

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AddCourseActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun assertGetCircumference() {
        Espresso.onView(withId(R.id.action_add)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.action_add)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.add_ed_note))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}