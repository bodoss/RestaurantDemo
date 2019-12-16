package com.bodoss.restaurantdemo.ui

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.bodoss.restaurantdemo.MainActivity
import com.bodoss.restaurantdemo.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainScreenTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testSort() {
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("distance"))).perform(click())
        Thread.sleep(500)
        onView(
            Matchers.allOf(
                withText("distance"),
                not(isDescendantOfA(withId(R.id.rv)))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun testFilter() {
        onView(withId(R.id.editText)).perform(TypeTextAction("tano"))
        Thread.sleep(500)
        onView(withText("Tanoshii Sushi")).check(matches(isDisplayed()))
    }

}