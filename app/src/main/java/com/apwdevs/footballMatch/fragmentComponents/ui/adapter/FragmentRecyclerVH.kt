package com.apwdevs.footballMatch.fragmentComponents.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData
import com.apwdevs.footballMatch.utility.MyDate.getDate

class FragmentRecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val homeTeamName: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamname_left)
    private val awayTeamName: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamname_right)
    private val homeTeamScore: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamscore_left)
    private val awayTeamScore: TextView =
        itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamscore_right)
    private val date: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_date)
    fun bindItem(item: MatchTeamLeagueData, pos: Int, listener: (MatchTeamLeagueData, Int) -> Unit) {
        homeTeamName.text = item.strHomeTeam
        homeTeamScore.text = item.intHomeScore ?: "-"
        awayTeamName.text = item.strAwayTeam
        awayTeamScore.text = item.intAwayScore ?: "-"
        date.text = getDate(item.dateEvent, "yyyy-MM-dd")
        itemView.setOnClickListener {
            listener(item, pos)
        }
    }
}