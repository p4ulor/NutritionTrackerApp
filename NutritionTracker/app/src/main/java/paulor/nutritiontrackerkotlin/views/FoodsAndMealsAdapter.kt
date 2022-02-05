package paulor.nutritiontrackerkotlin.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.model.Food

class FoodsAndMealsAdapter(
    private var allFoods: List<Food>,
    private val itemClickedListener: OnItemClickListener
    ) : RecyclerView.Adapter<HistoryItemViewHolder>() { // a view that will create tuples that ammount to only filling up the whole screen, and that constant number of views will be reused when scrolling through all the tuples

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder { //creates View tuples/holders. It's called by the layout manager when it needs a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_or_meal_tuple, parent, false)
        return HistoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) { // re-use lists. Is called more than onCreateViewHolder
        holder.bindTo(allFoods[position], itemClickedListener)
    }

    override fun getItemCount(): Int = allFoods.size

    //---Above methods are obligated to be overrated

    fun loadNewHistoryData(newHistory: List<Food>){
        allFoods = newHistory
        notifyDataSetChanged()
    }

    fun getGameDTO(position: Int) : Food? {
        return if (position in 1 until itemCount) allFoods?.get(position) else null
    }
}

/*
 * Implementation of the ViewHolder pattern. Its purpose is to eliminate the need for
 * executing findViewById each time a reference to a view's child is required.
 */
class HistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //receives a view and adds properties to the view
    private val name = itemView.findViewById<TextView>(R.id.name)

    fun bindTo(food: Food, itemClickedListener: OnItemClickListener) { //Binds this view holder to the given quote item
        name.text = food.name

        itemView.setOnClickListener {
            itemClickedListener.onItemClicked(food, adapterPosition)
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(food: Food, holderPosition: Int)
}
