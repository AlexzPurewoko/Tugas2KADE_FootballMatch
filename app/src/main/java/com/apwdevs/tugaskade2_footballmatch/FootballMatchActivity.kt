package com.apwdevs.tugaskade2_footballmatch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.apwdevs.tugaskade2_footballmatch.fragment_components.FragmentMatch
import com.apwdevs.tugaskade2_footballmatch.fragment_components.api_request.MATCH_TYPE
import kotlinx.android.synthetic.main.activity_football_match.*

class FootballMatchActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_football_match)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        val h = intent.getStringExtra("LEAGUE_ID_SELECTED")
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, h)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_football_match, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager, private val id: String) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                return FragmentMatch.newInstance(id, MATCH_TYPE.LAST_MATCH)
            } else {
                return FragmentMatch.newInstance(id, MATCH_TYPE.NEXT_MATCH)
            }
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }
}
