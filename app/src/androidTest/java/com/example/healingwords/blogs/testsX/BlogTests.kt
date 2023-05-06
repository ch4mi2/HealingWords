package com.example.healingwords.posts.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.healingwords.*
import com.example.healingwords.databinding.ActivityAddABlogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BlogTests {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(AddBlog::class.java)

    private lateinit var activityScenario: ActivityScenario<NewPostActivity>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    @Before
    fun setUp() {

        // Initialize Intents
        Intents.init()
    }


    @Test
    fun addBlog() {

        //first login before testing


        onView(withId(R.id.newBlogTitle)).perform(
            ViewActions.typeText("blog title test"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.newBlogDesc)).perform(
            ViewActions.typeText("blog desc  test"),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.postBtn)).perform(click())

        onView(withId(R.id.main_activity_layout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        intended(hasComponent(MainActivity::class.java.name))
        Intents.release() // Release Intents

    }

}