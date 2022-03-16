package paulor.nutritiontrackerkotlin.model

import paulor.nutritiontrackerkotlin.NutritionTrackerRepo

private val foods = arrayOf(

    FoodsTable(name = "Oats",
        nutrients = arrayListOf(Nutrient(Compound.CAL, 379f), Nutrient(Compound.CARBS, 226f),
                                Nutrient(Compound.VA, 100f), Nutrient(Compound.V12, 3000f)),
        price = 2f,
        amount = 0f,
        unit = EdibleUnit.G,
        comment = "https://www.continente.pt/produto/flocos-aveia-finos-integral-pack-poupanca-continente-equilibrio-7246729.html",
        selfNutritionDataURL = "https://nutritiondata.self.com/facts/breakfast-cereals/1597/2"
    ),

    FoodsTable(name = "Oats2",
        nutrients = arrayListOf(Nutrient(Compound.CAL, 379f)
        ),
        price = 0f,
        amount = 0f,
        selfNutritionDataURL = "https://nutritiondata.self.com/facts/breakfast-cereals/1597/2"
    )

)

private val meals = arrayOf(
    MealsTable("Meal")
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
