package com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.adapter

import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.DataPropertyRecycler
import org.jetbrains.anko.textColor
import java.lang.ref.WeakReference

class DetailMatchRecyclerAdapter(data_props: MutableList<DataPropertyRecycler>) :
    RecyclerView.Adapter<DetailCardViewHolder>() {
    private val props: WeakReference<MutableList<DataPropertyRecycler>> = WeakReference(data_props)

    init {
        setHasStableIds(true)
    }
    override fun onCreateViewHolder(root: ViewGroup, p1: Int): DetailCardViewHolder {
        val layout = LayoutInflater.from(root.context).inflate(R.layout.adapter_cardview_matchdetail, root, false)
        return DetailCardViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return if (props.get() != null) props.get()!!.size else 0
    }


    override fun onBindViewHolder(p0: DetailCardViewHolder, p1: Int) {
        val prop = props.get()!![p0.adapterPosition]
        synchronized(prop) {
            if (prop.is_property.equals(true)) {
                val card = p0.itemView as CardView
                card.setCardBackgroundColor(p0.itemView.context.resources.getColor(R.color.colorPrimary))
                p0.prop_name.text = prop.name
                p0.prop_name.textColor = Color.WHITE
            } else {
                p0.home.text = if (prop.home_value == null) "-" else prop.home_value
                p0.away.text = if (prop.away_value == null) "-" else prop.away_value
                p0.prop_name.text = prop.name
            }
            Log.i("LogRecycler", "i = p1. ${p0.adapterPosition}. propbool val = ${prop.is_property}")
        }
        //p0.bindItem(prop)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}