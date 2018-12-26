package com.apwdevs.tugaskade2_footballmatch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.apwdevs.tugaskade2_footballmatch.fragment_components.FragmentMatch
import com.apwdevs.tugaskade2_footballmatch.fragment_components.api_request.MATCH_TYPE
import com.apwdevs.tugaskade2_footballmatch.utility.ParameterClass
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

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    override fun onBackPressed() {
        finish()
        startActivity(intentFor<SplashScreen>().clearTask().clearTop())
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, private val id: String) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                return FragmentMatch.newInstance(id, MATCH_TYPE.LAST_MATCH, league_name)
            } else {
                return FragmentMatch.newInstance(id, MATCH_TYPE.NEXT_MATCH, league_name)
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
