package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller.DataPropertyRecycler

class DetailTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(prop: DataPropertyRecycler) {
        val text = itemView as TextView
        text.text = prop.name
    }

    companion object {
        fun newInstance(ctx: Context): DetailTextViewHolder {
            val text = TextView(ctx)
            text.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text.gravity = Gravity.CENTER_HORIZONTAL
            return DetailTextViewHolder(text)
        }
    }
}
