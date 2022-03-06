package paulor.nutritiontrackerkotlin.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.databinding.FragmentEdibleFactsBinding
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.mapper
import paulor.nutritiontrackerkotlin.model.Compound
import paulor.nutritiontrackerkotlin.model.EdibleUnit
import paulor.nutritiontrackerkotlin.model.Food
import paulor.nutritiontrackerkotlin.model.Nutrient
import paulor.nutritiontrackerkotlin.views.FoodsAndMealsAdapter
import paulor.nutritiontrackerkotlin.views.NutritionFactsAdapter

private const val TAG = "EdibleFactsFragment"

class EdibleFactsFragment : Fragment(),  AdapterView.OnItemSelectedListener {

    private lateinit var layout: FragmentEdibleFactsBinding
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment
    private lateinit var recyclerView: RecyclerView

    private var food: Food = Food("Unknown")
    private lateinit var units: Spinner
    private var adapter: NutritionFactsAdapter? = null
    private var changed = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log("onCreateView")
        layout = FragmentEdibleFactsBinding.inflate(inflater, container, false)
        val root: View = layout.root
        recyclerView = layout.nutrientsListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context /*or activity*/)

        units = layout.unitSpinner

        units.adapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, EdibleUnit.getAsStringArray())
        units.onItemSelectedListener = this

        val stringJson = arguments?.getString("food")
        if(!stringJson.isNullOrEmpty()) {
            food = mapper.fromJson(stringJson, Food::class.java)
            adapter = food.nutrients?.let { NutritionFactsAdapter(it) }
            recyclerView.adapter = adapter
            if(food.nutrients?.size==Compound.size) layout.addNutrientButton.visibility = View.GONE
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        layout.priceNumber.setText(food.price.toString().toCharArray(), 0, food.price.toString().length)
        layout.commentText.text = food.comment
        layout.priceNumber.doOnTextChanged { text, start, before, count ->
            changed = true
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 -> {}
            1 -> {}
            2 -> {}
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onPause() {
        super.onPause()
        log(TAG+"onPause")
        if(changed) viewModel.repo.putFoodTableInDB(getThisFood().toFoodsTable())
    }

    private fun getThisFood() : Food {
        val list = ArrayList<Nutrient>()
        adapter?.notifyDataSetChanged()
        repeat(adapter?.itemCount ?: 0) {
            list.add(adapter?.getNutrient(it)!!)
        }
        return Food(food.name, nutrients = list, price = layout.priceNumber.text.toString().toFloat())
    }




}