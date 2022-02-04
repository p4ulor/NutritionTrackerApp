package paulor.nutritiontrackerkotlin

import android.app.Application
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import paulor.nutritiontrackerkotlin.databinding.ActivityMainBinding
import paulor.nutritiontrackerkotlin.model.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeScreem, R.id.food_and_meals, R.id.tracklist
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
    var history: List<Food>? = null
        private set

    val foodsDTO: TablesDAO by lazy {
        getApplication<NutritionTrackerApp>().ediblesDB.getDAO()
    }

    fun loadHistory() : LiveData<List<Food>?> {
        val result = doAsyncWithResult {
            foodsDTO.getAll().map {
                it.toFood()
            }
        }
        history = result.value
        return result
    }

    fun getValues(){
        NutritionTrackerRepo(foodsDTO).getLatestFoodFromDB {
            it.onSuccess {
                log(it.toString())
            }
        }
    }
}