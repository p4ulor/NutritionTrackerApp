package paulor.nutritiontrackerkotlin.views

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.Food

class NutritionFactsAdapter (
    private var allFoods: List<Food>,
    private val itemClickedListener: OnItemClickListener
    ) : RecyclerView.Adapter<NutritionFactsViewHolder>() { // a view that will create tuples that ammount to only filling up the whole screen, and that constant number of views will be reused when scrolling through all the tuples

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NutritionFactsViewHolder { //creates View tuples/holders. It's called by the layout manager when it needs a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_or_meal_tuple, parent, false)
        return NutritionFactsViewHolder(view, itemClickedListener)
    }

    override fun onBindViewHolder(holder: NutritionFactsViewHolder, position: Int) { // re-use lists. Is called more than onCreateViewHolder
        holder.bindTo(allFoods[position])
        log("Binded")
    }

    override fun getItemCount(): Int = allFoods.size

    fun loadNewHistoryData(newHistory: List<Food>){
        allFoods = newHistory
        notifyDataSetChanged()
    }

    fun getGameDTO(position: Int) : Food? {
        return if (position in 1 until itemCount) allFoods?.get(position) else null
    }
}

/* Implementation of the ViewHolder pattern. Its purpose is to eliminate the need for
 * executing findViewById each time a reference to a view's child is required.
 */
class NutritionFactsViewHolder(itemView: View, var itemClickedListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) { //receives a view and adds properties to the view
    private val name = itemView.findViewById<TextView>(R.id.name)

    fun bindTo(food: Food) { //Binds this view holder to the given quote item
        name.text = food.name
        itemView.setOnLongClickListener {
            it.showContextMenu()
            it.isSelected = !it.isSelected
            true
        }
        itemView.setOnClickListener {
            itemClickedListener.onItemClicked(name.text.toString())
        }
    }
}
