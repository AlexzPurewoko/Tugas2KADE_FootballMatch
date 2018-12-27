package com.apwdevs.footballMatch.fragmentComponents.presenter

import android.content.Context
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.GetMatchInLeague
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.MATCH_TYPE
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueResponse
import com.apwdevs.footballMatch.fragmentComponents.ui.FragmentLastMatchModel
import com.apwdevs.footballMatch.utility.CekKoneksi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FragmentLastMatchPresenter(
    private val ctx: Context,
    private val view: FragmentLastMatchModel,
    private val gson: Gson,
    private val apiRepository: ApiRepository
) {
    fun getMatch(id: String, match_type: MATCH_TYPE) {
        view.onShowLoading()
        var message: String? = null
        var data: MatchTeamLeagueResponse? = null
        doAsync {
            if (CekKoneksi.isConnected(ctx)) {
                data = gson.fromJson(
                    apiRepository.doRequest(GetMatchInLeague.getMatch(id, match_type)),
                    MatchTeamLeagueResponse::class.java
                )
                if (data == null) {
                    message = "Failed to get some data from server"
                }
            } else {
                message = "Your phone is not connected into internet. Make sure to connect the internet!"
            }
            uiThread {
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
}