package com.apwdevs.tugaskade2_footballmatch

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.DataPropertyRecycler
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.DetailMatchDataClass
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.TeamPropData
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.presenter.DetailMatchPresenter
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui.DetailMatchModel
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui.adapter.DetailCardViewHolder
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui.adapter.DetailMatchRecyclerAdapter
import com.apwdevs.tugaskade2_footballmatch.api_repo.ApiRepository
import com.apwdevs.tugaskade2_footballmatch.utility.DialogShowHelper
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.content_match_detail.*
import java.text.SimpleDateFormat
import java.util.*

class MatchDetail : AppCompatActivity(), DetailMatchModel {


    // data section
    private lateinit var match_details: DetailMatchDataClass
    private lateinit var team_props: List<TeamPropData>
    private lateinit var data_props: MutableList<DataPropertyRecycler>
    // upper layout section
    private lateinit var home_logo: ImageView
    private lateinit var away_logo: ImageView
    private lateinit var score_home: TextView
    private lateinit var score_away: TextView
    private lateinit var name_home: TextView
    private lateinit var name_away: TextView
    private lateinit var date_text: TextView

    // recyclersection
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<DetailCardViewHolder>
    private lateinit var progressBar: ProgressBar

    // other utility
    private val dialog: DialogShowHelper = DialogShowHelper(this)
    private lateinit var id_match: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setSupportActionBar(toolbar)
        prepareLayout()
        id_match = intent.getStringExtra("MATCH_SELECTED")
        val apiRepository = ApiRepository()
        val gson = Gson()
        val presenter = DetailMatchPresenter(this, apiRepository, this, gson)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.getDataFromServer(id_match)
    }

    private fun prepareLayout() {
        home_logo = content_match_detail_id_home_logoteam
        away_logo = content_match_detail_id_away_logoteam
        score_home = content_match_detail_id_teamscore_left
        score_away = content_match_detail_id_teamscore_right
        name_home = content_match_detail_id_teamname_left
        name_away = content_match_detail_id_teamname_right
        date_text = content_match_detail_id_date

        recyclerView = content_match_detail_id_recyclerlist
        progressBar = content_match_detail_id_progressbar
        dialog.buildLoadingLayout()
    }

    override fun showLoading() {
        dialog.showDialog()
        progressBar.visible()
    }

    override fun hideLoading() {
        dialog.stopDialog()
        progressBar.gone()
    }

    override fun onSuccessLoadingData(
        match_data: DetailMatchDataClass,
        team_props: List<TeamPropData>,
        data_recycler: MutableList<DataPropertyRecycler>
    ) {
        this.match_details = match_data
        this.team_props = team_props
        this.data_props = data_recycler
        adapter = DetailMatchRecyclerAdapter(data_props)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        name_home.text = team_props[0].strTeam
        name_away.text = team_props[1].strTeam
        score_home.text = match_details.intHomeScore
        score_away.text = match_details.intAwayScore
        Picasso.get().load(team_props[0].strTeamBadge).into(home_logo)
        Picasso.get().load(team_props[1].strTeamBadge).into(away_logo)
        date_text.text = getDate(match_details.dateEvent, "yyyy-MM-dd")
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

    override fun onFailedLoadingData(what: String) {

    }
}
