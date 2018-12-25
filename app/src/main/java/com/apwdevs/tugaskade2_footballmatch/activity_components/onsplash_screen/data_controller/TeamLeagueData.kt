package com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.data_controller

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