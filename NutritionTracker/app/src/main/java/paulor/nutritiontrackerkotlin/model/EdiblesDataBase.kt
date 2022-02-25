package paulor.nutritiontrackerkotlin.model

import androidx.room.*
import com.google.gson.Gson
import kotlin.collections.ArrayList

@Entity(tableName = "FOODS")
data class FoodsTable (
    @PrimaryKey var name: String,
    var nutrients: ArrayList<Nutrient>,
    var price: Float,
    var amount: Float,
    var selfNutritionDataURL: String) {

    fun toFood() = Food(
        name = this.name,
        nutrients = this.nutrients,
        price = this.price,
        amount = this.amount,
        selfNutritionDataURL = this.selfNutritionDataURL
    )
}

@Entity(tableName = "MEALS")
data class MealsTable (
    @PrimaryKey var name: String,
    var foods: ArrayList<Food>,
    var price: Float,
    var ammount: Float) {

    fun toMeal() = Meal(
        name = this.name,
        foods = this.foods,
        price = this.price,
        amount = this.price
    )
}

@Dao
interface TablesDAO { // Data Access Object, provides methods that your app can use to query, update, insert, and delete data in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE) //https://stackoverflow.com/a/54260385/9375488
    fun insert(foodsTable: FoodsTable)

    @Delete
    fun delete(foodsTable: FoodsTable)

    @Update
    fun update(foodsTable: FoodsTable)

    @Query("SELECT * FROM FOODS ORDER BY name DESC")
    fun getAll() : List<FoodsTable>

    @Query("SELECT * FROM FOODS ORDER BY name DESC LIMIT :count")
    fun getLast(count: Int) : List<FoodsTable>

}

@Database(entities = [FoodsTable::class/*, MealsTable::class*/], version = 1) //creates DB schema
@TypeConverters(Converters::class)
abstract class EdiblesDataBase : RoomDatabase(){
    abstract fun getDAO() : TablesDAO
}

@ProvidedTypeConverter
class Converters { // https://stackoverflow.com/questions/52693954/android-room-to-persist-complex-objects/52695045   https://developer.android.com/training/data-storage/room/referencing-data
    private val mapper: Gson by lazy { Gson() }

    @TypeConverter
    fun stringToFloatArray(values: String) : FloatArray {
        val list = values.split(" ")
        val floats = FloatArray(list.size)
        floats.forEachIndexed{ index, _ ->
            floats[index] = list[index].toFloat()
        }
        return floats
    }

    @TypeConverter
    fun floatArrayToString(values: FloatArray) : String {
        val sb = StringBuilder()
        values.forEach {
            sb.append(it).append(" ")
        }
        sb.deleteCharAt(sb.length-1)
        return sb.toString()
    }

    @TypeConverter
    fun nutrientToString(nutrient: ArrayList<Nutrient>) : String {
        return Gson().toJson(nutrient)
    }

    @TypeConverter
    fun nutrientStringToFood(nutrient: String) : ArrayList<Nutrient> {
        return Gson().fromJson(nutrient, ArrayList::class.java) as ArrayList<Nutrient>
    }

    @TypeConverter
    fun mealToString(food: Meal) : String {
        return Gson().toJson(food)
    }

    @TypeConverter
    fun mealStringToNutrient(food: String) : Meal {
        return Gson().fromJson(food, Meal::class.java)
    }
}

