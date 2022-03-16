package paulor.nutritiontrackerkotlin/*

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.html5.div
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import paulor.nutritiontrackerkotlin.log

private val DocElement.categoryName: String
    get() = div {
        withAttribute = "align" to "center"
        findFirst {
            text
        }
    }

private fun DocElement.textOf(className: String) = div {
    withClass = className
    0 { text }
}

private val DocElement.entries: List<Entry>
    get() = div {
        withClass = "clearer"
        findAll {
            map { entry ->
                Entry(
                    name = entry.textOf("nf1"),
                    amount = entry.textOf("nf2"),
                    unit = entry.textOf("nf3"),
                    percentDv = entry.textOf("nf4"),
                )
            }
        }
    }

suspend fun getNuts(): Map<String, List<Entry>> {
    return skrape(BrowserFetcher) {
        request {
            url = "https://nutritiondata.self.com/facts/nut-and-seed-products/3086/2"
            timeout = 40_000
        }
        response {
            htmlDocument {
                "#NutritionInformationSlide .m-t13" {
                    findAll {
                        associate {
                            it.categoryName to it.entries
                        }
                    }
                }
            }
        }
    }
}

fun getFood() {
    GlobalScope.launch {
        getNuts().forEach {
            log(it.toString())
        }
    }
    */
/*viewModelScope.launch {
        //didnt work, gave network on main nexception
    }*//*

}
}

data class Entry(
    val name: String,
    val amount: String,
    val unit: String,
    val percentDv: String,
)






*/








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