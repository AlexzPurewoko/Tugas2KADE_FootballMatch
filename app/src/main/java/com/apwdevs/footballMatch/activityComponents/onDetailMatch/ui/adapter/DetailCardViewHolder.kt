package com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
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
    internal val propertyName: TextView = itemView.findViewById(R.id.adapter_cardview_detail_id_propnames)
    fun bindItem(item: DataPropertyRecycler) {

        if (item.isProperty) {
            val card = itemView as CardView
            card.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
            propertyName.text = item.name
            propertyName.textColor = Color.WHITE
        } else {
            home.text = item.homeValue ?: "-"
            away.text = item.awayValue ?: "-"
            propertyName.text = item.name
        }
    }
}