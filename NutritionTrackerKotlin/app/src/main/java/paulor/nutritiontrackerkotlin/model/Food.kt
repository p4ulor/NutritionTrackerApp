package paulor.nutritiontrackerkotlin.model

class Food (val name: String, var nutrients: Array<Nutrients?>, var values: FloatArray){
    init {
        //check properties.length==vals?
    }

    fun sortNutrients() {
        val nu = Nutrients.values() //correct order
        val orderedNutrients = arrayOfNulls<Nutrients>(nutrients.size)
        val orderedVals = FloatArray(values.size)
        var index = 0
        for (i in nu.indices) { //query already in correct order
            for (j in nutrients.indices) { //get existing nutrient in already correct order
                if (nutrients[i] == null) continue
                if (nu[j].name.equals(nutrients[i]!!.name, ignoreCase = true)) { //if exists
                    orderedNutrients[index] = nutrients[j]
                    orderedVals[index] = values[j]
                    index++
                    break
                }
            }
        }
        nutrients = orderedNutrients //ehh, let the GC (garbage colector) do its job lol https://stackoverflow.com/questions/55567935/how-to-delete-an-array-properly-in-java/55568220
        values = orderedVals //https://stackoverflow.com/questions/15448457/deleting-an-entire-array
    }
}