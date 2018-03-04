package com.adammcneilly.espressopatronum

import android.app.Activity
import android.content.Context
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.base.DefaultFailureHandler
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import com.squareup.spoon.SpoonRule


/**
 * Utility methods for setting up and running tests.
 */
object TestUtils {
    fun setFailureHandler(spoon: SpoonRule, context: Context) {
        Espresso.setFailureHandler { error, viewMatcher ->
            takeScreenshot(spoon, "test_failed")
            DefaultFailureHandler(context).handle(error, viewMatcher)
        }
    }

    fun takeScreenshot(spoon: SpoonRule, description: String) {
        spoon.screenshot(getCurrentActivityInstance(), description)
    }

    private fun getCurrentActivityInstance(): Activity? {
        val activity = arrayOfNulls<Activity>(1)
        getInstrumentation().runOnMainSync {
            val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                val currentActivity = resumedActivities.iterator().next() as Activity
                activity[0] = currentActivity
            }
        }

        return activity[0]
    }
}