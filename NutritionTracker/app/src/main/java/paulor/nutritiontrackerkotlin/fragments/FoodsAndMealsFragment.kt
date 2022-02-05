package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.databinding.FragmentFoodsAndMealsBinding
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.Food
import paulor.nutritiontrackerkotlin.toast
import paulor.nutritiontrackerkotlin.views.FoodsAndMealsAdapter
import paulor.nutritiontrackerkotlin.views.OnItemClickListener

class FoodsAndMealsFragment : Fragment(), OnItemClickListener {

    private lateinit var layout: FragmentFoodsAndMealsBinding
    lateinit var recyclerView: RecyclerView
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment

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
        log("onStart_")
        viewModel.history?.observe(viewLifecycleOwner){
            recyclerView.adapter = FoodsAndMealsAdapter(it, this)
        }
    }

    override fun onItemClicked(gameDTO: Food, holderPosition: Int) {
        findNavController().navigate(R.id.action_food_and_meals_to_nutrition_facts)
        navOptions {
            anim {
                enter = android.R.animator.fade_in
                exit = android.R.animator.fade_out
            }
        }
    }
}
