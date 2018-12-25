package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui.adapter

import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.apwdevs.tugaskade2_footballmatch.R
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.DataPropertyRecycler
import org.jetbrains.anko.textColor

class DetailCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val home: TextView = itemView.findViewById(R.id.adapter_cardview_detail_id_home)
    private val away: TextView = itemView.findViewById(R.id.adapter_cardview_detail_id_away)
    private val prop_name: TextView = itemView.findViewById(R.id.adapter_cardview_detail_id_propnames)
    fun bindItem(item: DataPropertyRecycler) {
        if (item.is_property) {
            val card = itemView as CardView
            card.setCardBackgroundColor(card.context.resources.getColor(R.color.colorPrimary))
            prop_name.text = item.name
            prop_name.textColor = Color.WHITE
        } else {
            home.text = if (item.home_value == null) "-" else item.home_value
            away.text = if (item.away_value == null) "-" else item.away_value
            prop_name.text = item.name
        }
    }
}