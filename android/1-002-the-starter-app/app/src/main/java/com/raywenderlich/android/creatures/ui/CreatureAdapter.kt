package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.app.inflate
import com.raywenderlich.android.creatures.model.CompositeItem
import com.raywenderlich.android.creatures.model.Creature

class CreatureAdapter(private val compositeItems: MutableList<CompositeItem>): RecyclerView.Adapter<CreatureListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatureListViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_creature, parent, false)
//        return CreatureListViewHolder(view)
        return when(viewType) {
            ViewType.HEADER.ordinal -> CreatureListViewHolder(parent.inflate(R.layout.list_item_planet_header))
            ViewType.CREATURE.ordinal -> CreatureListViewHolder(parent.inflate(R.layout.list_item_creature))
            else -> throw IllegalArgumentException("Illegal value for viewType")
        }
    }

//    fun updateCreatureList(creatures: List<Creature>) {
//        this.creatureList.clear()
//        this.creatureList.addAll(creatures)
//        notifyDataSetChanged()
//    }
fun updateCreatures(compositeItems: List<CompositeItem>) {
    this.compositeItems.clear()
    this.compositeItems.addAll(compositeItems)
    notifyDataSetChanged()
}

    override fun getItemViewType(position: Int): Int {
        return if (compositeItems[position].isHeader) {
            ViewType.HEADER.ordinal
        } else {
            ViewType.CREATURE.ordinal
        }
    }

    override fun getItemCount(): Int {
        return compositeItems.size
    }

    override fun onBindViewHolder(holder: CreatureListViewHolder, position: Int) {
        holder.bind(compositeItems[position])
    }


    enum class ViewType {
        HEADER, CREATURE
    }
}