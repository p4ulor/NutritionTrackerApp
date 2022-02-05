package paulor.nutritiontrackerkotlin.model

import paulor.nutritiontrackerkotlin.NutritionTrackerRepo

private val foods = arrayOf(
    FoodsTable(name = "Oats",
        values = floatArrayOf(367f, 56.1f, 12.1f, 8.4f, 9.1f, 0.4f, 0.002f, 0.5f, 0.2f, 1.1f, 1.1f, 0.1f, 32f, 52f, 4.3f, 138f, 410f, 362f, 6f, 3.6f, 0.4f, 3.6f, 0.0289f, 100f, 2200f)
    ),

    FoodsTable(name = "Rice",
        values = floatArrayOf(355f, 78f, 7.5f, 1f, 2.4f)
    ),

    FoodsTable(name = "Chickpeas",
        values = floatArrayOf(111f, 12f, 6.5f, 2.6f, 6f)
    ),

    FoodsTable(name = "Lentils",
        values = floatArrayOf(48f, 339f, 27f, 1.8f, 12f)
    ),

    FoodsTable(name = "Quinoa",
        values = floatArrayOf(120f, 21.3f, 4.4f, 1.9f, 2.8f, 0f, 0f, 0.6f, 0f, 0.1f, 0.1f, 0.4f, 0f, 0.1f, 0.042f, 17f, 1.5f, 64f, 152f, 172f, 7f, 1.1f, 0.2f, 0.6f, 0.0028f, 0f, 0f)
    )

)

fun initializeDB(repo: NutritionTrackerRepo) {
    doAsync {
        foods.forEach {
            repo.putFoodTableInDB(it)
        }
    }
}
