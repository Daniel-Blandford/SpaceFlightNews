package com.danielblandford.spaceflightnews


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.danielblandford.spaceflightnews.R
import kotlinx.coroutines.awaitAll

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainUITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainUITest() {
        Thread.sleep(5000)
        val cardView = onView(
            allOf(
                withId(R.id.item_article_layout_parent),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerview_articles),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        
        cardView.perform(click())


        val cardView2 = onView(
            allOf(
                withId(R.id.item_article_layout_parent),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerview_articles),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView2.perform(click())

        val cardView3 = onView(
            allOf(
                withId(R.id.item_article_layout_parent),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerview_articles),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView3.perform(click())

        val cardView4 = onView(
            allOf(
                withId(R.id.item_article_layout_parent),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerview_articles),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView4.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
