package com.apwdevs.footballMatch.fragmentComponents.ui

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.R.color.colorAccent
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FragmentMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )
                id = R.id.adapter_fragment_lastmatch_swiperefresh
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    recyclerView {
                        id = R.id.adapter_fragment_lastmatch_recyclerview
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBar {
                        id = R.id.adapter_fragment_lastmatch_progressbar
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }
}