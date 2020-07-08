/*
 * Copyright 2020 Elements
 *
 * Licensed under the BSD-3 license:
 * https://opensource.org/licenses/BSD-3-Clause
 */
package nl.elements.mobilization.interactor

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withTimeout

/**
 * Base interactor class to be used for work without a specific result.
 * Examples:
 * - updating the local database
 * - perform an api call and update the database (without having to know the actual result)
 *
 * returns: [InvokeStatus] in a flow on invoke to indicate the status of the work
 */
abstract class Interactor<in P> {
    operator fun invoke(params: P, timeoutMs: Long = defaultTimeoutMs): Flow<InvokeStatus> {
        return flow {
            withTimeout(timeoutMs) {
                emit(InvokeStarted)
                doWork(params)
                emit(InvokeSuccess)
            }
        }.catch { t ->
            emit(InvokeError(t))
        }
    }

    /**
     * Executes the work synchronously without knowing the status
     */
    suspend fun executeSync(params: P) = doWork(params)

    /**
     * The actual work to be executed
     */
    protected abstract suspend fun doWork(params: P)

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}

/**
 * Interactor used to get explicit result from the work performed
 *
 * Code example:
 * ```
 * viewModelScope.launch {
 *      val result = getResult(params).first()
 * }
 * ```
 */
abstract class ResultInteractor<in P, R> {
    /**
     * To invoke the interactor
     *
     * @return: Flow with the result
     */
    operator fun invoke(params: P): Flow<R> {
        return flow {
            emit(doWork(params))
        }
    }

    /**
     * The actual work to be executed
     */
    protected abstract suspend fun doWork(params: P): R
}

/**
 * Performs suspending work
 *
 * Examples:
 * - can be used within async / launch blocks from the viewmodel
 * - direct search
 *
 * Code example:
 * ```
 *  viewModelScope.launch {
 *      val job = launch {
 *          search(Params(query))
 *      }
 *      job.join()
 *  }
 * ```
 */
abstract class SuspendingWorkInteractor<P : Any, T : Any> : SubjectInteractor<P, T>() {
    override fun createObservable(params: P): Flow<T> = flow {
        emit(doWork(params))
    }

    /**
     * The actual work to be executed
     */
    abstract suspend fun doWork(params: P): T
}

/**
 * Performs background work and is observed separately
 *
 * Examples:
 * - retrieving database queries
 * - retrieving asynchronous tasks
 *
 * Code example:
 * ```
 * viewModelScope.launch() {
 *      observeUser.observe().collect { // update state }
 * }
 *
 * observeUser()
 * ```
 */
abstract class SubjectInteractor<P : Any, T> {
    private val channel = Channel<P>(Channel.CONFLATED)

    operator fun invoke(params: P) = channel.offer(params)

    /**
     * The observable work to be returned
     */
    protected abstract fun createObservable(params: P): Flow<T>

    fun observe(): Flow<T> = channel.receiveAsFlow()
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
}

// Handy extensions functions for interactors with Unit params
operator fun Interactor<Unit>.invoke() = invoke(Unit)
operator fun <T> SubjectInteractor<Unit, T>.invoke() = invoke(Unit)
operator fun <T> ResultInteractor<Unit, T>.invoke() = invoke(Unit)
