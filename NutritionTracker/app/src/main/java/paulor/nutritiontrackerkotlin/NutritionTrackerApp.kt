package paulor.nutritiontrackerkotlin

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.room.Room
import paulor.nutritiontrackerkotlin.model.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

// ADD "<application
//        android:name=".NutritionTrackerApp" FOR THIS CLASS TO WORK

private const val TAG = "LOG_"
private const val DATEPATTERN = "dd/M/yyyy" //The 'M' must be uppercase or it will read the minutes
private val allNutrients: Array<Nutrients> = Nutrients.values()

class NutritionTrackerApp : Application() {

    init { Log.i(TAG, "NutritionTrackerApp executed") }

    val ediblesDB: EdiblesDataBase by lazy {
        Room.databaseBuilder(this, EdiblesDataBase::class.java, "edibles"/*DB key*/).addTypeConverter(Converters()).build()
    }

    val repo: NutritionTrackerRepo by lazy {
        NutritionTrackerRepo(ediblesDB.getDAO())
    }

    override fun onCreate() {
        super.onCreate()
        log("App created")
        doAsync {
            log(TAG, "Initializing DB")
            ediblesDB.getDAO().insert( //For demonstration purposes. It's default puzzle that already comes with the app :)
                FoodsTable(
                    name = "Oats",
                    values = floatArrayOf(367f, 56.1f, 12.1f, 8.4f, 9.1f, 0.4f, 0.002f, 0.5f, 0.2f, 1.1f, 1.1f, 0.1f, 32f, 52f, 4.3f, 138f, 410f, 362f, 6f, 3.6f, 0.4f, 3.6f, 0.0289f, 100f, 2200f)
                )
            )
        }
    }
}

// UTILITY AND GLOBAL METHODS

fun play(id: Int, ctx: Context) {
    val player = MediaPlayer.create(ctx, id)
    player.setOnCompletionListener { player.release() }
    player.start()
}

fun getTodaysDate(): String {
    val s: StringBuilder = StringBuilder(SimpleDateFormat(DATEPATTERN).format(Date()))
    if(s[4] =='/'){
        s.insert(3,'0') // "01/1/2022" -> "01/01/2022"
    }
    return s.toString()
}

fun convertToDate(date: String?): java.sql.Date {
    val df: DateFormat = SimpleDateFormat(DATEPATTERN)
    return java.sql.Date(df.parse(date).time)
}

fun log(s: String) = Log.i(TAG, s)

fun log(t: String, s: String) =  Log.i(TAG+t, s)

fun log(arrayOfStrings: Array<String>?){
    val sb = StringBuilder()
    arrayOfStrings?.forEach { sb.append("$it ") }
    log(sb.toString())
}

fun toast(s: String, ctx: Context) = Toast.makeText(ctx, s, Toast.LENGTH_LONG).show()

fun toast(id: Int, ctx: Context) = toast(ctx.getString(id), ctx)

fun topToast(text: String, ctx: Context) {
    val toast = Toast.makeText(ctx, text, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.TOP + Gravity.CENTER_HORIZONTAL, 0, 0)
    toast.show()
}
