package com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.adapter

import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.DataPropertyRecycler
import org.jetbrains.anko.textColor

class DetailCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val home: TextView = itemView.findViewById(R.id.adapter_cardview_detail_id_home)
    internal val away: TextView = itemView.findViewById(R.id.adapter_cardview_detail_id_away)
    internal val prop_name: TextView = itemView.findViewById(R.id.adapter_cardview_detail_id_propnames)
    fun bindItem(item: DataPropertyRecycler) {

        if (item.is_property) {
            val card = itemView as CardView
            card.setCardBackgroundColor(card.context.resources.getColor(R.color.colorPrimary))
            prop_name.text = item.name
            prop_name.textColor = Color.WHITE
        } else {
            home.text = item.home_value ?: "-"
            away.text = item.away_value ?: "-"
            prop_name.text = item.name
        }
    }
}