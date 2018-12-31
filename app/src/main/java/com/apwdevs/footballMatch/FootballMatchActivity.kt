package com.apwdevs.footballMatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.apwdevs.footballMatch.fragmentComponents.FragmentFavorites
import com.apwdevs.footballMatch.fragmentComponents.FragmentMatch
import com.apwdevs.footballMatch.fragmentComponents.apiRequest.MatchType
import com.apwdevs.footballMatch.utility.ParameterClass
import kotlinx.android.synthetic.main.activity_football_match.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor

class FootballMatchActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var leagueName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_football_match)

        setSupportActionBar(toolbar)
        val h = intent.getStringExtra(ParameterClass.ID_SELECTED_LEAGUE_KEY)
        leagueName = intent.getStringExtra(ParameterClass.NAME_LEAGUE_KEY)

        supportActionBar?.title = leagueName
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
        container.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                }

                override fun onPageSelected(p0: Int) {
                    when (p0) {
                        0 -> bottom_navigation.selectedItemId = R.id.last_match
                        1 -> bottom_navigation.selectedItemId = R.id.next_match
                        2 -> bottom_navigation.selectedItemId = R.id.favorite_match
                    }
                }

                override fun onPageScrollStateChanged(p0: Int) {

                }

            }
        )
    }

    override fun onBackPressed() {
        finish()
        startActivity(intentFor<SplashScreen>().clearTask().clearTop())
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, private val id: String) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FragmentMatch.newInstance(id, MatchType.LAST_MATCH, leagueName)
                1 -> FragmentMatch.newInstance(id, MatchType.NEXT_MATCH, leagueName)
                else -> FragmentFavorites.newInstance(id)
            }
        }

        override fun getCount(): Int {
            return 3
        }
    }
}
