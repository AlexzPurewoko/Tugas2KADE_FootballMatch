package com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TeamLeagueData(
    @SerializedName("idLeague")
    val idLeague: String?,
    @SerializedName("strLeague")
    val strLeague: String?,
    @SerializedName("strSport")
    val strSport: String?
) : Serializable