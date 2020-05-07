package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.raywenderlich.android.creatures.R
import kotlinx.android.synthetic.main.list_item_creature.view.*

class CreatureListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val listCreatureImage = itemView.findViewById(R.id.creatureImage) as ImageView
    val listFullName = itemView.findViewById(R.id.fullName) as TextView
}
