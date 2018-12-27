package com.apwdevs.footballMatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.apwdevs.footballMatch.fragmentComponents.FragmentFavorites
import com.apwdevs.footballMatch.fragmentComponents.FragmentMatch
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.MATCH_TYPE
import com.apwdevs.footballMatch.utility.ParameterClass
import kotlinx.android.synthetic.main.activity_football_match.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor

class FootballMatchActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var league_name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_football_match)

        setSupportActionBar(toolbar)
        val h = intent.getStringExtra(ParameterClass.ID_SELECTED_LEAGUE_KEY)
        league_name = intent.getStringExtra(ParameterClass.NAME_LEAGUE_KEY)

        supportActionBar?.title = league_name
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, h)

        container.adapter = mSectionsPagerAdapter

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.last_match -> {
                    container.setCurrentItem(0, true)
                    true
                }
                R.id.next_match -> {
                    container.setCurrentItem(1, true)
                    true
                }
                else -> {
                    container.setCurrentItem(2, true)
                    true
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
        startActivity(intentFor<SplashScreen>().clearTask().clearTop())
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, private val id: String) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FragmentMatch.newInstance(id, MATCH_TYPE.LAST_MATCH, league_name)
                1 -> FragmentMatch.newInstance(id, MATCH_TYPE.NEXT_MATCH, league_name)
                else -> FragmentFavorites.newInstance(id)
            }
        }

        override fun getCount(): Int {
            return 3
        }
    }
}
