package com.apwdevs.footballMatch.koneksi

import com.apwdevs.footballMatch.utility.CekKoneksi
import com.apwdevs.footballMatch.utility.TestCoroutineContext
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CekKoneksiTest {
    @Test
    fun isConnected() {
        val cekKoneksi = mock(CekKoneksi::class.java)
        val coroutine = TestCoroutineContext()
        cekKoneksi.isReachableNetworks(coroutineContextProvider = coroutine)
        verify(cekKoneksi).isReachableNetworks(coroutineContextProvider = coroutine)
    }
}