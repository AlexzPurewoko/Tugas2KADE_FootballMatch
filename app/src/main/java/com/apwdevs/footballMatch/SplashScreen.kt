package com.apwdevs.footballMatch

import android.graphics.Color
import android.graphics.Point
import android.os.*
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.TeamLeagueData
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.presenter.SplashPresenter
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.ui.SplashRecyclerAdapter
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.ui.SplashView
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.utility.ParameterClass
import com.apwdevs.footballMatch.utility.gone
import com.apwdevs.footballMatch.utility.visible
import com.google.gson.Gson
import org.jetbrains.anko.alert
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor


class SplashScreen : AppCompatActivity(), SplashView {


    private lateinit var relativeLayout: RelativeLayout
    private lateinit var linearLayout: LinearLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val gson = Gson()
        val api_repo = ApiRepository()
        val adapter = SplashPresenter(this, this, api_repo, gson)
        adapter.getLeagueList()

        setTransparentColorBar()
    }

    private fun setTransparentColorBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#91000000")
        }
        window.setBackgroundDrawableResource(R.drawable.splash_football_match)
    }

    override fun onLoadDataStarted() {
        relativeLayout = findViewById(R.id.splash_relative_loading)
        linearLayout = findViewById(R.id.splash_linear_final)
        recyclerView = findViewById(R.id.splash_recycler)
        setRecyclerSize()
        linearLayout.gone()
        relativeLayout.visible()
    }

    override fun onLOadDataFinished() {
        relativeLayout.gone()
        val fade = Fade(Fade.IN)
        fade.duration = 1200
        TransitionManager.beginDelayedTransition(linearLayout, fade)
        linearLayout.visible()
    }

    override fun showLeagueInSpinner(leagues: List<TeamLeagueData>) {
        val adapter = SplashRecyclerAdapter(this, leagues) {
            startActivity(
                intentFor<FootballMatchActivity>(
                    ParameterClass.ID_SELECTED_LEAGUE_KEY to it.idLeague,
                    ParameterClass.NAME_LEAGUE_KEY to it.strLeague
                ).clearTask()
            )
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setRecyclerSize() {
        var point = Point()
        windowManager.defaultDisplay.getSize(point)
        recyclerView.minimumHeight = (point.y / 2)
    }

    override fun onDataIsNotLoaded(err: String) {
        alert(err, getString(R.string.app_name), {
            this.negativeButton("Okay", {
                it.dismiss()
                this@SplashScreen.finish()
                val pid = Process.myPid()
                Handler(Looper.getMainLooper()).postDelayed({
                    Process.killProcess(pid)
                }, 1000)

            })
            this.iconResource = android.R.drawable.ic_dialog_alert
        }).show()
    }

    override fun onBackPressed() {
        val pid = Process.myPid()
        Handler(Looper.getMainLooper()).postDelayed({
            Process.killProcess(pid)
        }, 2000)
    }
}
