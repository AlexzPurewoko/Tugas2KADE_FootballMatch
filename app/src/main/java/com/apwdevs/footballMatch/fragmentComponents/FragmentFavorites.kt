package com.apwdevs.footballMatch.fragmentComponents

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.apwdevs.footballMatch.MatchDetail
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.database.FavoriteData
import com.apwdevs.footballMatch.database.database
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData
import com.apwdevs.footballMatch.fragmentComponents.ui.FragmentLastMatchUI
import com.apwdevs.footballMatch.fragmentComponents.ui.adapter.FragmentRecyclerAdapter
import com.apwdevs.footballMatch.utility.ParameterClass
import com.apwdevs.footballMatch.utility.gone
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class FragmentFavorites : Fragment() {
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var favorites: MutableList<MatchTeamLeagueData> = mutableListOf()
    private var origFavorites: MutableList<FavoriteData> = mutableListOf()
    private lateinit var adapter: FragmentRecyclerAdapter
    private lateinit var idLeague: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FragmentRecyclerAdapter(favorites) { _, y ->
            startActivity(
                intentFor<MatchDetail>(
                    ParameterClass.ID_EVENT_MATCH_SELECTED to origFavorites[y].idEvent,
                    ParameterClass.ID_SELECTED_LEAGUE_KEY to origFavorites[y].idLeague,
                    ParameterClass.NAME_LEAGUE_KEY to origFavorites[y].leagueName
                ).clearTask()
            )
        }

        idLeague = arguments?.getString(ParameterClass.KEY_FRAGMENT_FAVORITES_LEAGUE_ID) ?: ""
        listTeam.adapter = adapter

        swipeRefreshLayout.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(FavoriteData.TABLE_FAVORITE)
            val fav = result.parseList(classParser<FavoriteData>())
            favorites.clear()
            origFavorites.clear()
            if (!fav.isEmpty()) {
                for (x in fav) {
                    if (x.idLeague.equals(idLeague)) {
                        val c = MatchTeamLeagueData(
                            x.idEvent,
                            x.homeTeam,
                            x.awayTeam,
                            if (x.homeScore == null) "-" else x.homeScore.toString(),
                            if (x.awayScore == null) "-" else x.awayScore.toString(),
                            null,
                            x.dateEvent
                        )
                        favorites.add(c)
                        origFavorites.add(x)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = FragmentLastMatchUI().createView(AnkoContext.create(requireContext(), container!!))
        listTeam = layout.find(R.id.adapter_fragment_lastmatch_recyclerview)
        swipeRefreshLayout = layout.find(R.id.adapter_fragment_lastmatch_swiperefresh)
        layout.find<ProgressBar>(R.id.adapter_fragment_lastmatch_progressbar).gone()
        return layout
    }

    companion object {
        fun newInstance(leagueId: String): FragmentFavorites {
            val fragment = FragmentFavorites()
            val args = Bundle()
            args.putString(ParameterClass.KEY_FRAGMENT_FAVORITES_LEAGUE_ID, leagueId)
            fragment.arguments = args
            return fragment
        }
    }
}