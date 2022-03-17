package paulor.nutritiontrackerkotlin

import paulor.nutritiontrackerkotlin.model.*


private const val TAG = "Repo"

class NutritionTrackerRepo(private val dao: TablesDAO) {

    fun getLatestFoodFromDB(callback: (Result<FoodsTable?>) -> Unit) {
        callbackAfterAsync(callback) {
            dao.getLast(1).firstOrNull()
        }
    }

    fun getFoodFromDB(foodName: String, callback: (Result<FoodsTable?>) -> Unit) {
        callbackAfterAsync(callback) {
            dao.getFood(foodName)
        }
    }

    fun putFoodTableInDB(food: FoodsTable) {
        doAsync { dao.insert(food) }
    }

    fun putMealTableInDB(meal: MealsTable): Boolean {
        doAsync {
            dao.insert(meal)
        }
        return true
    }

    fun getAllWith(name: String): List<Food> =
        dao.getAllFoodsByName(name).map {
            it.toFood()
        }


    fun addFood4TodayTotal(foodName: String) {
        doAsync {
            /*val total = dao.getMeal(TODAYS_TOTAL_MEAL).toMeal()
            total.addFood(dao.getFood(foodName).toFood())
            dao.insert(total.toMealsTable())*/
        }
    }
}

