package com.adammcneilly.espressopatronum

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

/**
 * Defines a robot responsible for controlling the registration flow.
 */
class RegistrationRobot {

    fun firstName(firstName: String): RegistrationRobot {
        onView(FIRST_NAME_INPUT_MATCHER).perform(clearText(), typeText(firstName), closeSoftKeyboard())
        return this
    }

    fun lastName(lastName: String): RegistrationRobot {
        onView(LAST_NAME_INPUT_MATCHER).perform(clearText(), typeText(lastName), closeSoftKeyboard())
        return this
    }

    fun email(email: String): RegistrationRobot {
        onView(EMAIL_INPUT_MATCHER).perform(clearText(), typeText(email), closeSoftKeyboard())
        return this
    }

    fun phone(phone: String): RegistrationRobot {
        onView(PHONE_INPUT_MATCHER).perform(clearText(), typeText(phone), closeSoftKeyboard())
        return this
    }

    fun register(): RegistrationRobot {
        onView(REGISTER_INPUT_MATCHER).perform(click())
        return this
    }

    fun assertFullNameDisplay(fullName: String): RegistrationRobot {
        onView(FULL_NAME_DISPLAY_MATCHER).check(matches(withText(fullName)))
        return this
    }

    fun assertEmailDisplay(email: String): RegistrationRobot {
        onView(EMAIL_DISPLAY_MATCHER).check(matches(withText(email)))
        return this
    }

    fun assertPhoneDisplay(phone: String): RegistrationRobot {
        onView(PHONE_DISPLAY_MATCHER).check(matches(withText(phone)))
        return this
    }

    fun assertFirstNameError(error: String): RegistrationRobot {
        onView(FIRST_NAME_INPUT_MATCHER).check(matches(hasErrorText(error)))
        return this
    }

    fun assertLastNameError(error: String): RegistrationRobot {
        onView(LAST_NAME_INPUT_MATCHER).check(matches(hasErrorText(error)))
        return this
    }

    fun assertEmailError(error: String): RegistrationRobot {
        onView(EMAIL_INPUT_MATCHER).check(matches(hasErrorText(error)))
        return this
    }

    fun assertPhoneError(error: String): RegistrationRobot {
        onView(PHONE_INPUT_MATCHER).check(matches(hasErrorText(error)))
        return this
    }

    companion object {
        private val FIRST_NAME_INPUT_MATCHER = withId(R.id.etFirstName)
        private val LAST_NAME_INPUT_MATCHER = withId(R.id.etLastName)
        private val EMAIL_INPUT_MATCHER = withId(R.id.etEmail)
        private val PHONE_INPUT_MATCHER = withId(R.id.etPhone)
        private val REGISTER_INPUT_MATCHER = withId(R.id.registerButton)

        private val FULL_NAME_DISPLAY_MATCHER = withId(R.id.tvFullName)
        private val EMAIL_DISPLAY_MATCHER = withId(R.id.tvEmailAddress)
        private val PHONE_DISPLAY_MATCHER = withId(R.id.tvPhoneNumber)
    }
}