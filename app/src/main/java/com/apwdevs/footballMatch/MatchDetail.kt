package com.apwdevs.footballMatch

import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.DataPropertyRecycler
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.DetailMatchDataClass
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.TeamPropData
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.presenter.DetailMatchPresenter
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.DetailMatchModel
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.adapter.DetailCardViewHolder
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.adapter.DetailMatchRecyclerAdapter
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.database.FavoriteData
import com.apwdevs.footballMatch.database.database
import com.apwdevs.footballMatch.utility.DialogShowHelper
import com.apwdevs.footballMatch.utility.ParameterClass
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.content_match_detail.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.onRefresh
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
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private val dialog: DialogShowHelper = DialogShowHelper(this)
    private lateinit var id_match: String
    private lateinit var id_league: String
    private lateinit var name_league: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setSupportActionBar(toolbar)
        prepareLayout()
        id_match = intent.getStringExtra(ParameterClass.ID_EVENT_MATCH_SELECTED)
        id_league = intent.getStringExtra(ParameterClass.ID_SELECTED_LEAGUE_KEY)
        name_league = intent.getStringExtra(ParameterClass.NAME_LEAGUE_KEY)
        match_detail_swiperefresh.setColorSchemeColors(
            getColors(R.color.colorAccent),
            getColors(android.R.color.holo_green_light),
            getColors(android.R.color.holo_orange_light),
            getColors(android.R.color.holo_red_light)
        )
        val apiRepository = ApiRepository()
        val gson = Gson()
        val presenter = DetailMatchPresenter(this, apiRepository, this, gson)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.getDataFromServer(id_match)
        match_detail_swiperefresh.onRefresh {
            presenter.getDataFromServer(id_match)
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteState()
    }

    private fun getColors(id: Int): Int {
        return ContextCompat.getColor(this, id)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_match, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                this.isFavorite = !this.isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteData.TABLE_FAVORITE)
                .whereArgs(
                    "(${FavoriteData.ID_LEAGUE} = {id} AND ${FavoriteData.ID_EVENT} = {event})",
                    "id" to id_league,
                    "event" to id_match
                )
            val favorites = result.parseList(classParser<FavoriteData>())

            if (!favorites.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteData.TABLE_FAVORITE,
                    FavoriteData.ID_LEAGUE to id_league,
                    FavoriteData.ID_EVENT to id_match,
                    FavoriteData.LEAGUE_NAME to name_league,
                    FavoriteData.DATE_EVENT to match_details.dateEvent,
                    FavoriteData.HOME_TEAM to match_details.strHomeTeam,
                    FavoriteData.HOME_SCORE to match_details.intHomeScore?.toInt(),
                    FavoriteData.AWAY_TEAM to match_details.strAwayTeam,
                    FavoriteData.AWAY_SCORE to match_details.intAwayScore?.toInt()
                )
            }
            match_detail_swiperefresh.snackbar("Added into Database, :)").show()
        } catch (e: SQLiteConstraintException) {
            match_detail_swiperefresh.snackbar(e.localizedMessage).show()

        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteData.TABLE_FAVORITE,
                    "(${FavoriteData.ID_LEAGUE} = {id} AND ${FavoriteData.ID_EVENT} = {event})",
                    "id" to id_league,
                    "event" to id_match
                )
            }
            match_detail_swiperefresh.snackbar("Removed from Database, :(").show()
        } catch (e: SQLiteConstraintException) {
            match_detail_swiperefresh.snackbar(e.localizedMessage).show()
        }
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
        match_detail_swiperefresh.isRefreshing = false
        this.match_details = match_data
        this.team_props = team_props
        this.data_props = data_recycler
        adapter = DetailMatchRecyclerAdapter(data_props)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        name_home.text = team_props[0].strTeam
        name_away.text = team_props[1].strTeam
        score_home.text = match_details.intHomeScore ?: "-"
        score_away.text = match_details.intAwayScore ?: "-"
        Picasso.get().load(team_props[0].strTeamBadge).resize(100, 100).into(home_logo)
        Picasso.get().load(team_props[1].strTeamBadge).resize(100, 100).into(away_logo)
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

    override fun onBackPressed() {
        finish()
        startActivity(
            intentFor<FootballMatchActivity>(
                ParameterClass.ID_SELECTED_LEAGUE_KEY to id_league,
                ParameterClass.NAME_LEAGUE_KEY to name_league
            ).clearTask()
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }
}
