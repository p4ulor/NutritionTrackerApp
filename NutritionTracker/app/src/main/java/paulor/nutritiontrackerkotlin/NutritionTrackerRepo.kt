package paulor.nutritiontrackerkotlin

import paulor.nutritiontrackerkotlin.model.*


private const val TAG = "Repo"
const val TODAYS_TOTAL_MEAL = "TodaysTotal"

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

    fun putMealTableInDB(meal: MealsTable) : Boolean {
        if(meal.name==TODAYS_TOTAL_MEAL) false
        doAsync {
            dao.insert(meal)
        }
        return true
    }

    fun getAllWith(name: String?): List<Food>? {
        val result = doAsyncWithResult {
            dao.getAll().map {
                it.toFood()
            }
        }.value
        return result
    }

    fun addFood4TodayTotal(foodName: String){
        doAsync {
            val total = dao.getMeal(TODAYS_TOTAL_MEAL).toMeal()
            total.addFood(dao.getFood(foodName).toFood())
            dao.insert(total.toMealsTable())
        }
    }
}

