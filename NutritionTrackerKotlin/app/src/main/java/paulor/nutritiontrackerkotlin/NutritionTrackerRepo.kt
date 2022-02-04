package paulor.nutritiontrackerkotlin

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import paulor.nutritiontrackerkotlin.model.Food
import paulor.nutritiontrackerkotlin.model.FoodsTable
import paulor.nutritiontrackerkotlin.model.TablesDAO
import paulor.nutritiontrackerkotlin.model.callbackAfterAsync
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Repo"

class NutritionTrackerRepo(private val dao: TablesDAO) {
    private fun getLatestPuzzleFromDB(callback: (Result<FoodsTable?>) -> Unit) {
        callbackAfterAsync(callback) {
            dao.getLast(1).firstOrNull()
        }
    }

    private fun saveToDB(dto: Food, callback: (Result<Unit>) -> Unit = { }) {
        callbackAfterAsync(callback) {
            //dao.insert(dto.toGameTable())
        }
    }
}

