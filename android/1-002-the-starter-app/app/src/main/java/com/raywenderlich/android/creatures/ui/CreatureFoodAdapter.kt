package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.model.Creature
import com.raywenderlich.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_creature.view.creatureImage
import kotlinx.android.synthetic.main.list_item_creature_with_food.view.*

class CreatureWithFoodAdapter(private var creatureList : MutableList<Creature>): RecyclerView.Adapter<CreatureWithFoodAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_creature_with_food, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.foodRecyclerView.recycledViewPool = viewPool
        LinearSnapHelper().attachToRecyclerView(holder.itemView.foodRecyclerView)
        return holder
    }

    override fun getItemCount(): Int {
        return creatureList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatureList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private lateinit var creature: Creature
        private val foodAdapter = FoodAdapter(mutableListOf())

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(creature: Creature){
            this.creature = creature

//        itemView.fullName.text = creature.fullName
//        itemView.nickName.text = creature.nickname
            itemView.creatureImage.setImageResource(itemView.context.resources.getIdentifier(creature.uri, null, itemView.context.packageName))
            itemView.foodRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.foodRecyclerView.adapter = foodAdapter
            foodAdapter.updateFoods(CreatureStore.getCreatureFoods(creature))
        }

        override fun onClick(view: View) {
            val context = view.context
            val intent = CreatureActivity.newIntent(context, creature.id)
            context.startActivity(intent)
        }
    }
}