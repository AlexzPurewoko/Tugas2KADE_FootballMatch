package com.apwdevs.footballMatch.fragmentComponents.dataController

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MatchTeamLeagueData(
    @SerializedName("idEvent")
    val idEvent: String?,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String?,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String?,
    @SerializedName("intHomeScore")
    val intHomeScore: String?,
    @SerializedName("intAwayScore")
    val intAwayScore: String?,
    @SerializedName("strDate")
    val strDate: String?,
    @SerializedName("dateEvent")
    val dateEvent: String?
) : Serializable