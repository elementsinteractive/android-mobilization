package nl.elements.mobilization.interactor.test

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import nl.elements.mobilization.interactor.Interactor
import nl.elements.mobilization.interactor.InvokeError
import nl.elements.mobilization.interactor.InvokeStarted
import nl.elements.mobilization.interactor.InvokeStatus
import nl.elements.mobilization.interactor.InvokeSuccess
import nl.elements.mobilization.interactor.invoke
import nl.elements.mobilization.shared.test.TestCoroutineRule
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class InteractorTest {

    @get:Rule
    val testRule = TestCoroutineRule()

    @Test
    fun `Invoke an Interactor and retrieve status`() {
        val testInteractor = object : Interactor<Unit>() {
            override suspend fun doWork(params: Unit) {
                withContext(testRule.dispatcher) {

                }
            }
        }

        testRule.dispatcher.runBlockingTest {
            testInteractor.invoke()
                .take(3)
                .launchIn(this)
        }
    }

    @Test
    fun `Invoke an Interactor with timeout results in error`() {
        val testInteractor = object : Interactor<Unit>() {
            override suspend fun doWork(params: Unit) {
                withContext(testRule.dispatcher) {
                    delay(1000)
                }
            }
        }

        testRule.dispatcher.runBlockingTest {
            testInteractor.invoke(Unit, 500)
                .take(2)
                .onEach { status ->
                    if (status is InvokeStarted) {
                        advanceTimeBy(550)
                    }
                }
                .launchIn(this)
        }
    }
}
