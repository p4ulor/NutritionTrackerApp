package paulor.nutritiontrackerkotlin.model

data class Food (val name: String, var values: Array<Nutrient>) {
    var gramCountForEachNutrient: Short = 100
    var price = 0f
    var selfNutritionDataURL = ""

    constructor(name: String, values: Array<Nutrient>, price: Float, gramCount: Short, selfNutritionDataURL: String) : this(name, values) {
        this.price = price
        gramCountForEachNutrient = gramCount
        this.selfNutritionDataURL = selfNutritionDataURL
    }
}

data class Nutrient(var nutrient: Nutrients, var ammount: Float, var unit: Unit)

enum class Unit (fullName: String) {
    g("Grams"),
    mg("Milligrams"),
    mcg("Micrograms"),
    IU("International Unit")
}

