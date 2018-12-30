package com.apwdevs.footballMatch.fragmentComponents.presenter

import android.content.Context
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.GetMatchInLeague
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.MATCH_TYPE
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueResponse
import com.apwdevs.footballMatch.fragmentComponents.ui.FragmentMatchModel
import com.apwdevs.footballMatch.utility.CekKoneksi
import com.apwdevs.footballMatch.utility.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentMatchPresenter(
    private val ctx: Context,
    private val view: FragmentMatchModel,
    private val gson: Gson,
    private val apiRepository: ApiRepository,
    private val isTesting: Boolean = false,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getMatch(id: String, matchType: MATCH_TYPE) {
        view.onShowLoading()
        var message: String? = null
        var data: MatchTeamLeagueResponse? = null
        GlobalScope.launch(contextPool.main) {
            var connected = true
            if (!isTesting)
                connected = CekKoneksi.isConnected(ctx).await()

            if (connected) {
                data = gson.fromJson(
                    apiRepository.doRequest(GetMatchInLeague.getMatch(id, matchType)).await(),
                    MatchTeamLeagueResponse::class.java
                )
                if (data == null) {
                    message = "Failed to get some data from server"
                }
            } else {
                message = "Your phone is not connected into internet. Make sure to connect the internet!"
            }
            //delay(1500)
                if (message == null && data != null) {
                    view.onHideLoading()
                    if (data?.events != null)
                        view.onShowMatch(data?.events!!)
                    else
                        view.onNullMatch(id)
                } else {
                    view.onCancelShow(message!!)
                }
        }


    }
}