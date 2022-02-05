package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.NutritionTrackerApp
import paulor.nutritiontrackerkotlin.databinding.FragmentFoodsAndMealsBinding
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.EdiblesDataBase
import paulor.nutritiontrackerkotlin.model.Food
import paulor.nutritiontrackerkotlin.model.TablesDAO
import paulor.nutritiontrackerkotlin.model.doAsyncWithResult
import paulor.nutritiontrackerkotlin.toast
import paulor.nutritiontrackerkotlin.views.FoodsAndMealsAdapter
import paulor.nutritiontrackerkotlin.views.OnItemClickListener

class DashboardFragment : Fragment(), OnItemClickListener {

    private var layout: FragmentFoodsAndMealsBinding? = null
    lateinit var recyclerView: RecyclerView
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log("onCreateView")
        layout = FragmentFoodsAndMealsBinding.inflate(inflater, container, false)
        val root: View = layout!!.root
        recyclerView = layout!!.nutrientsListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context /*or activity*/)
        viewModel.loadHistory()
        layout!!.addNewEdible.setOnClickListener {
            viewModel.getValues()
        }

        return root
    }

    

    override fun onStart() {
        super.onStart()
        log("onStart")
        viewModel.history?.observe(viewLifecycleOwner){
            recyclerView.adapter = FoodsAndMealsAdapter(it, this)
        }
        //recyclerView.adapter = FoodsAndMealsAdapter(viewModel.history?.value!!, this)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onItemClicked(gameDTO: Food, holderPosition: Int) {
        toast("yes", activity?.baseContext!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        layout = null
    }
}
