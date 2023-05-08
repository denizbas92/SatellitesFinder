package com.example.satellitesfinder.list

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.satellitesfinder.R
import com.example.satellitesfinder.list.view.SatelliteListActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SatelliteListActivityTest {
    @Test
    fun activityScenarioShown(){
        val activityScenario = ActivityScenario.launch(SatelliteListActivity::class.java)
        onView(withId(R.id.satellite_list_activity)).check(matches(isDisplayed()))
    }
}