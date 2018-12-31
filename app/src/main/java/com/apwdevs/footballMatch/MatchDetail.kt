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
import com.apwdevs.footballMatch.utility.MyDate.getDate
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

class MatchDetail : AppCompatActivity(), DetailMatchModel {


    // data section
    private lateinit var matchDetails: DetailMatchDataClass
    private lateinit var teamProps: List<TeamPropData>
    private lateinit var dataProps: MutableList<DataPropertyRecycler>
    // upper layout section
    private lateinit var homeLogo: ImageView
    private lateinit var awayLogo: ImageView
    private lateinit var scoreHome: TextView
    private lateinit var scoreAway: TextView
    private lateinit var nameHome: TextView
    private lateinit var nameAway: TextView
    private lateinit var dateText: TextView

    // recyclersection
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<DetailCardViewHolder>

    // other utility
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private val dialog: DialogShowHelper = DialogShowHelper(this)
    private lateinit var idMatch: String
    private lateinit var idLeague: String
    private lateinit var nameLeague: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setSupportActionBar(toolbar)
        prepareLayout()
        idMatch = intent.getStringExtra(ParameterClass.ID_EVENT_MATCH_SELECTED)
        idLeague = intent.getStringExtra(ParameterClass.ID_SELECTED_LEAGUE_KEY)
        nameLeague = intent.getStringExtra(ParameterClass.NAME_LEAGUE_KEY)
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
        presenter.getDataFromServer(idMatch)
        match_detail_swiperefresh.onRefresh {
            presenter.getDataFromServer(idMatch)
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
                    "id" to idLeague,
                    "event" to idMatch
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
                    FavoriteData.ID_LEAGUE to idLeague,
                    FavoriteData.ID_EVENT to idMatch,
                    FavoriteData.LEAGUE_NAME to nameLeague,
                    FavoriteData.DATE_EVENT to matchDetails.dateEvent,
                    FavoriteData.HOME_TEAM to matchDetails.strHomeTeam,
                    FavoriteData.HOME_SCORE to matchDetails.intHomeScore?.toInt(),
                    FavoriteData.AWAY_TEAM to matchDetails.strAwayTeam,
                    FavoriteData.AWAY_SCORE to matchDetails.intAwayScore?.toInt()
                )
            }
            match_detail_swiperefresh.snackbar(ParameterClass.STRING_ADD_INTO_DATABASE).show()
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
                    "id" to idLeague,
                    "event" to idMatch
                )
            }
            match_detail_swiperefresh.snackbar(ParameterClass.STRING_REMOVE_FROM_DATABASE).show()
        } catch (e: SQLiteConstraintException) {
            match_detail_swiperefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun prepareLayout() {
        homeLogo = content_match_detail_id_home_logoteam
        awayLogo = content_match_detail_id_away_logoteam
        scoreHome = content_match_detail_id_teamscore_left
        scoreAway = content_match_detail_id_teamscore_right
        nameHome = content_match_detail_id_teamname_left
        nameAway = content_match_detail_id_teamname_right
        dateText = content_match_detail_id_date

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
        matchData: DetailMatchDataClass,
        teamProps: List<TeamPropData>,
        dataRecycler: MutableList<DataPropertyRecycler>
    ) {
        match_detail_swiperefresh.isRefreshing = false
        this.matchDetails = matchData
        this.teamProps = teamProps
        this.dataProps = dataRecycler
        adapter = DetailMatchRecyclerAdapter(dataProps)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        nameHome.text = teamProps[0].strTeam
        nameAway.text = teamProps[1].strTeam
        scoreHome.text = matchDetails.intHomeScore ?: "-"
        scoreAway.text = matchDetails.intAwayScore ?: "-"
        Picasso.get().load(teamProps[0].strTeamBadge).resize(100, 100).into(homeLogo)
        Picasso.get().load(teamProps[1].strTeamBadge).resize(100, 100).into(awayLogo)
        dateText.text = getDate(matchDetails.dateEvent, "yyyy-MM-dd")
    }

    override fun onFailedLoadingData(what: String) {

    }

    override fun onBackPressed() {
        finish()
        startActivity(
            intentFor<FootballMatchActivity>(
                ParameterClass.ID_SELECTED_LEAGUE_KEY to idLeague,
                ParameterClass.NAME_LEAGUE_KEY to nameLeague
            ).clearTask()
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }
}
