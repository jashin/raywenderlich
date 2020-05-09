package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.model.CompositeItem
import com.raywenderlich.android.creatures.model.Creature
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_creature.view.creatureImage
import kotlinx.android.synthetic.main.list_item_planet_header.view.*

class CreatureListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    private lateinit var creature: Creature
    private val foodAdapter = FoodAdapter(mutableListOf())

    init{
        itemView.setOnClickListener(this)
    }

//    fun bind(creature: Creature){
//        this.creature = creature
//
//        itemView.fullName.text = creature.fullName
//        itemView.fullName.text = creature.nickname
//        itemView.creatureImage.setImageResource(itemView.context.resources.getIdentifier(creature.uri, null, itemView.context.packageName))
//        animateView(itemView)
//    }
fun bind(compositeItem: CompositeItem) {
    if (compositeItem.isHeader) {
        itemView.headerName.text = compositeItem.header.name
    } else {
        creature = compositeItem.creature
        val context = itemView.context
        itemView.creatureImage.setImageResource(context.resources.getIdentifier(creature.thumbnail, null, context.packageName))
        itemView.fullName.text = creature.fullName
        itemView.nickname.text = creature.nickname
        animateView(itemView)
    }
}

    override fun onClick(view: View) {
        val context = view.context
        val intent = CreatureActivity.newIntent(context, creature.id)
        context.startActivity(intent)
    }

    private fun animateView(viewToAnimate: View) {
        if (viewToAnimate.animation == null) {
            val animId = R.anim.scale_xy
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, animId)
            viewToAnimate.animation = animation
        }
    }
}
