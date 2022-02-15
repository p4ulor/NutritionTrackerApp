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
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlDivision
import com.gargoylesoftware.htmlunit.html.HtmlPage
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

    fun getFood() {
        WebClient().use { webClient ->
            webClient.options.isThrowExceptionOnScriptError = false
            webClient.options.isCssEnabled = false
            webClient.options.isUseInsecureSSL = true

            val page = webClient.getPage<HtmlPage>("https://nutritiondata.self.com/facts/nut-and-seed-products/3086/2")
            webClient.waitForBackgroundJavaScript(10000)
            log(page.asXml())
            val div = page.getHtmlElementById<HtmlDivision>("nf1 left")
            log(div.toString())
            val pageAsText = page.asNormalizedText()
            log(pageAsText)
        }
    }




}




/*
    JSOUP IS AN HTML SOURCE ONLY PARSER, DOESNT LOAD VALUES in <span>
    Jsoup does not support JavaScript, and, because of this, any dynamically generated content or content which is added to the page after page load cannot be extracted from the page. If you need to extract content which is added to the page with JavaScript, there are a few alternative options:
    https://sodocumentation.net/jsoup


    doAsync {
            Jsoup.connect("https://nutritiondata.self.com/facts/nut-and-seed-products/3086/2").get().run {
                    this.select("#NutritionInformationSlide .m-t13").forEachIndexed { i, element ->
                        val name = element.getElementsByClass("nf1 left")
                        val ammount = element.getElementsByClass("nf2 left").next()
                        val unit = element.getElementsByClass("nf3 left")
                        val percentDV = element.getElementsByClass("nf4 left")

                        log("${name.text()}, ${ammount}, ${unit.text()}, ${percentDV.text()}")
                        //titleAnchor.sel
                    }
                }
        }
     */