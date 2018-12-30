package com.apwdevs.footballMatch.activityComponents.onDetailMatch.presenter

import android.content.Context
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.apiRequest.GetDataFromAPI
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.*
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.DetailMatchModel
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.utility.CekKoneksi
import com.apwdevs.footballMatch.utility.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(
    private val ctx: Context,
    private val apiRepository: ApiRepository,
    private val model: DetailMatchModel,
    private val gson: Gson,
    private val isTesting: Boolean = false,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getDataFromServer(event_id: String) {
        model.showLoading()
        GlobalScope.launch(contextPool.main) {
            var data: DetailMatchResponse?
            var msg: String? = null
            var dataTeam: MutableList<TeamPropData>? = null
            var recyclerData: MutableList<DataPropertyRecycler>? = null
            val connect = if (!isTesting) {
                CekKoneksi.isConnected(ctx).await()
            } else {
                true
            }
            if (connect) {
                data = gson.fromJson(
                    apiRepository.doRequest(GetDataFromAPI.getDetailMatchURL(event_id)).await(),
                    DetailMatchResponse::class.java
                )
                if (data == null) {
                    msg = "Cannot load the data from Server"
                } else {
                    val idHome = data.events[0].idHomeTeam
                    val idAway = data.events[0].idAwayTeam
                    val dataTeamHome = gson.fromJson(
                        apiRepository.doRequest(GetDataFromAPI.getImageClubURL(idHome!!)).await(),
                        TeamPropDataResponse::class.java
                    )
                    val dataTeamAway = gson.fromJson(
                        apiRepository.doRequest(GetDataFromAPI.getImageClubURL(idAway!!)).await(),
                        TeamPropDataResponse::class.java
                    )
                    dataTeam = mutableListOf()
                    dataTeam.clear()
                    dataTeam.add(dataTeamHome.teams[0])
                    dataTeam.add(dataTeamAway.teams[0])
                    recyclerData = getDataRecycler(data.events[0])
                    if (recyclerData == null) {
                        msg = "RecyclerData is null"
                    }
                }
                if (isTesting)
                    Thread.sleep(1500)
                //uiThread {
                    model.hideLoading()
                    if (msg != null) {
                        model.onFailedLoadingData(msg)
                    } else {
                        model.onSuccessLoadingData(data.events[0], dataTeam?.toList()!!, recyclerData!!)
                    }
                //}
            }
        }
    }

    private fun getDataRecycler(data: DetailMatchDataClass): MutableList<DataPropertyRecycler> {
        val ret: MutableList<DataPropertyRecycler> = mutableListOf()
        ret.clear()
        ret.add(DataPropertyRecycler(true, "Statistics", null, null))
        ret.add(DataPropertyRecycler(false, "Goals", data.intHomeScore, data.intAwayScore))
        ret.add(
            DataPropertyRecycler(
                false,
                "Goal Details",
                buildPart(data.strHomeGoalDetails, ';', '\n'),
                buildPart(data.strAwayGoalDetails, ';', '\n')
            )
        )
        ret.add(DataPropertyRecycler(false, "Shots", data.intHomeShots, data.intAwayShots))
        ret.add(
            DataPropertyRecycler(
                false,
                "Yellow Cards",
                buildPart(data.strHomeYellowCards, ';', '\n'),
                buildPart(data.strAwayYellowCards, ';', '\n')
            )
        )
        ret.add(
            DataPropertyRecycler(
                false,
                "Red Cards",
                buildPart(data.strHomeRedCards, ';', '\n'),
                buildPart(data.strAwayRedCards, ';', '\n')
            )
        )

        ret.add(DataPropertyRecycler(true, "Lineups", null, null))
        ret.add(
            DataPropertyRecycler(
                false,
                "GoalKeeper",
                buildPart(data.strHomeLineupGoalkeeper, ';', '\n'),
                data.strAwayLineupGoalkeeper
            )
        )
        ret.add(
            DataPropertyRecycler(
                false,
                "Defense",
                buildPart(data.strHomeLineupDefense, ';', '\n'),
                buildPart(data.strAwayLineupGoalkeeper, ';', '\n')
            )
        )
        ret.add(
            DataPropertyRecycler(
                false,
                "MidField",
                buildPart(data.strHomeLineupMidfield, ';', '\n'),
                data.strAwayLineupMidfield
            )
        )
        ret.add(
            DataPropertyRecycler(
                false,
                "Forward",
                buildPart(data.strHomeLineupForward, ';', '\n'),
                buildPart(data.strAwayLineupForward, ';', '\n')
            )
        )
        ret.add(
            DataPropertyRecycler(
                false,
                "Subtitues",
                buildPart(data.strHomeLineupSubstitutes, ';', '\n'),
                buildPart(data.strAwayLineupSubstitutes, ';', '\n')
            )
        )

        return ret

    }

    private fun buildPart(str: String?, s: Char, c: Char): String? {
        if (str == null) return null
        val buffer = StringBuffer()
        for (i in str) {
            if (i == s)
                buffer.append(c)
            else
                buffer.append(i)
        }
        return buffer.toString()
    }

}
