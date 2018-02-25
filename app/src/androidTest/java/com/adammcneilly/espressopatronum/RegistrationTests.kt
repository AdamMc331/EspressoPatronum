package com.adammcneilly.espressopatronum

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Suite of test cases for the Registration flow.
 */
@RunWith(AndroidJUnit4::class)
class RegistrationTests {

    @JvmField
    @Rule
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSuccessfulRegistration() {
        onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
        onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
        onView(withId(R.id.etEmail)).perform(typeText("amcneilly@okcupid.com"))
        onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
        onView(withId(R.id.registerButton)).perform(click())

        onView(withId(R.id.tvFullName)).check(matches(withText("Adam McNeilly")))
        onView(withId(R.id.tvEmailAddress)).check(matches(withText("amcneilly@okcupid.com")))
        onView(withId(R.id.tvPhoneNumber)).check(matches(withText("(123)-456-7890")))
    }

    @Test
    fun testMissingEmailError() {
        onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
        onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
        onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
        onView(withId(R.id.registerButton)).perform(click())

        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Must enter an email address.")))
    }

    @Test
    fun testInvalidEmailError() {
        onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
        onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
        onView(withId(R.id.etEmail)).perform(typeText("blahblah"))
        onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
        onView(withId(R.id.registerButton)).perform(click())

        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Must enter a valid email address.")))
    }
}