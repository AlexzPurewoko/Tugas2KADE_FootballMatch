package com.apwdevs.footballMatch.activityComponents.onDetailMatch.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apwdevs.footballMatch.R
import com.apwdevs.footballMatch.activityComponents.onDetailMatch.dataController.DataPropertyRecycler
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
        p0.bindItem(prop)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}