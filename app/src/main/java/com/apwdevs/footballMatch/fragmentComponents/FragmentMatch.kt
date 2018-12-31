package com.apwdevs.footballMatch.fragmentComponents

import android.content.Intent
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
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.MatchType
import com.apwdevs.footballMatch.fragmentComponents.dataController.MatchTeamLeagueData
import com.apwdevs.footballMatch.fragmentComponents.presenter.FragmentMatchPresenter
import com.apwdevs.footballMatch.fragmentComponents.ui.FragmentMatchModel
import com.apwdevs.footballMatch.fragmentComponents.ui.FragmentMatchUI
import com.apwdevs.footballMatch.fragmentComponents.ui.adapter.FragmentRecyclerAdapter
import com.apwdevs.footballMatch.utility.ParameterClass
import com.apwdevs.footballMatch.utility.gone
import com.apwdevs.footballMatch.utility.visible
import com.google.gson.Gson
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class FragmentMatch : Fragment(), FragmentMatchModel {


    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: FragmentMatchPresenter
    private var items: MutableList<MatchTeamLeagueData> = mutableListOf()
    private lateinit var adapter: FragmentRecyclerAdapter
    private var idValue: String? = null
    private var matchType: MatchType? = null
    private var leagueName: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = FragmentMatchUI().createView(AnkoContext.create(context!!, container!!))
        // Gets the id
        swipeRefreshLayout = layout.find(R.id.adapter_fragment_lastmatch_swiperefresh)
        progressBar = layout.find(R.id.adapter_fragment_lastmatch_progressbar)
        recyclerView = layout.find(R.id.adapter_fragment_lastmatch_recyclerview)

        val gson = Gson()
        val apiRepository = ApiRepository()
        idValue = arguments?.getString(ARG_INT_ID)
        matchType = arguments?.getSerializable(ARG_MATCH_TYPE) as MatchType
        leagueName = arguments?.getString(ARG_NAME_LEAGUE)
        presenter = FragmentMatchPresenter(context!!, this, gson, apiRepository)
        adapter = FragmentRecyclerAdapter(items) { it, _ ->
            startActivity(
                intentFor<MatchDetail>(
                    ParameterClass.ID_EVENT_MATCH_SELECTED to it.idEvent,
                    ParameterClass.ID_SELECTED_LEAGUE_KEY to idValue,
                    ParameterClass.NAME_LEAGUE_KEY to leagueName
                ).clearTask()
            )
        }
        recyclerView.adapter = adapter
        if (idValue != null && matchType != null)
            presenter.getMatch(idValue!!, matchType!!)
        swipeRefreshLayout.onRefresh {
            if (idValue != null && matchType != null)
                presenter.getMatch(idValue!!, matchType!!)
        }
        return layout
    }

    override fun onShowLoading() {
        progressBar.visible()
    }

    override fun onHideLoading() {
        progressBar.gone()
    }

    override fun onCancelShow(what: String) {
        alert(what, getString(R.string.app_name)) {
            this.negativeButton("Okay") {
                it.dismiss()
            }
            this.iconResource = android.R.drawable.ic_dialog_alert
        }.show()
    }

    override fun onShowMatch(allMatch: List<MatchTeamLeagueData>) {
        swipeRefreshLayout.isRefreshing = false
        items.clear()
        items.addAll(allMatch)
        adapter.notifyDataSetChanged()
    }

    override fun onNullMatch(id: String) {
        swipeRefreshLayout.isRefreshing = false
        items.clear()
        adapter.notifyDataSetChanged()
        alert("Sorry :(\nNo match found in League id $id. Please select another league", getString(R.string.app_name)) {
            this.negativeButton("Okay") {
                it.dismiss()
                val i = context?.packageManager?.getLaunchIntentForPackage(context?.packageName!!)
                if (i != null) {
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                }

            }
            this.iconResource = android.R.drawable.ic_dialog_alert
        }.show()
    }

    companion object {
        const val ARG_INT_ID = "ID_SELECTED_LEAGUE"
        const val ARG_NAME_LEAGUE = "LEAGUE_NAME"
        const val ARG_MATCH_TYPE = "MatchType"

        fun newInstance(idLeague: String, matchType: MatchType, league_name: String): FragmentMatch {
            val fragment = FragmentMatch()
            val args = Bundle()
            args.putString(ARG_INT_ID, idLeague)
            args.putSerializable(ARG_MATCH_TYPE, matchType)
            args.putString(ARG_NAME_LEAGUE, league_name)
            fragment.arguments = args
            return fragment
        }
    }
}