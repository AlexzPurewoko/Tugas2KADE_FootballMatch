package com.apwdevs.footballMatch.fragmentComponents.ui

import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData

interface FragmentLastMatchModel {
    fun onShowLoading()
    fun onHideLoading()
    fun onCancelShow(what: String)
    fun onShowMatch(all_match: List<MatchTeamLeagueData>)
    fun onNullMatch(id: String)
}