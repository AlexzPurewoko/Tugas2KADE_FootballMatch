package com.apwdevs.footballMatch.activityComponents.onSplashScreen.ui

import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.TeamLeagueData

interface SplashView {
    fun onLoadDataStarted()
    fun onLOadDataFinished()
    fun showLeagueInSpinner(leagues: List<TeamLeagueData>)
    fun onDataIsNotLoaded(err: String)
}