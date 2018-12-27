package com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController

import java.io.Serializable

data class LeagueResponse(
    val leagues: List<TeamLeagueData>
) : Serializable