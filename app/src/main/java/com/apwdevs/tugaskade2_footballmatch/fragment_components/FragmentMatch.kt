package com.apwdevs.tugaskade2_footballmatch.fragment_components

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.apwdevs.tugaskade2_footballmatch.MatchDetail
import com.apwdevs.tugaskade2_footballmatch.R
import com.apwdevs.tugaskade2_footballmatch.api_repo.ApiRepository
import com.apwdevs.tugaskade2_footballmatch.fragment_components.api_request.MATCH_TYPE
import com.apwdevs.tugaskade2_footballmatch.fragment_components.data_controller.MatchTeamLeagueData
import com.apwdevs.tugaskade2_footballmatch.fragment_components.presenter.FragmentLastMatchPresenter
import com.apwdevs.tugaskade2_footballmatch.fragment_components.ui.FragmentLastMatchModel
import com.apwdevs.tugaskade2_footballmatch.fragment_components.ui.FragmentLastMatchUI
import com.apwdevs.tugaskade2_footballmatch.fragment_components.ui.adapter.FragmentRecyclerAdapter
import com.apwdevs.tugaskade2_footballmatch.gone
import com.apwdevs.tugaskade2_footballmatch.visible
import com.google.gson.Gson
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class FragmentMatch : Fragment(), FragmentLastMatchModel {


    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: FragmentLastMatchPresenter
    private var items: MutableList<MatchTeamLeagueData> = mutableListOf()
    private lateinit var adapter: FragmentRecyclerAdapter
    private var idValue: String? = null
    private var match_type: MATCH_TYPE? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = FragmentLastMatchUI().createView(AnkoContext.create(context!!, container!!))

        // Gets the id
        swipeRefreshLayout = layout.find(R.id.adapter_fragment_lastmatch_swiperefresh)
        progressBar = layout.find(R.id.adapter_fragment_lastmatch_progressbar)
        recyclerView = layout.find(R.id.adapter_fragment_lastmatch_recyclerview)

        val gson = Gson()
        val apiRepository = ApiRepository()
        idValue = arguments?.getString(ARG_INT_ID)
        match_type = arguments?.getSerializable(ARG_MATCH_TYPE) as MATCH_TYPE
        presenter = FragmentLastMatchPresenter(context!!, this, gson, apiRepository)
        adapter = FragmentRecyclerAdapter(items) {
            startActivity(intentFor<MatchDetail>("MATCH_SELECTED" to it.idEvent).clearTask().clearTop())
        }
        recyclerView.adapter = adapter
        if (idValue != null && match_type != null)
            presenter.getMatch(idValue!!, match_type!!)
        swipeRefreshLayout.onRefresh {
            if (idValue != null && match_type != null)
                presenter.getMatch(idValue!!, match_type!!)
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
        alert(what, getString(R.string.app_name), {
            this.negativeButton("Okay", {
                it.dismiss()
            })
            this.iconResource = android.R.drawable.ic_dialog_alert
        }).show()
    }

    override fun onShowMatch(all_match: List<MatchTeamLeagueData>) {
        swipeRefreshLayout.isRefreshing = false
        items.clear()
        items.addAll(all_match)
        adapter.notifyDataSetChanged()
    }

    companion object {
        val ARG_INT_ID = "ID_SELECTED_LEAGUE"
        val ARG_MATCH_TYPE = "MATCH_TYPE"

        fun newInstance(idLeague: String, match_type: MATCH_TYPE): FragmentMatch {
            val fragment = FragmentMatch()
            val args = Bundle()
            args.putString(ARG_INT_ID, idLeague)
            args.putSerializable(ARG_MATCH_TYPE, match_type)
            fragment.arguments = args
            return fragment
        }
    }
}