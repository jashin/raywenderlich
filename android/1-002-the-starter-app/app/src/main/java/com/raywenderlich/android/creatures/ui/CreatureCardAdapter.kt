package com.raywenderlich.android.creatures.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.red
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.model.Creature
import com.raywenderlich.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_creature.view.creatureImage
import kotlinx.android.synthetic.main.list_item_creature.view.nickName
import kotlinx.android.synthetic.main.list_item_creature_card.view.*
import kotlinx.android.synthetic.main.list_item_creature_with_food.view.*

class CreatureCardAdapter(private var creatureList: MutableList<Creature>) : RecyclerView.Adapter<CreatureCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_creature_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return creatureList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatureList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var creature: Creature
        private val foodAdapter = FoodAdapter(mutableListOf())

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature

            itemView.nickName.text = creature.nickname
            val imageResource = itemView.context.resources.getIdentifier(creature.uri, null, itemView.context.packageName)
            itemView.creatureImage.setImageResource(imageResource)
            setBackgroundColors(itemView.context, imageResource)
        }

        override fun onClick(view: View) {
            val context = view.context
            val intent = CreatureActivity.newIntent(context, creature.id)
            context.startActivity(intent)
        }

        private fun setBackgroundColors(context: Context, imageResource: Int) {
            val image = BitmapFactory.decodeResource(context.resources, imageResource)
            Palette.from(image).generate {
                val backgroundColor = it.getDominantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                itemView.creatureCard.setBackgroundColor(backgroundColor)
                itemView.nickNameHolder.setBackgroundColor(backgroundColor)
                val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
                itemView.nickName.setTextColor(textColor)
            }
        }

        private fun isColorDark(color: Int) : Boolean {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }
    }
}