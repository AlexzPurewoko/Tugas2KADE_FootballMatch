package com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController

import com.google.gson.annotations.SerializedName

data class TeamPropData(
    @SerializedName("idTeam")
    val idTeam: String?,
    @SerializedName("strTeam")
    val strTeam: String?,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String?
)

data class TeamPropDataResponse(
    val teams: List<TeamPropData>
)