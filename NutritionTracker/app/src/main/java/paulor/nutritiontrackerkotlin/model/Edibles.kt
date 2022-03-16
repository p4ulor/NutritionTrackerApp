package paulor.nutritiontrackerkotlin.model

import kotlin.collections.ArrayList

abstract class Edible(
    var name: String,
    var price: Float,
    var amount: Float, //used for making up Meals and making calculation for Tracking
    var unit: EdibleUnit,
    var comment: String
)

class Food ( // I'm using "class" and not "data class" to have the flexibility of using or not "var" in the constructor. Same for meal
    foodName: String,
    var nutrients: ArrayList<Nutrient>? = null,
    price: Float = 0f,
    amount: Float = 0f,
    unit: EdibleUnit = EdibleUnit.G,
    comment: String = "",
    var selfNutritionDataURL: String = "") : Edible(foodName, price, amount, unit, comment) {

    fun addNutrient(nutrient: Nutrient){
        if(nutrients==null) nutrients = ArrayList()
        nutrients?.add(nutrient)
    }

    fun sumUpProperties(food: Food) : Food {
        nutrients = Nutrient.sumUp(nutrients, food.nutrients)
        price += food.price
        amount += food.amount
        return this
    }

    fun toFoodsTable() = FoodsTable(name, nutrients, price, amount, unit, comment, selfNutritionDataURL)
}

class Meal(
    mealName: String,
    var foods: ArrayList<Food>? = null,
    price: Float = 0f,
    amount: Float = 0f,
    unit: EdibleUnit = EdibleUnit.G,
    comment: String = "") : Edible(mealName, price, amount, unit, comment) {

    fun addFood(food: Food){
        if(foods==null) foods = ArrayList()
        foods?.add(food)
    }

    fun sumUpProperties() : Meal {
        //todo
        return this
    }

    fun toMealsTable() = MealsTable(name, foods, price, amount, unit, comment)
}

data class Track(
    var date: String,
    var foods: ArrayList<Food>? = null,
    var meals: ArrayList<Meal>? = null,
    var price: Float = 0f,
    var comment: String = "") {

    fun addFood(food: Food) {
        if(foods==null) foods = ArrayList()
        foods?.add(food)
    }

    fun addMeal(meal: Meal) {
        if(meals==null) meals = ArrayList()
        meals?.add(meal)
    }

    /*fun sumUpProperties() : Meal {
        //todo
        return this
    }*/
}

// a Food's Nutrient, must have it's amount appropriated/rationed to the Food's quantity in grams
data class Nutrient(var compound: Compound, var amount: Float) {
    companion object {
        fun getAllWithZero() : ArrayList<Nutrient> {
            val compounds = Compound.values()
            val ret: ArrayList<Nutrient> = ArrayList()
            compounds.forEach { ret.add(Nutrient(it, 0f)) }
            return ret
        }

        fun sumUp(nutrients1: ArrayList<Nutrient>?, nutrients2: ArrayList<Nutrient>?) : ArrayList<Nutrient>? {
            if(nutrients1==null && nutrients2 == null) return null
            return nutrients1 ?: nutrients2
            nutrients1?.forEachIndexed { index, nutrient ->
                if(nutrient == nutrients2?.get(index))
                    nutrient.amount += nutrients2?.get(index).amount
            }
            return nutrients1
        }

        fun getCompounds(nutrients: ArrayList<Nutrient>) : ArrayList<Compound> {
            val compounds = ArrayList<Compound>()
            nutrients.forEach {
                compounds.add(it.compound)
            }
            return compounds
        }
    }
}

interface UnitType {
    val fullName: String
}

enum class EdibleUnit(override val fullName: String) : UnitType {
    U("Unit"),
    G("Grams"),

    GAL("Gallon"),
    OUN("Ounce"),
    CUP("Coup"),
    SPO("Spoon"),
    LIT("Litter");

    companion object {
        fun getAsStringArray() : ArrayList<String> {
            val list = ArrayList<String>()
            values().forEach {
                list.add(it.fullName)
            }
            return list
        }
    }
}

enum class CompoundUnit(override val fullName: String) : UnitType {
    MG("Milligrams"),
    MCG("Micrograms"),
    IU("International Unit")
}
