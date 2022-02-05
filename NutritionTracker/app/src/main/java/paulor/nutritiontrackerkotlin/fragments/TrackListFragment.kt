package paulor.nutritiontrackerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import paulor.nutritiontrackerkotlin.databinding.FragmentTracklistBinding

class TrackListFragment : Fragment() {

    private lateinit var layout: FragmentTracklistBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = FragmentTracklistBinding.inflate(inflater, container, false)
        val root: View = layout.root

        layout.addNewEdibleButton.setOnClickListener {

        }

        return root
    }

}

