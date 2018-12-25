package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui

import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.DataPropertyRecycler
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.DetailMatchDataClass
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.TeamPropData

interface DetailMatchModel {
    fun showLoading()
    fun hideLoading()
    fun onSuccessLoadingData(
        match_data: DetailMatchDataClass,
        team_props: List<TeamPropData>,
        data_recycler: MutableList<DataPropertyRecycler>
    )

    fun onFailedLoadingData(what: String)
}