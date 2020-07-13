/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.interactor.test

import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import nl.elements.mobilization.interactor.ResultInteractor
import nl.elements.mobilization.interactor.invoke
import nl.elements.mobilization.shared.test.TestCoroutineRule
import org.junit.Rule
import org.junit.Test

class ResultInteractorTest {

    @get:Rule
    val testRule = TestCoroutineRule()

    @Test
    fun `invoke() a ResultInteractor - with params - emits a result`() {
        val result = "Elements"

        val testResultInteractor = object : ResultInteractor<Unit, String>() {
            override suspend fun doWork(params: Unit): String = result
        }

        testRule.dispatcher.runBlockingTest {
            val actual = testResultInteractor().first()

            assertEquals(result, actual)
        }
    }
}
