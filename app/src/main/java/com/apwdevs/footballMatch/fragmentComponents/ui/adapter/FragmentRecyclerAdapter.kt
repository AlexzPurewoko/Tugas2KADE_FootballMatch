package com.apwdevs.footballMatch.fragmentComponents.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData

class FragmentRecyclerAdapter(
    private val items: List<MatchTeamLeagueData>,
    private val listener: (MatchTeamLeagueData, Int) -> Unit
) : RecyclerView.Adapter<FragmentRecyclerVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FragmentRecyclerVH {
        val layout = LayoutInflater.from(p0.context).inflate(R.layout.adapter_cardview_footballmatch, p0, false)
        return FragmentRecyclerVH(layout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: FragmentRecyclerVH, p1: Int) {
        p0.bindItem(items[p1], p1, listener)
    }
}