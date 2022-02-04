package paulor.nutritiontrackerkotlin.model

import androidx.room.*

@Entity(tableName = "FOODS")
data class FoodsTable (
    @PrimaryKey val name: String,
    val values: FloatArray
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
class Converters {
    @TypeConverter
    fun stringToFloatArray(values: String): FloatArray {
        val list = values.split(" ")
        val ret = FloatArray(list.size)
        ret.forEachIndexed{ index, _ ->
            ret[index] = list[index].toFloat()
        }
        return ret
    }

    @TypeConverter
    fun floatArrayToString(values: FloatArray): String {
        val sb = StringBuilder()
        values.forEach {
            sb.append(it).append(" ")
        }
        sb.deleteCharAt(sb.length-1)
        return sb.toString()
    }
}

