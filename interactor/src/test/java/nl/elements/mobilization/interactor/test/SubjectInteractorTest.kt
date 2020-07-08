/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.interactor.test

import kotlin.test.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import nl.elements.mobilization.interactor.SubjectInteractor
import nl.elements.mobilization.shared.test.TestCoroutineRule
import org.junit.Rule
import org.junit.Test

class SubjectInteractorTest {

    @get:Rule
    val testRule = TestCoroutineRule()

    @Test
    fun `invoke() a SubjectInteractor - with params - returns a result`() {
        val result = "Elements"

        val testSubjectInteractor = object : SubjectInteractor<Unit, String>() {
            override fun createObservable(params: Unit): Flow<String> = flow {
                emit(result)
            }
        }

        testRule.dispatcher.runBlockingTest {
            testSubjectInteractor.observe()
                .take(1)
                .onEach {
                    assertEquals(result, it)
                }
                .launchIn(this)

            testSubjectInteractor(Unit)
        }
    }
}
