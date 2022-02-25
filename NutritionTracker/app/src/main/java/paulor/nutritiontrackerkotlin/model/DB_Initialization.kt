package paulor.nutritiontrackerkotlin.model

import paulor.nutritiontrackerkotlin.NutritionTrackerRepo

private val foods = arrayOf(
    FoodsTable(name = "Oats",
        nutrients = arrayListOf(Nutrient(Compound.CAL, 379f, NutriUnit.U)),
        price = 0f,
        amount = 0f,
        selfNutritionDataURL = "https://nutritiondata.self.com/facts/breakfast-cereals/1597/2"
    ),

    FoodsTable(name = "Oats2",
        nutrients = arrayListOf(Nutrient(Compound.CAL, 379f, NutriUnit.U)),
        price = 0f,
        amount = 0f,
        selfNutritionDataURL = "https://nutritiondata.self.com/facts/breakfast-cereals/1597/2"
    )

)

fun initializeDB(repo: NutritionTrackerRepo) {
    doAsync {
        foods.forEach {
            repo.putFoodTableInDB(it)
        }
    }
}
