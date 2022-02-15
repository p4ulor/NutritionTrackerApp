package paulor.nutritiontrackerkotlin.model

import androidx.room.*

@Entity(tableName = "FOODS")
data class FoodsTable (
    @PrimaryKey val name: String,
    val values: Array<Nutrient>
) {
    fun toFood() = Food(
        name = this.name,
        values = this.values
    )
}

@Entity(tableName = "MEALS")
data class MealsTable (
    @PrimaryKey val name: String,
    val foods: Array<Food>,
    val ammountGrams: IntArray
) {

}

@Dao
interface TablesDAO { // Data Access Object, provides methods that your app can use to query, update, insert, and delete data in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE) //https://stackoverflow.com/a/54260385/9375488
    fun insert(foodsTable: FoodsTable)

    @Delete
    fun delete(foodsTable: FoodsTable)

    @Update
    fun update(foodsTable: FoodsTable)

    @Query("SELECT * FROM FOODS ORDER BY name DESC") //to change color: Color scheme -> General -> Injected language fragment
    fun getAll() : List<FoodsTable>

    @Query("SELECT * FROM FOODS ORDER BY name DESC LIMIT :count") //https://stackoverflow.com/questions/31016070/how-to-use-substring-in-rawquery-android
    fun getLast(count: Int) : List<FoodsTable>

}

@Database(entities = [FoodsTable::class/*, MealsTable::class*/], version = 1) //creates DB schema
@TypeConverters(Converters::class)
abstract class EdiblesDataBase : RoomDatabase(){
    abstract fun getDAO() : TablesDAO
}

@ProvidedTypeConverter
class Converters { // https://stackoverflow.com/questions/52693954/android-room-to-persist-complex-objects/52695045   https://developer.android.com/training/data-storage/room/referencing-data
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
    fun nutrientToString(nutrient: Array<Nutrient>) : String {
        return nutrient.toString()
    }

    @TypeConverter
    fun nutrientStringToNutrient(nutrient: String) : Array<Nutrient> {
        return arrayOf(Nutrient(Nutrients.CAL, 20f, Unit.g))
    }
}

