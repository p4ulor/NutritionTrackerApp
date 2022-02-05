package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var layout: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val viewModel2: MainActivityViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = layout.root

        layout.button.setOnClickListener {
            viewModel2.getValuesToLog()
        }

        return root
    }
}

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}