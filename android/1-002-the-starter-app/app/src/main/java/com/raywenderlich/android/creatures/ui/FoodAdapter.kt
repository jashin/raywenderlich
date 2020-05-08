package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.model.Food
import kotlinx.android.synthetic.main.lisit_item_food.view.*

class FoodAdapter(private var foodList: MutableList<Food>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lisit_item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    fun updateFoods(foods: List<Food>) {
        foodList.clear()
        foodList.addAll(foods)
        notifyDataSetChanged()
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(food: Food) {
            itemView.foodImage.setImageResource(itemView.context.resources.getIdentifier(food.thumbnail, null, itemView.context.packageName))
        }
    }
}
