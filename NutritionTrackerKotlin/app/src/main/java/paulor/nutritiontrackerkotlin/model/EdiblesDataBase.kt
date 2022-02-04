package paulor.nutritiontrackerkotlin.model

import androidx.room.*

@Entity(tableName = "FOODS")
data class FoodsTable (
    @PrimaryKey val name: String,
    val nutrients: Array<Nutrients?>,
    val values: FloatArray
) {
    fun toFood() = Food(
        name = this.name,
        nutrients = this.nutrients,
        values = this.values
    )
}

@Entity(tableName = "MEALS")
data class MealsTable (
    @PrimaryKey val name: String,
    val foods: Array<Food>,
    val ammount: IntArray
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

    @Query("SELECT * FROM FOODS ORDER BY name DESC LIMIT :count")
    fun getLast(count: Int) : List<FoodsTable>

    @Query("SELECT * FROM FOODS WHERE name=:id")
    fun getGameWithID(id: String) : FoodsTable


}

@Database(entities = [FoodsTable::class], version = 1) //creates DB schema
abstract class EdiblesDataBase : RoomDatabase(){
    abstract fun getDAO() : TablesDAO
}

