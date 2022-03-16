package paulor.nutritiontrackerkotlin.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paulor.nutritiontrackerkotlin.*
import paulor.nutritiontrackerkotlin.databinding.FragmentEdibleFactsBinding
import paulor.nutritiontrackerkotlin.model.Compound
import paulor.nutritiontrackerkotlin.model.EdibleUnit
import paulor.nutritiontrackerkotlin.model.Food
import paulor.nutritiontrackerkotlin.model.Nutrient
import paulor.nutritiontrackerkotlin.views.NutritionFactsAdapter

private const val TAG = "EdibleFactsFragment"

class EdibleFactsFragment : Fragment(),  AdapterView.OnItemSelectedListener {

    private lateinit var layout: FragmentEdibleFactsBinding
    private val viewModel: MainActivityViewModel by activityViewModels() //is a reference to the same instance of the view model of the activity that hosts this fragment

    private lateinit var edibleName: String
    private lateinit var units: Spinner
    private lateinit var price: TextView
    private lateinit var amount: TextView
    private lateinit var linkButton: ImageView
    private lateinit var linkText: String
    private lateinit var comment: TextView
    private lateinit var recyclerView: RecyclerView
    private var adapter: NutritionFactsAdapter? = null

    private lateinit var addNutrientButton: Button

    private var food: Food = Food("Unknown")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { log("onCreateView")
        layout = FragmentEdibleFactsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        units = layout.unitSpinner; price = layout.priceNumber
        amount = layout.amountNumber; linkButton = layout.foodLink
        comment = layout.commentText

        val root: View = layout.root
        recyclerView = layout.nutrientsListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context /*or activity*/)

        units.adapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, EdibleUnit.getAsStringArray())
        units.onItemSelectedListener = this

        addNutrientButton = layout.addNutrientButton

        //load food into the widgets
        val stringJson = arguments?.getString("food")
        if(!stringJson.isNullOrEmpty()) food = mapper.fromJson(stringJson, Food::class.java)

        return root
    }

    override fun onStart() { super.onStart()
        edibleName = food.name
        units.setSelection(food.unit.ordinal)

        amount.text = food.amount.toString()
        price.text = food.price.toString()

        linkText = food.selfNutritionDataURL

        linkButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.alert_dialog_name, null)
            val name = dialogView.findViewById<EditText>(R.id.foodName)
            name.text.replace(0, 0, edibleName)
            builder.setView(dialogView)
                .setMessage("Change selfNutrition URL")
                .setPositiveButton("Done") { dialog, id ->
                    linkText = name.text.toString()
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    dialog.dismiss()
                }

            builder.create().show()
        }

        linkButton.setOnLongClickListener {
            true
        }

        comment.text = food.comment

        adapter = if(food.nutrients==null) NutritionFactsAdapter(ArrayList<Nutrient>()) else NutritionFactsAdapter(food.nutrients!!)
        recyclerView.adapter = adapter

        hideButtonIfHasAllNutrients()

        addNutrientButton.setOnClickListener {
            // custom dialog
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //dialog.setCancelable(true) //Sets whether this dialog is cancelable with the BACK key
            dialog.setCanceledOnTouchOutside(true) //Sets whether this dialog is canceled when touched outside the window's bounds. If setting to true, the dialog is set to be cancelable if not already set
            dialog.setContentView(R.layout.dialog_add_nutrient)

            val listOfNutrients = dialog.findViewById<Spinner>(R.id.nutrients)
            val nutrientsToAdd = if(food.nutrients==null) Compound.getAllAsStringArray() else Compound.getAsStringArrayExcept(food.nutrients!!)
            listOfNutrients.adapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, nutrientsToAdd)

            val amount = dialog.findViewById<EditText>(R.id.amount)
            val unit = dialog.findViewById<TextView>(R.id.unitTextView)
            unit.text = Compound.getCompound(listOfNutrients.selectedItem.toString()).toString()
            val grams = dialog.findViewById<NumberPicker>(R.id.grams)
            grams.maxValue = 5000
            grams.minValue = 0
            grams.value = 0

            grams.setOnScrollListener { numberPicker, i -> play(R.raw.hihat5, requireContext())
                layout.root.postDelayed( {amount.setText(numberPicker.value.toString())}, 200) //this is because sometimes it doesnt sync, and this helps to sync
            }

            val addButton = dialog.findViewById<Button>(R.id.AddButton)
            addButton.setOnClickListener {
                val compound = Compound.getCompound(listOfNutrients.selectedItem.toString())
                if(compound!=null){
                    val nutri = Nutrient(compound, amount.text.toString().toFloat())
                    adapter?.addNutrient(nutri)
                    hideButtonIfHasAllNutrients()
                } else toast("Error", requireContext())
                dialog.dismiss()
            }

            dialog.show()
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 -> {toast("Selected: "+EdibleUnit.values()[units.selectedItemPosition].fullName, requireContext())}
            1 -> {}
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onStop() { super.onStop(); log(TAG, "onStop")
        viewModel.repo.putFoodTableInDB(getThisFood().toFoodsTable())
    }

    private fun getThisFood() : Food {
        val list = adapter?.getAllHoldersValues()
        return Food(edibleName,
                    list,
                    price.text.toString().toFloat(),
                    amount.text.toString().toFloat(),
                    EdibleUnit.values()[units.selectedItemPosition],
                    comment.text.toString(),
                    linkText
        )
    }

    override fun onPause() { super.onPause(); log(TAG, "onPause") }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edible_facts_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeName -> {
                val builder = AlertDialog.Builder(requireContext())
                val inflater = requireActivity().layoutInflater
                val dialogView = inflater.inflate(R.layout.alert_dialog_name, null)
                val name = dialogView.findViewById<EditText>(R.id.foodName)
                name.text.replace(0, 0, edibleName)
                builder.setView(dialogView)
                    .setMessage("Change name")
                    .setPositiveButton("Done") { dialog, id ->
                        // repo replace
                        edibleName =  name.text.toString()

                    }
                    .setNegativeButton("Cancel") { dialog, id ->
                        dialog.dismiss()
                    }

                builder.create().show()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideButtonIfHasAllNutrients() {
        if(food.nutrients?.size==Compound.size) layout.addNutrientButton.visibility = View.GONE
        //else layout.addNutrientButton.visibility = View.VISIBLE
    }
}