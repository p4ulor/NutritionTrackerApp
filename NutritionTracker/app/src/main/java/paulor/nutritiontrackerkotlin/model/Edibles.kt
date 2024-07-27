package paulor.nutritiontrackerkotlin.model

import kotlin.collections.ArrayList

abstract class Edible(
    var name: String,
    var price: Float,
    var amount: Float, //the major and global variable that propagates through the price and the nutrients in a food or meal
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
data class Nutrient(var compound: Compound, internal var amount: Float = 0f) {
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
    fun editCompound(amount :Float) : Boolean {
        if(amount < 0f) return false
        this.amount = amount
        return true
    }
}



enum class EdibleUnit(val fullName: String, var representativeGrams: Float = 0f) {
    U("Unit", 100f),
    SPO("Spoon", 14f),
    SCO("Scoop", 25f),

    G("Grams"), OUN("Ounce"),
    GAL("Gallon"), LIT("Litter");

    companion object {
        fun getAsStringArray() : ArrayList<String> {
            val list = ArrayList<String>()
            values().forEach {
                list.add(it.fullName)
            }
            return list
        }

        fun gallonToLitters(food: Food, reverse: Boolean) : Float {
            if(food.unit==LIT && reverse) return food.amount/3.785f *1000 //litters -> gallons
            if(food.unit==GAL) return food.amount*3.785f //gallons -> litters
            return -1f
        }

        fun ounceToGrams(food: Food, reverse: Boolean) : Float { // 1 gram = 0.0352 Ounces
            if(food.unit==G && reverse) return food.amount/28.349f *1000//grams -> ounces
            if(food.unit==OUN) return food.amount*28.349f //ounces -> grams
            return -1f
        }
    }
}
