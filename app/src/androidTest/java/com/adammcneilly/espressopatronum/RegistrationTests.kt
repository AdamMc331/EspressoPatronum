package com.adammcneilly.espressopatronum

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adammcneilly.espressopatronum.TestUtils.setFailureHandler
import com.squareup.spoon.SpoonRule
import org.junit.Before
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

    @JvmField
    @Rule
    val mPermissionsRule = GrantPermissionRule.grant(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
    
    @JvmField
    @Rule
    val spoon: SpoonRule = SpoonRule()

    @Before
    fun setup() {
        setFailureHandler(spoon, rule.activity)
    }

    @Test
    fun testSuccessfulRegistrationWithOptIn() {
        registration(spoon) {
            firstName("Adam")
            lastName("McNeilly")
            email("adam@testing.com")
            phone("1234567890")
            emailOptIn()
            register()
        }

        UserProfileRobot(spoon)
                .assertFullNameDisplay("Adam McNeilly")
                .assertEmailDisplay("adam@testing.com")
                .assertPhoneDisplay("(123)-456-7890")
                .assertOptedIn()
    }

    @Test
    fun testSuccessfulRegistrationWithoutOptIn() {
        RegistrationRobot(spoon)
                .firstName("Adam")
                .lastName("McNeilly")
                .email("adam@testing.com")
                .phone("1234567890")
                .register()

        UserProfileRobot(spoon)
                .assertFullNameDisplay("Adam McNeilly")
                .assertEmailDisplay("adam@testing.com")
                .assertPhoneDisplay("(123)-456-7890")
                .assertOptedOut()
    }

    @Test
    fun testMissingEmailError() {
        RegistrationRobot(spoon)
                .firstName("Adam")
                .lastName("McNeilly")
                .phone("1234567890")
                .register()
                .assertEmailError("Must enter an email address.")
    }

    @Test
    fun testInvalidEmailError() {
        RegistrationRobot(spoon)
                .firstName("Adam")
                .lastName("McNeilly")
                .email("blahblah")
                .phone("1234567890")
                .register()
                .assertEmailError("Must enter a valid email address.")
    }
}