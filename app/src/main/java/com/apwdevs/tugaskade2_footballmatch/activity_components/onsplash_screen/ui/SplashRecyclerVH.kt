package com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.apwdevs.tugaskade2_footballmatch.R
import com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.data_controller.TeamLeagueData
import org.jetbrains.anko.find

class SplashRecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView.find<TextView>(R.id.splash_recycler_textcontent)
    fun bindItem(item: TeamLeagueData, listener: (TeamLeagueData) -> Unit) {
        textView.text = item.strLeague
        itemView.setOnClickListener {
            listener(item)
        }
    }
}