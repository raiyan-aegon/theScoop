package com.mukhtarinc.thescoop.ui.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mukhtarinc.thescoop.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Raiyan Mukhtar on 7/21/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginScreenActivityTest{


    @get: Rule
    val activityRule = ActivityScenarioRule(LoginScreenActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.log_in)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navToMainActivityWithSkipButton(){

        //To check if button is clicked
        onView(withId(R.id.SkipBT)).perform(ViewActions.click())

        //To check if MainActivity is in view
        onView(withId(R.id.parent_container_main))
                .check(matches(isDisplayed()))


    }

    @Test
    fun test_navToSignUpActivityWithSignUpButton(){

        //To check if button is clicked
        onView(withId(R.id.materialTextView_signUp)).perform(ViewActions.click())

        //To check if SignUPActivity is in view
        onView(withId(R.id.sign_up_main))
                .check(matches(isDisplayed()))

    }

    @Test
    fun test_navToBackToLoginScreen(){

        //To check if button is clicked
        onView(withId(R.id.materialTextView_signUp)).perform(ViewActions.click())

        //To check if SignUPActivity is in view
        onView(withId(R.id.sign_up_main))
                .check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.log_in)).check(matches(isDisplayed()))


    }


}