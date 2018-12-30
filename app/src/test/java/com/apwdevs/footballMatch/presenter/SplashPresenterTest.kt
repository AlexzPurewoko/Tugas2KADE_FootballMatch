package com.apwdevs.footballMatch.presenter

import android.content.Context
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.apiRequest.GetLeagueSoccer
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.LeagueResponse
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.TeamLeagueData
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.presenter.SplashPresenter
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.ui.SplashView
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.utility.CoroutineContextProvider
import com.apwdevs.footballMatch.utility.TestCoroutineContext
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SplashPresenterTest {
    @Mock
    private lateinit var ctx: Context

    @Mock
    private lateinit var view: SplashView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: SplashPresenter
    private lateinit var coroutine: CoroutineContextProvider
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        coroutine = TestCoroutineContext()
        presenter = SplashPresenter(ctx, view, apiRepository, gson, true, coroutine)
    }

    @Test
    fun testGetLeagueList() {
        val leagues: MutableList<TeamLeagueData> = mutableListOf()
        val response = LeagueResponse(leagues)

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(GetLeagueSoccer.getAllLeague()).await(),
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueList()
            Mockito.verify(view).onLoadDataStarted()
            Mockito.verify(view).onLOadDataFinished()
            Mockito.verify(view).showLeagueInSpinner(leagues)
        }
    }

}