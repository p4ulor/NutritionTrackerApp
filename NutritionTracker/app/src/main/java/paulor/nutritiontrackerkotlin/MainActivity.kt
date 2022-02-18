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

//html unit
/*import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlDivision
import com.gargoylesoftware.htmlunit.html.HtmlPage*/
import paulor.nutritiontrackerkotlin.databinding.ActivityMainBinding
import paulor.nutritiontrackerkotlin.model.*
import java.io.IOException
//bee
import org.apache.http.client.fluent.*


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

    val context = getApplication<NutritionTrackerApp>()
    val dao: TablesDAO by lazy { context.ediblesDB.getDAO() }
    val repo: NutritionTrackerRepo by lazy { context.repo }

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
        doAsync {
            /*WebClient(BrowserVersion.CHROME).use { webClient ->
                //webClient.options.isThrowExceptionOnScriptError = false
                //webClient.options.isJavaScriptEnabled = true
                //webClient.options.isCssEnabled = true //if false, it crashes
                //webClient.options.isUseInsecureSSL = true

                //webClient.waitForBackgroundJavaScript(20000)
                log("We runnin'")
                val page = webClient.getPage<HtmlPage>("https://nutritiondata.self.com/facts/nut-and-seed-products/3086/2")
                //Thread.sleep(21_000)
                log(page.asXml())
                val div = page.getHtmlElementById<HtmlDivision>("nf1 left")
                log(div.toString())
                val pageAsText = page.asNormalizedText()
                log(pageAsText)
                log("I waited")
            }*/
            try {

                // Create request
                val content: Content = Request.Get("https://app.scrapingbee.com/api/v1/?api_key=QWNF6FHPUCKMJ9Z7CCZ4V92J1RQAV26YMT9TC4GHCABJ5E7ZI87HR1V6P3HDEFIP2GY78Z18EWJW95P0&url=http%3A%2F%2Fhttpbin.org%2Fanything%3Fjson") // Fetch request and return content
                    .execute().returnContent()

                // Print content
                log(content.asString())
            } catch (e: IOException) {
                log(e.toString())
            }

        }
    }

    /*fun URLRequest(url: String, callback: (Result<String>) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val responseListener = Response.Listener<String> { response ->
            log(response.toString())
            val result = Result.success(response.toString())
            callback(result)
            log("Response received")
        }

        val errorListener = Response.ErrorListener {
            log("Connection error")
            val result = Result.failure<String>(Error())
            callback(result)
        }

        val stringRequest = StringRequest(Request.Method.GET, url, responseListener, errorListener)
        queue.add(stringRequest)
        log("Request finished")
    }*/





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


/*
    ALSO DOESNT LOAD javascript content

    fun scrape() {
        doAsync {
            val httpsUrl = "https://nutritiondata.self.com/facts/nut-and-seed-products/3086/2"
            try {
                val url = URL(httpsUrl)
                val con: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                println("****** Content of the URL ********")
                val br = BufferedReader(InputStreamReader(con.getInputStream()))
                var input: String?
                while (br.readLine().also { input = it } != null) {
                    log(input.toString())
                }
                br.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
 */