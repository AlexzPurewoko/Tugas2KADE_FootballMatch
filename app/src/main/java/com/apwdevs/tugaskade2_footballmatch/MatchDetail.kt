package com.apwdevs.tugaskade2_footballmatch

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
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
import com.apwdevs.tugaskade2_footballmatch.utility.ParameterClass
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.content_match_detail.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
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

    // other utility
    private val dialog: DialogShowHelper = DialogShowHelper(this)
    private lateinit var id_match: String
    private lateinit var id_league: String
    private var home_bitmap: Bitmap? = null
    private var away_bitmap: Bitmap? = null
    private val target_home_bitmap: Target = object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            this@MatchDetail.home_bitmap = bitmap
            home_logo.setImageBitmap(bitmap)
        }

    }
    private val target_away_bitmap: Target = object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            this@MatchDetail.away_bitmap = bitmap
            away_logo.setImageBitmap(bitmap)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setSupportActionBar(toolbar)
        prepareLayout()
        id_match = intent.getStringExtra(ParameterClass.ID_EVENT_MATCH_SELECTED)
        id_league = intent.getStringExtra(ParameterClass.ID_SELECTED_LEAGUE_KEY)
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
        dialog.buildLoadingLayout()
    }

    override fun showLoading() {
        dialog.showDialog()
    }

    override fun hideLoading() {
        dialog.stopDialog()
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
        Picasso.get().load(team_props[0].strTeamBadge).resize(100, 100).into(target_home_bitmap)
        Picasso.get().load(team_props[1].strTeamBadge).resize(100, 100).into(target_away_bitmap)
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

    override fun onDestroy() {
        away_bitmap?.recycle()
        home_bitmap?.recycle()
        super.onDestroy()
    }

    override fun onFailedLoadingData(what: String) {

    }

    override fun onBackPressed() {
        finish()
        startActivity(
            intentFor<FootballMatchActivity>(
                ParameterClass.ID_SELECTED_LEAGUE_KEY to id_league
            ).clearTask()
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }
}
