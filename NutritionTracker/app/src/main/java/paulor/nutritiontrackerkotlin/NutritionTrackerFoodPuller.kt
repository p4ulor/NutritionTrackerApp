package paulor.nutritiontrackerkotlin

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.html5.div

data class Entry(
    val name: String,
    val amount: String,
    val unit: String,
    val percentDv: String,
)

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

// https://github.com/skrapeit/skrape.it/issues/180
fun getNuts(): Map<String, List<Entry>> = skrape(BrowserFetcher) {
    request {
        url = "https://nutritiondata.self.com/facts/nut-and-seed-products/3086/2"
        timeout = 20_000
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
