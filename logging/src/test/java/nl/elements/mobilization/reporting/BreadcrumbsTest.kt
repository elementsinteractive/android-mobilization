package nl.elements.mobilization.reporting

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.mockk.mockk
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import kotlin.test.assertEquals

class BreadcrumbsTest {

    private val breadcrumbs = Breadcrumbs()

    private val activity: Activity = mockk()
    private val bundle: Bundle = mockk()
    private val fragment: Fragment = mockk()
    private val fragmentManager: FragmentManager = mockk()

    @Before
    fun setup() {
        Timber.uprootAll()
    }

    @Test
    fun `onActivityPaused - when called - logs info`() {
        // Given [activity]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onActivityPaused(activity)

        // Then
        val expectedMessage = log("<  onPaused", activity)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onActivityResumed - when called - logs info`() {
        // Given [activity]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onActivityResumed(activity)

        // Then
        val expectedMessage = log(">  onResume", activity)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onActivityDestroyed - when called - logs info`() {
        // Given [activity]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onActivityDestroyed(activity)

        // Then
        val expectedMessage = log("<< onDestroy", activity)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onActivityCreated - when called - logs info`() {
        // Given [activity]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onActivityCreated(activity, null)

        // Then
        val expectedMessage = log(">> onCreate", activity)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onFragmentCreated - when called - logs info`() {
        // Given [fragmentManager, fragment]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onFragmentCreated(fragmentManager, fragment, null)

        // Then
        val expectedMessage = log(">> onCreate", fragment)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onFragmentDestroyed - when called - logs info`() {
        // Given [fragmentManager, fragment]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onFragmentDestroyed(fragmentManager, fragment)

        // Then
        val expectedMessage = log("<< onDestroy", fragment)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onFragmentAttached - when called - logs info`() {
        // Given [fragmentManager, fragment, activity]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onFragmentAttached(fragmentManager, fragment, activity)

        // Then
        val expectedMessage = log(">> onAttach", fragment)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onFragmentDetached - when called - logs info`() {
        // Given [fragmentManager, fragment]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onFragmentDetached(fragmentManager, fragment)

        // Then
        val expectedMessage = log("<< onDetach", fragment)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onFragmentPaused - when called - logs info`() {
        // Given [fragmentManager, fragment]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onFragmentPaused(fragmentManager, fragment)

        // Then
        val expectedMessage = log("<  onPause", fragment)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onFragmentResumed - when called - logs info`() {
        // Given [fragmentManager, fragment]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onFragmentResumed(fragmentManager, fragment)

        // Then
        val expectedMessage = log(">  onResume", fragment)
        assertEquals(expectedMessage, message)
    }

    @Test
    fun `onActivitySaveInstanceState - when called - no log is performed`() {
        // Given [activity, bundle]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onActivitySaveInstanceState(activity, bundle)

        // Then
        assertNull(message)
    }

    @Test
    fun `onActivityStarted - when called - no log is performed`() {
        // Given [activity]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onActivityStarted(activity)

        // Then
        assertNull(message)
    }

    @Test
    fun `onActivityStopped - when called - no log is performed`() {
        // Given [activity]
        var message: String? = null
        plantTree { message = it }

        // When
        breadcrumbs.onActivityStopped(activity)

        // Then
        assertNull(message)
    }

    private fun log(prefix: String, activity: Any?): String = when (activity) {
        is Activity -> "$prefix: Activity"
        is Fragment -> "$prefix: Fragment"
        else -> throw IllegalArgumentException("$activity is not supported")
    }

    private fun plantTree(message: (String) -> Unit) {
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                assertEquals(Log.INFO, priority)
                message(message)
            }
        })
    }


}