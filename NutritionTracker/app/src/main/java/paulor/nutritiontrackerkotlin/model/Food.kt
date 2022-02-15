package paulor.nutritiontrackerkotlin.model

class Food (val name: String, var values: FloatArray) {
    var gramCountForEachNutrient: Short = 100
    var price = 0f
    var selfNutritionDataURL = ""

    constructor(name: String, values: FloatArray, price: Float, gramCount: Short, selfNutritionDataURL: String) : this(name, values) {
        this.price = price
        gramCountForEachNutrient = gramCount
        this.selfNutritionDataURL = selfNutritionDataURL
    }

}