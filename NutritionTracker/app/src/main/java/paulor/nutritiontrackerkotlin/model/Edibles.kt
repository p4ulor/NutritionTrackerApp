package paulor.nutritiontrackerkotlin.model

import java.util.*

data class Food (
    var name: String,
    var nutrients: ArrayList<Nutrient>,
    var price: Float = 0f,
    var amount: Float = 0f,
    var selfNutritionDataURL: String = "") {

    fun addNutrient(nutrient: Nutrient){

    }
}

data class Meal(
    var name: String,
    var foods: ArrayList<Food>,
    var price: Float = 0f,
    var amount: Float = 0f) {

    fun addFood(food: Food){

    }
}

// a Food's Nutrient, must have it's amount appropriated/rationed to the Food's quantity in grams
data class Nutrient(var nutrient: Compound, var amount: Float, var nutriUnit: NutriUnit) {
    constructor(nutrient: Compound, amount: Float) : this(nutrient, amount, NutriUnit.MG)
}

enum class NutriUnit (fullName: String) {
    U("Unit"),
    G("Grams"),
    MG("Milligrams"),
    MCG("Micrograms"),
    IU("International Unit")
}

