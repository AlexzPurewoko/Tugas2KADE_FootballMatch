package com.apwdevs.footballMatch.activityComponents.onSplashScreen.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.TeamLeagueData

class SplashRecyclerAdapter(
    private val ctx: Context,
    private val list_league: List<TeamLeagueData>,
    private val listener: (TeamLeagueData) -> Unit
) : RecyclerView.Adapter<SplashRecyclerVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SplashRecyclerVH {
        val created =
            SplashRecyclerVH(LayoutInflater.from(ctx).inflate(R.layout.adapter_splash_recyclerview, p0, false))

        return created
    }

    override fun getItemCount(): Int {
        return list_league.size
    }

    override fun onBindViewHolder(p0: SplashRecyclerVH, p1: Int) {
        p0.bindItem(list_league[p1], listener)
    }
}