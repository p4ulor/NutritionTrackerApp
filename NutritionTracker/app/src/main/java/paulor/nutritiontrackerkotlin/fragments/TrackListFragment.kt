package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import paulor.nutritiontrackerkotlin.databinding.FragmentTracklistBinding

class NotificationsFragment : Fragment() {

    private val viewModel: NotificationsViewModel by viewModels()
    private var layout: FragmentTracklistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = FragmentTracklistBinding.inflate(inflater, container, false)
        val root: View = layout!!.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        layout = null
    }
}

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}