package com.apwdevs.footballMatch.fragmentComponents.ui

import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData

interface FragmentMatchModel {
    fun onShowLoading()
    fun onHideLoading()
    fun onCancelShow(what: String)
    fun onShowMatch(allMatch: List<MatchTeamLeagueData>)
    fun onNullMatch(id: String)
}