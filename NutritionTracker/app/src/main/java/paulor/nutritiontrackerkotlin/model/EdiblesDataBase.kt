package paulor.nutritiontrackerkotlin.model

import androidx.room.*
import com.google.gson.Gson
import kotlin.collections.ArrayList

@Entity(tableName = "FOODS")
data class FoodsTable (
    @PrimaryKey var name: String,
    var nutrients: ArrayList<Nutrient>? = null,
    var price: Float = 0f,
    var amount: Float = 0f,
    var unit: EdibleUnit = EdibleUnit.G,
    var comment: String = "",
    var selfNutritionDataURL: String = "") {

    fun toFood() = Food(name, nutrients, price, amount, unit, comment, selfNutritionDataURL)
}

@Entity(tableName = "MEALS")
data class MealsTable (
    @PrimaryKey var name: String,
    var foods: ArrayList<Food>? = null,
    var price: Float = 0f,
    var ammount: Float = 0f,
    var unit: EdibleUnit = EdibleUnit.G,
    var comment: String = "") {

    fun toMeal() = Meal(name, foods, price, ammount, unit, comment)
}

@Entity(tableName = "TRACK")
data class TrackTable (
    @PrimaryKey var date: String,
    var foods: ArrayList<Food>? = null,
    var meals: ArrayList<Meal>? = null,
    var price: Float = 0f,
    var comment: String = "") {

    fun toTrack() = Track(date, foods, meals, price, comment)
}

@Dao
interface TablesDAO { // Data Access Object, provides methods that your app can use to query, update, insert, and delete data in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(foodsTable: FoodsTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mealsTable: MealsTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trackTable: TrackTable)

    @Delete
    fun delete(foodsTable: FoodsTable)

    @Update
    fun update(foodsTable: FoodsTable)

    @Query("SELECT * FROM FOODS ORDER BY name DESC")
    fun getAllFoods() : List<FoodsTable>

    @Query("SELECT * FROM MEALS ORDER BY name DESC")
    fun getAllMeals() : List<MealsTable>

    @Query("SELECT * FROM FOODS ORDER BY name DESC LIMIT :count")
    fun getLast(count: Int) : List<FoodsTable>

    @Query("SELECT * FROM FOODS WHERE name=:name")
    fun getFood(name: String) : FoodsTable

    @Query("SELECT * FROM MEALS WHERE name=:name")
    fun getMeal(name: String) : MealsTable

    @Query("SELECT * FROM TRACK WHERE date=:date")
    fun getTrack(date: String) : TrackTable
}

@Database(entities = [FoodsTable::class, MealsTable::class, TrackTable::class], version = 1) //creates DB schema
@TypeConverters(Converters::class)
abstract class EdiblesDataBase : RoomDatabase(){
    abstract fun getDAO() : TablesDAO
}

@ProvidedTypeConverter
class Converters { // https://stackoverflow.com/questions/52693954/android-room-to-persist-complex-objects/52695045   https://developer.android.com/training/data-storage/room/referencing-data
    private val mapper = Gson()

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
    fun nutrientToString(nutrient: ArrayList<Nutrient>?) : String {
        return mapper.toJson(nutrient)
    }

    @TypeConverter
    fun nutrientStringToFood(nutrient: String) : ArrayList<Nutrient> {
        return mapper.fromJson(nutrient, ArrayList::class.java) as ArrayList<Nutrient>
    }

    @TypeConverter
    fun edibleUnitToString(unit: EdibleUnit) : String {
        return mapper.toJson(unit)
    }

    @TypeConverter
    fun edibleUnitStringToEdibleUnit(unit: String) : EdibleUnit {
        return mapper.fromJson(unit, EdibleUnit::class.java)
    }

    @TypeConverter
    fun arrayListFoodToString(food: ArrayList<Food>) : String {
        return mapper.toJson(food)
    }

    @TypeConverter
    fun foodStringToArrayListFood(food: String) : ArrayList<Food> {
        return mapper.fromJson(food, ArrayList::class.java) as ArrayList<Food>
    }

    @TypeConverter
    fun arrayListMealToString(meal: ArrayList<Meal>) : String {
        return mapper.toJson(meal)
    }

    @TypeConverter
    fun foodStringToArrayListMeal(meal: String) : ArrayList<Meal> {
        return mapper.fromJson(meal, ArrayList::class.java) as ArrayList<Meal>
    }
}

