package com.apwdevs.footballMatch.fragmentComponents.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData
import java.text.SimpleDateFormat
import java.util.*

class FragmentRecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val home_team_name: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamname_left)
    private val away_team_name: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamname_right)
    private val home_team_score: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamscore_left)
    private val away_team_score: TextView =
        itemView.findViewById(R.id.adapter_cardview_footballmatch_id_teamscore_right)
    private val date: TextView = itemView.findViewById(R.id.adapter_cardview_footballmatch_id_date)
    fun bindItem(item: MatchTeamLeagueData, pos: Int, listener: (MatchTeamLeagueData, Int) -> Unit) {
        home_team_name.text = item.strHomeTeam
        home_team_score.text = item.intHomeScore ?: "-"
        away_team_name.text = item.strAwayTeam
        away_team_score.text = item.intAwayScore ?: "-"
        date.text = getDate(item.dateEvent, "yyyy-MM-dd")
        itemView.setOnClickListener {
            listener(item, pos)
        }
    }

    private fun getDate(strDate: String?, pattern: String): CharSequence? {
        if (strDate == null) return null
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat(pattern).parse(strDate)
        val day = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.THURSDAY -> "Thu"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.SATURDAY -> "Sat"
            Calendar.TUESDAY -> "Tue"
            Calendar.FRIDAY -> "Fri"
            else -> "nan"
        }
        val month = when (calendar.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "Jan"
            Calendar.FEBRUARY -> "Feb"
            Calendar.MARCH -> "March"
            Calendar.APRIL -> "April"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "June"
            Calendar.JULY -> "July"
            Calendar.AUGUST -> "August"
            Calendar.SEPTEMBER -> "Sep"
            Calendar.OCTOBER -> "Oct"
            Calendar.NOVEMBER -> "Nov"
            Calendar.DECEMBER -> "Dec"
            else -> "nan"
        }
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)

        return "$day, $date $month $year"
    }
}