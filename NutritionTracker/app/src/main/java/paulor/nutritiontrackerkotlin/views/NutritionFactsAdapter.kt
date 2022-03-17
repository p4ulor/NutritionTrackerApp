package paulor.nutritiontrackerkotlin.views

import android.view.*
import android.widget.NumberPicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.Compound
import paulor.nutritiontrackerkotlin.model.Nutrient
import paulor.nutritiontrackerkotlin.play
import kotlin.math.roundToInt

class NutritionFactsAdapter (
    var allNutrients: MutableList<Nutrient>,
    /*private val itemClickedListener: OnItemClickListener*/
    ) : RecyclerView.Adapter<NutritionFactsViewHolder>() { // a view that will create tuples that ammount to only filling up the whole screen, and that constant number of views will be reused when scrolling through all the tuples
    val holders = ArrayList<NutritionFactsViewHolder>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NutritionFactsViewHolder { //creates View tuples/holders. It's called by the layout manager when it needs a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nutrient_tuple, parent, false)
        val holder = NutritionFactsViewHolder(view, this)
        holders.add(holder)
        return holder
    }

    override fun onBindViewHolder(holder: NutritionFactsViewHolder, position: Int) { // re-use lists. Is called more than onCreateViewHolder
        holder.bindTo(allNutrients[position])
        log("Binded")
    }

    override fun getItemCount(): Int = allNutrients.size

    fun getAllHoldersValues() : ArrayList<Nutrient>{
        val nutrients = ArrayList<Nutrient>()
        holders.forEach {
            nutrients.add(Nutrient(it.compound, it.grams.value.toFloat()))
        }
        return nutrients
    }

    fun loadNewHistoryData(newHistory: List<Nutrient>){
        allNutrients = newHistory.toMutableList()
        notifyDataSetChanged()
    }

    fun addNutrient(nutrient: Nutrient){
        allNutrients.add(nutrient)
        notifyItemChanged(allNutrients.size)
    }

    fun getNutrient(position: Int) : Nutrient {
        return allNutrients[position]
    }
}

/* Implementation of the ViewHolder pattern. Its purpose is to eliminate the need for
 * executing findViewById each time a reference to a view's child is required.
 */
class NutritionFactsViewHolder(itemView: View/*, var itemClickedListener: OnItemClickListener*/,
                               private val nutritionFactsAdapter: NutritionFactsAdapter
) : RecyclerView.ViewHolder(itemView) {
    lateinit var compound: Compound
    private val name = itemView.findViewById<TextView>(R.id.name)
    var grams = itemView.findViewById<NumberPicker>(R.id.grams)
    private val unit = itemView.findViewById<TextView>(R.id.unit)
    fun bindTo(nutrient: Nutrient) { //Binds this view holder to the given quote item
        compound = nutrient.compound
        name.text = nutrient.compound.fullName

        grams.maxValue = 5000
        grams.minValue = 0
        grams.value = nutrient.amount.roundToInt()

        grams.setOnScrollListener { numberPicker, i -> play(R.raw.hihat5, itemView.context)
            //val index = nutritionFactsAdapter.allNutrients.indexOf(nutrient)
            //nutritionFactsAdapter.allNutrients[index].amount = numberPicker.value.toFloat()
        }

        unit.text = nutrient.compound.unit.fullName
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
