package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller

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