package paulor.nutritiontrackerkotlin.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.databinding.FragmentFoodsAndMealsBinding
import paulor.nutritiontrackerkotlin.views.FoodsAndMealsAdapter
import paulor.nutritiontrackerkotlin.views.OnItemClickListener

import android.view.*
import android.widget.*
import paulor.nutritiontrackerkotlin.*
import paulor.nutritiontrackerkotlin.model.Compound
import paulor.nutritiontrackerkotlin.model.Food
import paulor.nutritiontrackerkotlin.model.Nutrient
import pl.droidsonroids.gif.GifImageView
import pl.droidsonroids.gif.GifDrawable
import java.io.File


private const val TAG = "FoodsAndMealsFragment"

class FoodsAndMealsFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentFoodsAndMealsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment
    lateinit var foodAndMealAdapter: FoodsAndMealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        log("onCreateView")
        _binding = FragmentFoodsAndMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {

        foodAndMealAdapter = FoodsAndMealsAdapter(arrayListOf(), this)
        binding.nutrientsListRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = foodAndMealAdapter
        }

        binding.addNewEdibleButton.setOnClickListener {
            viewModel.getValuesToLog()
            log(activity?.supportFragmentManager?.backStackEntryCount.toString())

            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.alert_dialog_name, null)
            val name = dialogView.findViewById<EditText>(R.id.foodName)

            name.text.replace(0, 0, "")
            AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setMessage("Add edible")
                .setPositiveButton("Done") { dialog, id ->
                    val edible = Food(name.text.toString())
                    viewModel.repo.putFoodTableInDB(edible.toFoodsTable())
                    foodAndMealAdapter.addFood(edible)
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        binding.addNewEdibleButton.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCanceledOnTouchOutside(true) //Sets whether this dialog is canceled when touched outside the window's bounds. If setting to true, the dialog is set to be cancelable if not already set
            dialog.setContentView(R.layout.dialog_add_edible)
            val gif = dialog.findViewById<GifImageView>(R.id.gifImageView)
            //val gifFromAssets = GifDrawable(resources, R.drawable.food)
            //gif.background = gifFromAssets
            dialog.show()
        }

        viewModel.foods?.observe(viewLifecycleOwner) {
            foodAndMealAdapter.loadNewHistoryData(it)
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

    override fun onItemClicked(foodName: String) {
        viewModel.repo.addFood4TodayTotal(foodName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //prevent view hierarchy leak
    }
}
