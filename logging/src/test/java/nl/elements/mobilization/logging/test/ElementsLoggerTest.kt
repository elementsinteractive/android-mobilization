package nl.elements.mobilization.logging.test

import io.mockk.mockk
import nl.elements.mobilization.logging.ElementsLogger
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import kotlin.test.assertEquals

class ElementsLoggerTest {

    private val logger = ElementsLogger(mockk())

    @Before
    fun setup() {
        Timber.uprootAll()
    }

    @Test
    fun `ElementsLogger does not plant any tree when not requested`() {
        logger.setup(debugMode = false, logsToCrashlytics = false)

        assertEquals(0, Timber.treeCount())
    }

    @Test
    fun `ElementsLogger plants DebugTree when requested`() {
        logger.setup(debugMode = true, logsToCrashlytics = false)

        assertEquals(1, Timber.treeCount())
    }

    @Test
    fun `ElementsLogger plants CrashlyticsTree when requested`() {
        logger.setup(debugMode = false, logsToCrashlytics = true)

        assertEquals(1, Timber.treeCount())
    }
}
