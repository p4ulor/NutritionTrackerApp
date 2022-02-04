package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.NutritionTrackerApp
import paulor.nutritiontrackerkotlin.databinding.FragmentFoodsAndMealsBinding
import paulor.nutritiontrackerkotlin.model.EdiblesDataBase
import paulor.nutritiontrackerkotlin.model.Food
import paulor.nutritiontrackerkotlin.model.TablesDAO
import paulor.nutritiontrackerkotlin.model.doAsyncWithResult
import paulor.nutritiontrackerkotlin.views.FoodsAndMealsAdapter
import paulor.nutritiontrackerkotlin.views.OnItemClickListener

class DashboardFragment : Fragment(), OnItemClickListener {

    private var layout: FragmentFoodsAndMealsBinding? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = FragmentFoodsAndMealsBinding.inflate(inflater, container, false)
        val root: View = layout!!.root
        recyclerView = layout!!.nutrientsListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context /*or activity*/)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.adapter = ViewModelProvider(this).get(MainActivityViewModel::class.java).loadHistory().value?.let {
            FoodsAndMealsAdapter(
                it, this)
        }

    }


    override fun onItemClicked(gameDTO: Food, holderPosition: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        layout = null
    }
}
