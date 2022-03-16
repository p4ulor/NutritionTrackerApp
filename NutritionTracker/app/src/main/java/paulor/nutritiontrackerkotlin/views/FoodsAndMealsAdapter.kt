package paulor.nutritiontrackerkotlin.views

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.Food


class FoodsAndMealsAdapter(
    private var allFoods: ArrayList<Food>,
    private val itemClickedListener: OnItemClickListener
    ) : RecyclerView.Adapter<FoodItemViewHolder>() { // a view that will create tuples that ammount to only filling up the whole screen, and that constant number of views will be reused when scrolling through all the tuples


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder { //creates View tuples/holders. It's called by the layout manager when it needs a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_or_meal_tuple, parent, false)
        return FoodItemViewHolder(view, itemClickedListener)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) { // re-use lists. Is called more than onCreateViewHolder
        holder.bindTo(allFoods[position])
        log("Binded")
    }

    override fun getItemCount(): Int = allFoods.size

    fun loadNewHistoryData(newHistory: ArrayList<Food>){
        allFoods = newHistory
        notifyDataSetChanged()
    }

    fun addFood(food: Food) {
        allFoods.add(food)
        notifyDataSetChanged()
    }

    fun getFood(position: Int) : Food? {
        return if (position in 1 until itemCount) allFoods?.get(position) else null
    }
}

/* Implementation of the ViewHolder pattern. Its purpose is to eliminate the need for
 * executing findViewById each time a reference to a view's child is required.
 */
class FoodItemViewHolder(itemView: View, var itemClickedListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener { //receives a view and adds properties to the view
    private val name = itemView.findViewById<TextView>(R.id.name)
    private lateinit var food: Food
    fun bindTo(food: Food) { //Binds this view holder to the given quote item
        this.food = food
        name.text = food.name
        itemView.setOnLongClickListener {
            it.showContextMenu()
            it.isSelected = !it.isSelected
            true
        }
        itemView.setOnClickListener {
            itemClickedListener.onItemClicked(name.text.toString())
        }

        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        //menu?.setHeaderTitle("Choose")
        val addToMeal: MenuItem = menu!!.add(Menu.NONE, 1, 1, "Add to meal")
        addToMeal.setOnMenuItemClickListener(onEditMenu)

        val edit: MenuItem = menu!!.add(Menu.NONE, 2, 2, "Edit")
        edit.setOnMenuItemClickListener(onEditMenu)
    }

    private val onEditMenu: MenuItem.OnMenuItemClickListener = MenuItem.OnMenuItemClickListener { menuItem ->

        when (menuItem.itemId) {
            1 -> {}
            2 -> itemClickedListener.onItemPressed(food, 2)
        }
        true
    }
}

interface OnItemClickListener {
    fun onItemPressed(food: Food, option: Int)
    fun onItemClicked(foodName: String)
}
