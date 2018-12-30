package com.apwdevs.footballMatch.presenter

import android.content.Context
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.MATCH_TYPE
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueResponse
import com.apwdevs.footballMatch.fragmentComponents.presenter.FragmentMatchPresenter
import com.apwdevs.footballMatch.fragmentComponents.ui.FragmentMatchModel
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

class FragmentMatchPresenterTest {
    @Mock
    private lateinit var ctx: Context

    @Mock
    private lateinit var view: FragmentMatchModel

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: FragmentMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FragmentMatchPresenter(ctx, view, gson, apiRepository, true, TestCoroutineContext())
    }

    @Test
    fun testFragmentLastMatch() {
        val leagues: MutableList<MatchTeamLeagueData> = mutableListOf()
        val response = MatchTeamLeagueResponse(leagues)
        val leagueId = "4328"
        val matchType = MATCH_TYPE.LAST_MATCH

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchTeamLeagueResponse::class.java
                )
            ).thenReturn(response)


            presenter.getMatch(leagueId, matchType)
            Mockito.verify(view).onShowLoading()
            Mockito.verify(view).onHideLoading()
            Mockito.verify(view).onShowMatch(response.events)

        }
    }

    @Test
    fun testFragmentNextMatch() {
        val leagues: MutableList<MatchTeamLeagueData> = mutableListOf()
        val response = MatchTeamLeagueResponse(leagues)
        val leagueId = "4328"
        val matchType = MATCH_TYPE.NEXT_MATCH

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchTeamLeagueResponse::class.java
                )
            ).thenReturn(response)


            presenter.getMatch(leagueId, matchType)
            Mockito.verify(view).onShowLoading()
            Mockito.verify(view).onHideLoading()
            Mockito.verify(view).onShowMatch(response.events)

        }
    }
}