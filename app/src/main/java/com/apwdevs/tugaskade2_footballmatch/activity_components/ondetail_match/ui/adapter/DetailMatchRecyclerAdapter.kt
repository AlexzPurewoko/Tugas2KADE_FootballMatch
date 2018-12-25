package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.apwdevs.tugaskade2_footballmatch.R
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.DataPropertyRecycler

class DetailMatchRecyclerAdapter(private val data_props: MutableList<DataPropertyRecycler>) :
    RecyclerView.Adapter<DetailCardViewHolder>() {
    override fun onCreateViewHolder(root: ViewGroup, p1: Int): DetailCardViewHolder {
        val layout = LayoutInflater.from(root.context).inflate(R.layout.adapter_cardview_matchdetail, root, false)
        return DetailCardViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data_props.size
    }

    override fun onBindViewHolder(p0: DetailCardViewHolder, p1: Int) {
        p0.bindItem(data_props[p1])
    }
}

class ContainerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val rootLayout = itemView as RelativeLayout

    companion object {
        fun newInstance(ctx: Context): ContainerVH {
            val layout = RelativeLayout(ctx)
            layout.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            return ContainerVH(layout)
        }
    }
}