package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.model.Creature
import com.squareup.picasso.Picasso

class CreatureAdapter(private val creatureList : List<Creature>): RecyclerView.Adapter<CreatureListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatureListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_creature, parent, false)
        return CreatureListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return creatureList.size
    }

    override fun onBindViewHolder(holder: CreatureListViewHolder, position: Int) {
        Picasso.get().load(creatureList[position].image).into(holder.listCreatureImage)
        holder.listFullName.text = creatureList[position].fullName
    }
}