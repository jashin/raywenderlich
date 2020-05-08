package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.model.Creature
import com.raywenderlich.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_creature.view.creatureImage
import kotlinx.android.synthetic.main.list_item_creature_with_food.view.*

class CreatureListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    private lateinit var creature: Creature
    private val foodAdapter = FoodAdapter(mutableListOf())

    init{
        itemView.setOnClickListener(this)
    }

    fun bind(creature: Creature){
        this.creature = creature

        itemView.fullName.text = creature.fullName
        itemView.nickName.text = creature.nickname
        itemView.creatureImage.setImageResource(itemView.context.resources.getIdentifier(creature.uri, null, itemView.context.packageName))

    }

    override fun onClick(view: View) {
        val context = view.context
        val intent = CreatureActivity.newIntent(context, creature.id)
        context.startActivity(intent)
    }
}
