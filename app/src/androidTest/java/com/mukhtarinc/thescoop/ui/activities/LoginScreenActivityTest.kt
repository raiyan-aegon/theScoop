package com.mukhtarinc.thescoop.ui.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mukhtarinc.thescoop.R
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Raiyan Mukhtar on 7/21/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginScreenActivityTest{

    @Test
    fun test_isActivityInView() {
        val activityScenario  = ActivityScenario.launch(LoginScreenActivity::class.java)
        onView(withId(R.id.log_in)).check(matches(isDisplayed()))
    }
}