package paulor.nutritiontrackerkotlin.views

import android.view.*
import android.widget.NumberPicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.Nutrient
import paulor.nutritiontrackerkotlin.play
import kotlin.math.roundToInt

class NutritionFactsAdapter (
    var allNutrients: List<Nutrient>,
    /*private val itemClickedListener: OnItemClickListener*/
    ) : RecyclerView.Adapter<NutritionFactsViewHolder>() { // a view that will create tuples that ammount to only filling up the whole screen, and that constant number of views will be reused when scrolling through all the tuples

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NutritionFactsViewHolder { //creates View tuples/holders. It's called by the layout manager when it needs a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nutrient_tuple, parent, false)
        return NutritionFactsViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: NutritionFactsViewHolder, position: Int) { // re-use lists. Is called more than onCreateViewHolder
        holder.bindTo(allNutrients[position])
        log("Binded")
    }

    override fun getItemCount(): Int = allNutrients.size

    fun loadNewHistoryData(newHistory: List<Nutrient>){
        allNutrients = newHistory
        notifyDataSetChanged()
    }

    fun getNutrient(position: Int) : Nutrient {
        return allNutrients[position]
    }
}

/* Implementation of the ViewHolder pattern. Its purpose is to eliminate the need for
 * executing findViewById each time a reference to a view's child is required.
 */
class NutritionFactsViewHolder(
    itemView: View/*, var itemClickedListener: OnItemClickListener*/,
    val nutritionFactsAdapter: NutritionFactsAdapter
) : RecyclerView.ViewHolder(itemView) { //receives a view and adds properties to the view
    private val name = itemView.findViewById<TextView>(R.id.name)
    private var grams = itemView.findViewById<NumberPicker>(R.id.grams)
    private val unit = itemView.findViewById<TextView>(R.id.unit)
    fun bindTo(nutrient: Nutrient) { //Binds this view holder to the given quote item
        name.text = nutrient.nutrient.fullName

        grams.maxValue = 5000
        grams.minValue = 0
        grams.value = nutrient.amount.roundToInt()

        grams.setOnScrollListener { numberPicker, i -> play(R.raw.hihat5, itemView.context);
            val index = nutritionFactsAdapter.allNutrients.indexOf(nutrient)
            nutritionFactsAdapter.allNutrients[index].amount = numberPicker.value.toFloat()
        }

        unit.text = nutrient.nutrient.unit.name
        itemView.setOnLongClickListener {
            it.showContextMenu()
            it.isSelected = !it.isSelected
            true
        }
        itemView.setOnClickListener {
            /*itemClickedListener.onItemClicked(name.text.toString())*/
        }
    }
}
