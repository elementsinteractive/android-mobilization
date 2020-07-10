/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.logging.test

import io.mockk.mockk
import kotlin.test.assertEquals
import nl.elements.mobilization.logging.ElementsLogger
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class ElementsLoggerTest {

    private val logger = ElementsLogger(mockk())

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
}
