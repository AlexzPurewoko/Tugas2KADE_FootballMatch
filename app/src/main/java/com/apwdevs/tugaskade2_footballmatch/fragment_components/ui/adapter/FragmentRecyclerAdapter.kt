package com.apwdevs.tugaskade2_footballmatch.fragment_components.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apwdevs.tugaskade2_footballmatch.R
import com.apwdevs.tugaskade2_footballmatch.fragment_components.data_controller.MatchTeamLeagueData

class FragmentRecyclerAdapter(
    private val items: List<MatchTeamLeagueData>,
    private val listener: (MatchTeamLeagueData) -> Unit
) : RecyclerView.Adapter<FragmentRecyclerVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FragmentRecyclerVH {
        val layout = LayoutInflater.from(p0.context).inflate(R.layout.adapter_cardview_footballmatch, p0, false)
        return FragmentRecyclerVH(layout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: FragmentRecyclerVH, p1: Int) {
        p0.bindItem(items[p1], listener)
    }
}