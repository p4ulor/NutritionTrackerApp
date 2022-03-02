package paulor.nutritiontrackerkotlin.model

import paulor.nutritiontrackerkotlin.NutritionTrackerRepo
import paulor.nutritiontrackerkotlin.TODAYS_TOTAL_MEAL

private val foods = arrayOf(

    FoodsTable(name = "Oats",
        nutrients = arrayListOf(Nutrient(Compound.CAL, 379f)),
        price = 0f,
        amount = 0f,
        selfNutritionDataURL = "https://nutritiondata.self.com/facts/breakfast-cereals/1597/2"
    ),

    FoodsTable(name = "Oats2",
        nutrients = arrayListOf(Nutrient(Compound.CAL, 379f)),
        price = 0f,
        amount = 0f,
        selfNutritionDataURL = "https://nutritiondata.self.com/facts/breakfast-cereals/1597/2"
    )

)

private val meals = arrayOf(
    MealsTable(TODAYS_TOTAL_MEAL)
)

fun initializeDB(repo: NutritionTrackerRepo) {
    doAsync {
        foods.forEach {
            repo.putFoodTableInDB(it)
        }

        meals.forEach {
            repo.putMealTableInDB(it)
        }
    }
}
