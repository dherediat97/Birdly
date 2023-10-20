package com.dherediat97.birdly.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dherediat97.birdly.R
import com.dherediat97.birdly.domain.model.RecordedSound

class RecentRecordSoundAdapter(private val context: Context, private val items: List<RecordedSound>) :
    RecyclerView.Adapter<RecentRecordSoundAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recentRecordSoundIV: ImageView
        val recentSpecieNameTV: TextView

        init {
            recentRecordSoundIV = view.findViewById(R.id.recentRecordedSoundIV)
            recentSpecieNameTV = view.findViewById(R.id.recentSpecieNameTV)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recent_record_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(context).load(items[position].img).into(viewHolder.recentRecordSoundIV)
        viewHolder.recentSpecieNameTV.text = items[position].en
    }
}