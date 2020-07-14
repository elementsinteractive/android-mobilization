/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.interactor.test

import kotlin.test.assertTrue
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import nl.elements.mobilization.interactor.Interactor
import nl.elements.mobilization.interactor.InvokeError
import nl.elements.mobilization.interactor.InvokeStarted
import nl.elements.mobilization.interactor.InvokeSuccess
import nl.elements.mobilization.interactor.invoke
import nl.elements.mobilization.shared.test.TestCoroutineRule
import org.junit.Rule
import org.junit.Test

class InteractorTest {

    @get:Rule
    val testRule = TestCoroutineRule()

    @Test
    fun `invoke()  an Interactor - without params - emits InvokeStarted & InvokeSuccess only`() {
        val testInteractor = object : Interactor<Unit>() {
            override suspend fun doWork(params: Unit) {
                withContext(testRule.dispatcher) {
                }
            }
        }

        testRule.dispatcher.runBlockingTest {
            testInteractor.invoke()
                .take(2)
                .onEach {
                    assertTrue {
                        it is InvokeStarted || it is InvokeSuccess
                    }
                }
                .launchIn(this)
        }
    }

    @Test
    fun `invoke() an Interactor  - with a timeout - results in an InvokeError with TimeoutCancellationException`() {
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
                    } else if (status is InvokeError) {
                        assertTrue {
                            status.throwable is TimeoutCancellationException
                        }
                    }
                }
                .launchIn(this)
        }
    }

    @Test
    fun `executeSync()  - with params - does not emit status`() {
        val testInteractor = object : Interactor<Unit>() {
            override suspend fun doWork(params: Unit) {
                withContext(testRule.dispatcher) {
                }
            }
        }

        testRule.dispatcher.runBlockingTest {
            testInteractor.executeSync(Unit)
        }
    }
}
