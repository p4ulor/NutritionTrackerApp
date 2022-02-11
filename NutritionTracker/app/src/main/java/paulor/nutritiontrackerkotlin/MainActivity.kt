package paulor.nutritiontrackerkotlin

import android.app.Application
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ch.qos.logback.classic.spi.CallerData.extract
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Method
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home) {
            supportFragmentManager.popBackStack()
            if(supportFragmentManager.backStackEntryCount==0){
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                supportActionBar!!.setHomeButtonEnabled(false)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
    private val webPageScrap =  MutableLiveData<String>("")
    var history: LiveData<List<Food>>? = null
        private set

    val dao: TablesDAO by lazy {
        getApplication<NutritionTrackerApp>().ediblesDB.getDAO()
    }

    val repo: NutritionTrackerRepo by lazy {
        getApplication<NutritionTrackerApp>().repo
    }

    fun loadHistory() : LiveData<List<Food>> {
        val result = doAsyncWithResult {
            dao.getAll().map {
                it.toFood()
            }
        }
        history = result
        return result
    }

    fun getValuesToLog(){
        repo.getLatestFoodFromDB { result ->
            result.onSuccess {
                log(it.toString())
            }
        }
    }

    fun parse(url: String) { // https://github.com/skrapeit/skrape.it  https://docs.skrape.it/docs/dsl/extracting-data-from-websites
        skrape(HttpFetcher) {
            request {
                this.url = url
                method = Method.GET
                //headers = mapOf("Content-Type" to "application/json")
                //body = """{"foo":"bar"}"""
            }
            response {
                htmlDocument {
                    log(this.wholeText)
                }
            }
        }

    }

}