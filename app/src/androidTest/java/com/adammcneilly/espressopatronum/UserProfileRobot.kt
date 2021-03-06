package com.adammcneilly.espressopatronum

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adammcneilly.espressopatronum.TestUtils.takeScreenshot
import com.squareup.spoon.SpoonRule

/**
 * Robot that runs actions and assertions on the [UserProfileFragment].
 */
class UserProfileRobot(private val spoon: SpoonRule) {

    fun assertFullNameDisplay(fullName: String): UserProfileRobot {
        onView(FULL_NAME_DISPLAY_MATCHER).check(matches(withText(fullName)))
        takeScreenshot(spoon, "assert_full_name_display")
        return this
    }

    fun assertEmailDisplay(email: String): UserProfileRobot {
        onView(EMAIL_DISPLAY_MATCHER).check(matches(withText(email)))
        takeScreenshot(spoon, "assert_email_display")
        return this
    }

    fun assertPhoneDisplay(phone: String): UserProfileRobot {
        onView(PHONE_DISPLAY_MATCHER).check(matches(withText(phone)))
        takeScreenshot(spoon, "assert_phone_display")
        return this
    }

    fun assertOptedIn(): UserProfileRobot {
        onView(EMAIL_OPT_IN_DISPLAY_MATCHER).check(matches(isChecked()))
        takeScreenshot(spoon, "assert_email_opt_in")
        return this
    }

    fun assertOptedOut(): UserProfileRobot {
        onView(EMAIL_OPT_IN_DISPLAY_MATCHER).check(matches(isNotChecked()))
        takeScreenshot(spoon, "assert_email_opt_out")
        return this
    }

    companion object {
        private val FULL_NAME_DISPLAY_MATCHER = withId(R.id.tvFullName)
        private val EMAIL_DISPLAY_MATCHER = withId(R.id.tvEmailAddress)
        private val PHONE_DISPLAY_MATCHER = withId(R.id.tvPhoneNumber)
        private val EMAIL_OPT_IN_DISPLAY_MATCHER = withId(R.id.cbOptedIn)
    }
}