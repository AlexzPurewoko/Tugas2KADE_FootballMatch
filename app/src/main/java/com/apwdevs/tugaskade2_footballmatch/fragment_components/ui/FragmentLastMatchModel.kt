package com.apwdevs.tugaskade2_footballmatch.fragment_components.ui

import com.apwdevs.tugaskade2_footballmatch.fragment_components.data_controller.MatchTeamLeagueData

interface FragmentLastMatchModel {
    fun onShowLoading()
    fun onHideLoading()
    fun onCancelShow(what: String)
    fun onShowMatch(all_match: List<MatchTeamLeagueData>)
    fun onNullMatch(id: String)
}