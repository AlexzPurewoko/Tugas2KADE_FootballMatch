package com.apwdevs.footballMatch.koneksi

import com.apwdevs.footballMatch.utility.CekKoneksi
import com.apwdevs.footballMatch.utility.TestCoroutineContext
import org.junit.Test
import org.mockito.Mockito

class CekKoneksiWithAPI {
    @Test
    fun isConnected() {
        val cekKoneksi = Mockito.mock(CekKoneksi::class.java)
        val coroutine = TestCoroutineContext()
        cekKoneksi.isReachableNetworks("www.thesportsdb.com", coroutine)
        Mockito.verify(cekKoneksi).isReachableNetworks("www.thesportsdb.com", coroutine)
    }
}