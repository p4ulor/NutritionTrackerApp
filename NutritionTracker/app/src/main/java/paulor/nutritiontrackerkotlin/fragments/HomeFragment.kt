package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.databinding.FragmentHomeBinding
import paulor.nutritiontrackerkotlin.log
import paulor.nutritiontrackerkotlin.model.doAsync

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var layout: FragmentHomeBinding
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = layout.root

        layout.button.setOnClickListener {
            viewModel.getValuesToLog()
            //viewModel.getFood()
        }

        return root
    }

}