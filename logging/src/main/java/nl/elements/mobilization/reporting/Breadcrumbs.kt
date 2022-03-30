/*
 * Copyright 2021 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.reporting

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import timber.log.Timber.i

/**
 * Automatic breadcrumbs generation for Android applications
 * This tracks the most important lifecycle events and posts them Timber.
 * Androidx fragments are automatically hooked and logged as well.
 *
 * There is some minimal built-in breadcrumbs generation in Crashlytics that gets automatically
 * enabled when Google Analytics is implemented, but that design choice can be considered a privacy
 * issue, nor does it log any of the terminating events - of which experience has shown are more
 * highly correlated to issues compared to starting events.
 *
 */
@Suppress("TooManyFunctions")
class Breadcrumbs : Application.ActivityLifecycleCallbacks, FragmentManager.FragmentLifecycleCallbacks() {

    override fun onActivityPaused(activity: Activity) = log("<  onPaused", activity)

    override fun onActivityResumed(activity: Activity) = log(">  onResume", activity)

    override fun onActivityDestroyed(activity: Activity) = log("<< onDestroy", activity)

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        log(">> onCreate", activity)

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) =
        log(">> onCreate", f)

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) = log("<< onDestroy", f)

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) =
        log(">> onAttach", f)

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) = log("<< onDetach", f)

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) = log("<  onPause", f)

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) = log(">  onResume", f)

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Unused mandatory overrides
    }

    override fun onActivityStarted(activity: Activity) {
        // Unused mandatory overrides
    }

    override fun onActivityStopped(activity: Activity) {
        // Unused mandatory overrides
    }

    private fun screenToString(activity: Any?): String =
        activity?.javaClass?.simpleName ?: "(null)"

    private fun log(prefix: String, activity: Any?) = i("$prefix: ${screenToString(activity)}")
}
