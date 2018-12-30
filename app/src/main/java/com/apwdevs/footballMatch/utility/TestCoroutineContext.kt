package com.apwdevs.footballMatch.utility

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContext : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
}