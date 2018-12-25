package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.presenter

import android.content.Context
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.api_request.GetDataFromAPI
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.*
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui.DetailMatchModel
import com.apwdevs.tugaskade2_footballmatch.api_repo.ApiRepository
import com.apwdevs.tugaskade2_footballmatch.utility.CekKoneksi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(
    private val ctx: Context,
    private val apiRepository: ApiRepository,
    private val model: DetailMatchModel,
    private val gson: Gson
) {

    fun getDataFromServer(event_id: String) {
        model.showLoading()
        doAsync {
            var data: DetailMatchResponse? = null
            var msg: String? = null
            var data_team: MutableList<TeamPropData>? = null
            var recycler_data: MutableList<DataPropertyRecycler>? = null
            if (CekKoneksi.isConnected(ctx)) {
                data = gson.fromJson(
                    apiRepository.doRequest(GetDataFromAPI.getDetailMatchURL(event_id)),
                    DetailMatchResponse::class.java
                )
                if (data == null) {
                    msg = "Cannot load the data from Server"
                } else {
                    val idHome = data.events[0].idHomeTeam
                    val idAway = data.events[0].idAwayTeam
                    val data_team_home = gson.fromJson(
                        apiRepository.doRequest(GetDataFromAPI.getImageClubURL(idHome!!)),
                        TeamPropDataResponse::class.java
                    )
                    val data_team_away = gson.fromJson(
                        apiRepository.doRequest(GetDataFromAPI.getImageClubURL(idAway!!)),
                        TeamPropDataResponse::class.java
                    )
                    data_team = mutableListOf()
                    data_team.clear()
                    data_team.add(data_team_home.teams[0])
                    data_team.add(data_team_away.teams[0])
                    recycler_data = getDataRecycler(data.events[0])
                    if (recycler_data == null) {
                        msg = "RecyclerData is null"
                    }
                }

                uiThread {
                    model.hideLoading()
                    if (msg != null) {
                        model.onFailedLoadingData(msg)
                    } else {
                        model.onSuccessLoadingData(data.events[0], data_team?.toList()!!, recycler_data!!)
                    }
                }
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
                "GoealKeeper",
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
                "MieldField",
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
