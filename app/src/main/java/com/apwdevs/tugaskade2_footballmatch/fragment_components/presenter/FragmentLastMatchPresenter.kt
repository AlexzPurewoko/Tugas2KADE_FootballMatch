package com.apwdevs.tugaskade2_footballmatch.fragment_components.presenter

import android.content.Context
import com.apwdevs.tugaskade2_footballmatch.api_repo.ApiRepository
import com.apwdevs.tugaskade2_footballmatch.fragment_components.api_request.GetMatchInLeague
import com.apwdevs.tugaskade2_footballmatch.fragment_components.api_request.MATCH_TYPE
import com.apwdevs.tugaskade2_footballmatch.fragment_components.data_controller.MatchTeamLeagueResponse
import com.apwdevs.tugaskade2_footballmatch.fragment_components.ui.FragmentLastMatchModel
import com.apwdevs.tugaskade2_footballmatch.utility.CekKoneksi
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