package paulor.nutritiontrackerkotlin

import android.app.Application
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import paulor.nutritiontrackerkotlin.databinding.ActivityMainBinding
import paulor.nutritiontrackerkotlin.model.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val layout by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(layout.root)

        val navView: BottomNavigationView = layout.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_screem, R.id.food_and_meals, R.id.tracklist
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {

    private val context = getApplication<NutritionTrackerApp>()
    private val dao: TablesDAO by lazy { context.ediblesDB.getDAO() }
    val repo: NutritionTrackerRepo by lazy { context.repo }


    var todaysNutrition = Meal("TODAYS_TOTAL")


    private val webPageScrap = MutableLiveData("")

    val foods: LiveData<List<Food>> = dao.getAllFoods().map { foodList ->
        foodList.map { it.toFood() }
    }

    val meals: LiveData<List<Meal>> = dao.getAllMeals().map { mealsList ->
        mealsList.map { it.toMeal() }
    }

    fun getValuesToLog() {
        repo.getLatestFoodFromDB { result ->
            result.onSuccess {
                log(it?.toFood().toString())
            }
        }
    }
}