package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.databinding.FragmentFoodsAndMealsBinding
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.views.FoodsAndMealsAdapter
import paulor.nutritiontrackerkotlin.views.OnItemClickListener

import android.view.*
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.mapper
import paulor.nutritiontrackerkotlin.model.Food

private const val TAG = "FoodsAndMealsFragment"

class FoodsAndMealsFragment : Fragment(), OnItemClickListener {

    private lateinit var layout: FragmentFoodsAndMealsBinding
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log("onCreateView")
        layout = FragmentFoodsAndMealsBinding.inflate(inflater, container, false)
        val root: View = layout.root
        recyclerView = layout.nutrientsListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context /*or activity*/)
        viewModel.loadHistory()

        layout.addNewEdibleButton.setOnClickListener {
            viewModel.getValuesToLog()
            log(activity?.supportFragmentManager?.backStackEntryCount.toString())
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
        viewModel.foods?.observe(viewLifecycleOwner){
            recyclerView.adapter = FoodsAndMealsAdapter(it, this)
        }
    }

    override fun onItemPressed(food: Food, option: Int) {
        val bundle = Bundle()
        bundle.putString("food", mapper.toJson(food))
        findNavController().navigate(R.id.action_food_and_meals_to_edible_facts, bundle)
        navOptions {
            anim {
                enter = android.R.animator.fade_in
                exit = android.R.animator.fade_out
            }
        }
    }

    override fun onItemClicked(foodName: String){
        viewModel.repo.addFood4TodayTotal(foodName)
    }
}
