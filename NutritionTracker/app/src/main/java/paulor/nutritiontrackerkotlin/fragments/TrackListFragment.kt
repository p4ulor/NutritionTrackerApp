package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.MainActivityViewModel
import paulor.nutritiontrackerkotlin.databinding.FragmentTracklistBinding

private const val TAG = "TrackListFragment"

class TrackListFragment : Fragment() {

    private lateinit var layout: FragmentTracklistBinding
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = FragmentTracklistBinding.inflate(inflater, container, false)
        val root: View = layout.root


        return root
    }

}

