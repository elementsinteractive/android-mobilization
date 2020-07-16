/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.logging.test

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import nl.elements.mobilization.logging.ElementsLogger
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class ElementsLoggerTest {

    private val firebaseCrashlytics: FirebaseCrashlytics = mockk()
    private val logger = ElementsLogger(firebaseCrashlytics)

    @Before
    fun setup() {
        Timber.uprootAll()
    }

    @Test
    fun `setup() - when not requested - does not plant any trees`() {
        // Given the [ElementsLogger]

        // When setup
        logger.setup(debugMode = false, logsToCrashlytics = false)

        // Then there are no trees
        assertEquals(0, Timber.treeCount())
    }

    @Test
    fun `setup() - when requested - plants a DebugTree`() {
        // Given the [ElementsLogger]

        // When setup with at least debug mode
        logger.setup(debugMode = true, logsToCrashlytics = false)

        // Then there is at least 1 tree
        assertEquals(1, Timber.treeCount())
    }

    @Test
    fun `setup() - when requested - plants a CrashlyticsTree`() {
        // Given the [ElementsLogger]

        // When setup with crashlytics
        logger.setup(debugMode = false, logsToCrashlytics = true)

        // Then there is at least 1 tree
        assertEquals(1, Timber.treeCount())
    }

    @Test
    fun `setCustomKey - when setting custom key - a key value pair is set`() {
        // Given
        val key = "key"
        val value = "value"

        every { firebaseCrashlytics.setCustomKey(key, value) } returns Unit

        // When
        logger.setCustomKey(key, value)

        // Then
        verify { firebaseCrashlytics.setCustomKey(key, value) }
    }

    @Test
    fun `logger v - when logging - receives logs`() {
        var count = 0
        val error = Throwable()
        val message = "msg"

        // Given
        plantTree(Log.VERBOSE, error) {
            count += 1
        }

        // When
        logger.v(error)
        logger.v(message)
        logger.v(error, message)

        // Then
        assertEquals(3, count)
    }

    @Test
    fun `logger d - when logging - receives logs`() {
        var count = 0
        val error = Throwable()
        val message = "msg"

        // Given
        plantTree(Log.DEBUG, error) {
            count += 1
        }

        // When
        logger.d(error)
        logger.d(message)
        logger.d(error, message)

        // Then
        assertEquals(3, count)
    }

    @Test
    fun `logger i - when logging - receives logs`() {
        var count = 0
        val error = Throwable()
        val message = "msg"

        // Given
        plantTree(Log.INFO, error) {
            count += 1
        }

        // When
        logger.i(error)
        logger.i(message)
        logger.i(error, message)

        // Then
        assertEquals(3, count)
    }

    @Test
    fun `logger w - when logging - receives logs`() {
        var count = 0
        val error = Throwable()
        val message = "msg"

        // Given
        plantTree(Log.WARN, error) {
            count += 1
        }

        // When
        logger.w(error)
        logger.w(message)
        logger.w(error, message)

        // Then
        assertEquals(3, count)
    }

    @Test
    fun `logger e - when logging - receives logs`() {
        var count = 0
        val error = Throwable()
        val message = "msg"

        // Given
        plantTree(Log.ERROR, error) {
            count += 1
        }

        // When
        logger.e(error)
        logger.e(message)
        logger.e(error, message)

        // Then
        assertEquals(3, count)
    }

    @Test
    fun `logger wtf - when logging - receives logs`() {
        var count = 0
        val error = Throwable()
        val message = "msg"

        // Given
        plantTree(Log.ASSERT, error) {
            count += 1
        }

        // When
        logger.wtf(error)
        logger.wtf(message)
        logger.wtf(error, message)

        // Then
        assertEquals(3, count)
    }

    private fun plantTree(expectedPriority: Int, expectedThrowable: Throwable, logged: () -> Unit) {
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                assertEquals(expectedPriority, priority)

                if (t != null) {
                    assertEquals(expectedThrowable, t)
                }

                logged()
            }
        })
    }
}
