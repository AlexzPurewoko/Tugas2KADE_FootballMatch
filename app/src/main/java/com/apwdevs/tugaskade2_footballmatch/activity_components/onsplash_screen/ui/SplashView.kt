package com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.ui

import com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.data_controller.TeamLeagueData

interface SplashView {
    fun onLoadDataStarted()
    fun onLOadDataFinished()
    fun showLeagueInSpinner(leagues: List<TeamLeagueData>)
    fun onDataIsNotLoaded(err: String)
}