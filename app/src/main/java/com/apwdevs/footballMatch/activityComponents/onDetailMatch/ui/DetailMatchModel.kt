package com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui

import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.DataPropertyRecycler
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.DetailMatchDataClass
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.TeamPropData

interface DetailMatchModel {
    fun showLoading()
    fun hideLoading()
    fun onSuccessLoadingData(
        matchData: DetailMatchDataClass,
        teamProps: List<TeamPropData>,
        dataRecycler: MutableList<DataPropertyRecycler>
    )

    fun onFailedLoadingData(what: String)
}