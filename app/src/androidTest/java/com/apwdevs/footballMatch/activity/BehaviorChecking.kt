package com.apwdevs.footballMatch.activity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.apwdevs.footballMatch.R.id.*
import com.apwdevs.footballMatch.SplashScreen
import com.apwdevs.footballMatch.utility.ParameterClass
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BehaviorChecking {
    companion object {
        const val LONG_TIME = 3000L
        const val MIDDLE_TIME = 2000L
        const val SHORT_TIME = 1500L
    }

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(SplashScreen::class.java)

    @Test
    fun behaviorTest() {

        onSplashStartup()
        // suspend
        Thread.sleep(LONG_TIME)
        onTestLastMatch()
        Thread.sleep(LONG_TIME)
        //test next match
        onView(isRoot()).perform(swipeRight())
        Thread.sleep(SHORT_TIME)
        onTestNextMatch()
        onTestUnfavorite()
        Thread.sleep(MIDDLE_TIME)
    }

    private fun onTestUnfavorite() {
        // try to deletes all favorites match from Favorite menu
        for (i in 1..2) {
            onView(isRoot()).perform(swipeLeft(), swipeLeft())
            Thread.sleep(SHORT_TIME)
            onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
                .check(matches(isDisplayed()))
            onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

            // wait the ui thread until finished
            while (true) {
                try {
                    onView(withId(content_match_detail_id_recyclerlist))
                        .check(matches(isDisplayed()))
                    break
                } catch (e: Throwable) {
                    Thread.sleep(SHORT_TIME)
                }
            }

            // try to remove from favorites, if success star icon is unchecked and shows the message "Removed from Databases :("
            onView(withId(action_favorite))
                .perform(click())
            // check if its displayed or not
            onView(withText(ParameterClass.STRING_REMOVE_FROM_DATABASE)).check(matches(isDisplayed()))
            Thread.sleep(MIDDLE_TIME)
            onView(isRoot())
                .perform(pressBack())
            Thread.sleep(MIDDLE_TIME)
        }
        // if the test above success, all favorite match is deleted from Favorites menu
        onView(isRoot()).perform(swipeLeft(), swipeLeft())
    }

    private fun onTestNextMatch() {

        // wait the ui thread until finished
        while (true) {
            try {
                onView(allOf(withId(adapter_fragment_lastmatch_progressbar), isDisplayed()))
                    .check(matches(isDisplayed()))

            } catch (e: Throwable) {
                break
            }
            Thread.sleep(SHORT_TIME)
        }

        onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        // wait the ui thread until finished
        while (true) {
            try {
                onView(withId(content_match_detail_id_recyclerlist))
                    .check(matches(isDisplayed()))
                break
            } catch (e: Throwable) {
                Thread.sleep(SHORT_TIME)
            }
        }

        onView(isRoot()).perform(swipeDown())
        Thread.sleep(SHORT_TIME)
        onView(isRoot()).perform(swipeUp())
        Thread.sleep(MIDDLE_TIME)

        doTestFavorites()

        onView(isRoot())
            .perform(pressBack())
        Thread.sleep(MIDDLE_TIME)
    }

    private fun onTestLastMatch() {
        // perform check navigation

        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))

        onView(withId(container))
            .check(matches(isDisplayed()))

        // wait the ui thread until finished
        while (true) {
            try {
                onView(allOf(withId(adapter_fragment_lastmatch_progressbar), isDisplayed()))
                    .check(matches(isDisplayed()))

            } catch (e: Throwable) {
                break
            }
            Thread.sleep(SHORT_TIME)
        }

        onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
            .check(matches(isDisplayed()))

        // check the viewpager
        for (a in 1..2) {
            when (a) {
                1 -> onView(isRoot()).perform(swipeLeft(), swipeLeft())
                2 -> onView(isRoot()).perform(swipeRight(), swipeRight())
            }
            Thread.sleep(MIDDLE_TIME)
        }
        //////////

        // check in match detail and add into favorites
        onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6))
        Thread.sleep(SHORT_TIME)
        onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
        Thread.sleep(SHORT_TIME)
        onView(allOf(withId(adapter_fragment_lastmatch_recyclerview), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(8, click()))
        Thread.sleep(MIDDLE_TIME)

        // wait the ui thread until finished
        while (true) {
            try {
                onView(withId(content_match_detail_id_recyclerlist))
                    .check(matches(isDisplayed()))
                break
            } catch (e: Throwable) {
                Thread.sleep(SHORT_TIME)
            }
        }
        Thread.sleep(SHORT_TIME)
        onView(isRoot()).perform(swipeUp())
        Thread.sleep(SHORT_TIME)
        onView(isRoot()).perform(swipeDown())
        Thread.sleep(MIDDLE_TIME)

        doTestFavorites()

        onView(isRoot())
            .perform(pressBack())
        Thread.sleep(MIDDLE_TIME)
        //// swipe into last left section (Favorites Menu)
        onView(withId(container)).perform(swipeLeft(), swipeLeft())
        ///// The match can be shown in favorites menu after we added it into favorites from DetailMatch Activity
        Thread.sleep(MIDDLE_TIME)
    }

    private fun onSplashStartup() {
        // check loading
        // wait the UI thread until finish
        while (true) {
            try {
                onView(withId(splash_relative_loading))
                    .check(matches(isDisplayed()))
            } catch (e: Throwable) {
                break
            }
            Thread.sleep(SHORT_TIME)
        }

        Thread.sleep(SHORT_TIME)
        // check container of league content
        onView(withId(splash_linear_final))
            .check(matches(isDisplayed()))
        // check text title
        onView(withId(splash_text_title))
            .check(matches(isDisplayed()))
        // check text description
        onView(withId(splash_text_desc))
            .check(matches(isDisplayed()))
        // check recycler view
        onView(withId(splash_recycler))
            .check(matches(isDisplayed()))

        // check recycler can be scrolled
        onView(withId(splash_recycler))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(30))
        Thread.sleep(SHORT_TIME)
        onView(withId(splash_recycler))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Thread.sleep(SHORT_TIME)
        // select the item in recycler view
        onView(withId(splash_recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

    }

    private fun doTestFavorites() {
        // try to add into favorites, if success star icon is activated and shows the message "Added into Databases :)"
        onView(withId(action_favorite))
            .perform(click())
        // check the snackbar text, is displayed or not
        onView(withText(ParameterClass.STRING_ADD_INTO_DATABASE)).check(matches(isDisplayed()))
        Thread.sleep(MIDDLE_TIME)
        // try to remove from favorites, if success star icon is unchecked and shows the message "Removed from Databases :("
        onView(withId(action_favorite))
            .perform(click())
        // check the snackbar text, is displayed or not
        onView(withText(ParameterClass.STRING_REMOVE_FROM_DATABASE)).check(matches(isDisplayed()))
        Thread.sleep(MIDDLE_TIME)
        // we have to add again into favorites, and after that, we have to check into Favorite menu in Home, whether is added or not
        onView(withId(action_favorite))
            .perform(click())
        // check the snackbar text, is displayed or not
        onView(withText(ParameterClass.STRING_ADD_INTO_DATABASE)).check(matches(isDisplayed()))
        Thread.sleep(MIDDLE_TIME)
    }

}