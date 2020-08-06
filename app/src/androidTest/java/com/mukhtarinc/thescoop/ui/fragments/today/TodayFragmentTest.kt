package com.mukhtarinc.thescoop.ui.fragments.today

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mukhtarinc.thescoop.R
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class TodayFragmentTest{

    @Test
    fun test_TodayFragment_In_View() {
//        val scenario  = launchFragmentInContainer()

        onView(withId(R.id.today))
                .perform(ViewActions.click())

        onView(withId(R.id.today_parent))
                .check(matches(isDisplayed()))
    }




}