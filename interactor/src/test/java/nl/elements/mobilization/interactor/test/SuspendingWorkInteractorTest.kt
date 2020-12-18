/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.interactor.test

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import nl.elements.mobilization.interactor.SuspendingWorkInteractor
import nl.elements.mobilization.shared.test.TestCoroutineRule
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SuspendingWorkInteractorTest {
    @get:Rule
    val testRule = TestCoroutineRule()

    @Test
    fun `invoke() a SuspendingWorkInteractor - with params - Returns result in observe stream`() {
        val result = "Elements"

        val testSuspendingWorkInteractor = object : SuspendingWorkInteractor<Unit, String>() {
            override suspend fun doWork(params: Unit): String =
                withContext(testRule.dispatcher) {
                    result
                }
        }

        testRule.dispatcher.runBlockingTest {
            launch {
                testSuspendingWorkInteractor.observe()
                    .take(1)
                    .onEach {
                        assertEquals(result, it)
                    }
                    .launchIn(this)

                testSuspendingWorkInteractor(Unit)
            }
        }
    }
}
