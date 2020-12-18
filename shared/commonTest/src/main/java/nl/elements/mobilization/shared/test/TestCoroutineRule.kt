/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.shared.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.coroutines.ContinuationInterceptor

/**
 * JUnit 4 Rule for automatically creating a [TestCoroutineDispatcher],
 * then a [TestCoroutineScope] with the same CoroutineContext.
 *
 * [TestCoroutineScope.cleanupTestCoroutines] is called after execution of each test,
 * and will cause an exception if any coroutines are leaked.
 *
 * Usage of a rule in a Kotlin JUnit 4 test is:
 *
 * class MyTest {
 *
 *   @get:Rule val testRule = TestCoroutineRule()
 *
 *   val dispatcher = testRule.dispatcher
 * }
 *
 */
@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule, TestCoroutineScope by TestCoroutineScope() {

    val dispatcher = coroutineContext[ContinuationInterceptor] as TestCoroutineDispatcher

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {

                Dispatchers.setMain(dispatcher)

                // everything above this happens before the test
                base.evaluate()
                // everything below this happens after the test

                Dispatchers.resetMain()
                cleanupTestCoroutines()
            }
        }
    }
}
