package paulor.nutritiontrackerkotlin.model

import kotlin.collections.ArrayList

data class Food (
    var name: String,
    var nutrients: ArrayList<Nutrient>? = null,
    var price: Float = 0f,
    var amount: Float = 0f, //used for making up Meals and making calculation for Tracking
    var unit: EdibleUnit = EdibleUnit.G,
    var comment: String = "",
    var selfNutritionDataURL: String = "") {

    fun addNutrient(nutrient: Nutrient){
        if(nutrients==null) nutrients = ArrayList()
        nutrients?.add(nutrient)
    }

    fun sumUpProperties(food: Food) : Food {
        //TODO
        price = food.price
        amount = food.amount
        return this
    }

    fun toFoodsTable() = FoodsTable(name, nutrients, price, amount, unit, comment, selfNutritionDataURL)
}

data class Meal(
    var name: String,
    var foods: ArrayList<Food>? = null,
    var price: Float = 0f,
    var amount: Float = 0f,
    var unit: EdibleUnit = EdibleUnit.G,
    var comment: String = "") {

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
data class Nutrient(var nutrient: Compound, var amount: Float) {
    companion object {
        fun getAllWithZero() : ArrayList<Nutrient> {
            val compounds = Compound.values()
            val ret: ArrayList<Nutrient> = ArrayList()
            compounds.forEach { ret.add(Nutrient(it, 0f)) }
            return ret
        }
    }
}

enum class EdibleUnit (val fullName: String) {
    U("Unit"),
    G("Grams"),
    MG("Milligrams"),
    MCG("Micrograms"),
    IU("International Unit");

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

enum class LiquidUnits {
    Gallon,
    Ounce,
    Coup,
    Spoon,
    Liter
}
