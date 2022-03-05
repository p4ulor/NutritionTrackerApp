package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.R
import paulor.nutritiontrackerkotlin.databinding.FragmentEdibleFactsBinding
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.EdibleUnit

class EdibleFactsFragment : Fragment(),  AdapterView.OnItemSelectedListener {

    private lateinit var layout: FragmentEdibleFactsBinding
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment
    private lateinit var recyclerView: RecyclerView

    private var price: Float = 0f
    private var amount: Float = 0f
    private lateinit var units: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log("onCreateView")
        layout = FragmentEdibleFactsBinding.inflate(inflater, container, false)
        val root: View = layout.root
        recyclerView = layout.nutrientsListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context /*or activity*/)

        units = layout.unitSpinner

        units.adapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, EdibleUnit.getAsStringArray())
        units.onItemSelectedListener = this

        layout.amountText.text = arguments?.getString("some_argument")
        return root
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
        //viewModel.repo.
    }


}