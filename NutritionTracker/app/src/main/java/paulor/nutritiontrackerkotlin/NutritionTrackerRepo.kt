package paulor.nutritiontrackerkotlin

import paulor.nutritiontrackerkotlin.model.*


private const val TAG = "Repo"

class NutritionTrackerRepo(private val dao: TablesDAO) {
    fun getLatestFoodFromDB(callback: (Result<FoodsTable?>) -> Unit) {
        callbackAfterAsync(callback) {
            dao.getLast(1).firstOrNull()
        }
    }

    private fun saveToDB(dto: Food, callback: (Result<Unit>) -> Unit = { }) {
        /*callbackAfterAsync(callback) {
            //dao.insert(dto.toGameTable())
        }*/
    }

    private fun putFoodInDB(food: Food){
        /*doAsync {
            //dao.insert(food.to)
        }*/
    }

    fun putFoodTableInDB(food: FoodsTable){
        doAsync {
            dao.insert(food)
        }
    }

    fun getAll(): List<Food>? {
        val result = doAsyncWithResult {
            dao.getAll().map {
                it.toFood()
            }
        }.value
        return result
    }
}

