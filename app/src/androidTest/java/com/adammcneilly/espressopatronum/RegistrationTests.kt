package com.adammcneilly.espressopatronum

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
        RegistrationRobot()
                .firstName("Adam")
                .lastName("McNeilly")
                .email("amcneilly@okcupid.com")
                .phone("1234567890")
                .register()
                .assertFullNameDisplay("Adam McNeilly")
                .assertEmailDisplay("amcneilly@okcupid.com")
                .assertPhoneDisplay("(123)-456-7890")
    }

    @Test
    fun testMissingEmailError() {
        RegistrationRobot()
                .firstName("Adam")
                .lastName("McNeilly")
                .phone("1234567890")
                .register()
                .assertEmailError("Must enter an email address.")
    }

    @Test
    fun testInvalidEmailError() {
        RegistrationRobot()
                .firstName("Adam")
                .lastName("McNeilly")
                .email("blahblah")
                .phone("1234567890")
                .register()
                .assertEmailError("Must enter a valid email address.")
    }
}