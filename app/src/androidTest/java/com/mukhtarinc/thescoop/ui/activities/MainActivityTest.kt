package com.mukhtarinc.thescoop.ui.activities
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mukhtarinc.thescoop.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Raiyan Mukhtar on 7/21/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isMainActivityInView(){

        onView(withId(R.id.parent_container_main))
                .check(matches(isDisplayed()))
        
    }




}