package com.adammcneilly.espressopatronum

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.adammcneilly.espressopatronum.TestUtils.takeScreenshot
import com.squareup.spoon.SpoonRule

/**
 * Defines a robot responsible for controlling the registration flow.
 */
class RegistrationRobot(private val spoon: SpoonRule) {

    fun firstName(firstName: String): RegistrationRobot {
        onView(FIRST_NAME_INPUT_MATCHER).perform(clearText(), typeText(firstName), closeSoftKeyboard())
        takeScreenshot(spoon, "first_name_entered")
        return this
    }

    fun lastName(lastName: String): RegistrationRobot {
        onView(LAST_NAME_INPUT_MATCHER).perform(clearText(), typeText(lastName), closeSoftKeyboard())
        takeScreenshot(spoon, "last_name_entered")
        return this
    }

    fun email(email: String): RegistrationRobot {
        onView(EMAIL_INPUT_MATCHER).perform(clearText(), typeText(email), closeSoftKeyboard())
        takeScreenshot(spoon, "email_entered")
        return this
    }

    fun phone(phone: String): RegistrationRobot {
        onView(PHONE_INPUT_MATCHER).perform(clearText(), typeText(phone), closeSoftKeyboard())
        takeScreenshot(spoon, "phone_entered")
        return this
    }

    fun emailOptIn(): RegistrationRobot {
        onView(OPT_IN_MATCHER).perform(click())
        takeScreenshot(spoon, "opted_in")
        return this
    }

    fun register(): RegistrationRobot {
        takeScreenshot(spoon, "register_clicked")
        onView(REGISTER_INPUT_MATCHER).perform(click())
        return this
    }

    fun assertEmailError(error: String): RegistrationRobot {
        onView(EMAIL_INPUT_MATCHER).check(matches(hasErrorText(error)))
        takeScreenshot(spoon, "assert_email_error")
        return this
    }

    companion object {
        private val FIRST_NAME_INPUT_MATCHER = withId(R.id.etFirstName)
        private val LAST_NAME_INPUT_MATCHER = withId(R.id.etLastName)
        private val EMAIL_INPUT_MATCHER = withId(R.id.etEmail)
        private val PHONE_INPUT_MATCHER = withId(R.id.etPhone)
        private val OPT_IN_MATCHER = withId(R.id.rbEmailOptIn)
        private val REGISTER_INPUT_MATCHER = withId(R.id.registerButton)
    }
}