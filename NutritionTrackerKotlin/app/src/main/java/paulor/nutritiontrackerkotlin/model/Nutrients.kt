package paulor.nutritiontrackerkotlin.model

// default dailyValues are set according to: https://www.fda.gov/food/new-nutrition-facts-label/daily-value-new-nutrition-and-supplement-facts-labels

enum class Nutrients(
    val fullName: String,
    val group: Group,
    val dailyValue: Float
) {

    //5#, macros are all in g(grams)
    CAL("Calories", Group.A, 2000f),
    CARBS("Carbs", Group.A, 275f),
    PRO("Protein", Group.A, 50f),
    FAT("Fat", Group.A, 78f),
    FIB("Fiber", Group.A, 28f),

    //Micros are all in mg(milligrams), to convert micro grams to miligrams -> https://www.thecalculatorsite.com/conversions/common/mcg-to-mg.php value*0.001, IU to mg https://mypharmatools.com/othertools/iu
    //11# vitamins
    //https://en.wikipedia.org/wiki/Vitamin#List
    //https://en.wikipedia.org/wiki/B_vitamins             Alternative nutrient names(acording to google and nutritiondata.self.com):
    VA("Vitamin A", Group.V, 0.9f),   //Retinol, retinal, retinoic acid, beta-carotene
    VC("Vitamin C", Group.V, 0.9f),   //Ascorbic acid and ascorbate
    VE("Vitamin E", Group.V, 15f),    //alpha tocopherol, alpha-tocopherol, tocotrienol
    VK("Vitamin K", Group.V, 0.12f),  //antihemorrhagic factor, menadiol, menadione (vitamin K-3), menaquinone (vitamin K-2), methylphytyl naphthoquinone, phylloquinone (vitamin K-1), phytonadione
    V1("Vitamin B1", Group.V, 1.2f),  //Thiamin
    V2("Vitamin B2", Group.V, 1.3f),  //Riboflavin
    V3("Vitamin B3", Group.V, 16f),   //Niacin
    V5("Vitamin B5", Group.V, 5f),    //Pantothenic Acid
    V6("Vitamin B6", Group.V, 1.7f),  //Pyridoxine

    //  V7                                                       Biotin
    V9("Vitamin B9", Group.V, 0.4f),  //Folate (Folic Acid)
    V12("Vitamin B12", Group.V, 0.0024f),  //Cobalamin
    //Choline -> there's always trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant
    //Betaine -> there's always trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    //10# minerals
    CALC("Calcium", Group.M, 1300f),  //subjective and likely inflated by the dairy industry
    IR("Iron", Group.M, 18f),
    MAG("Magnesium", Group.M, 420f),
    PH("Phosphorus", Group.M, 1250f),
    POT("Potassium", Group.M, 4700f),
    SOD("Sodium", Group.M, 2300f),
    ZI("Zinc", Group.M, 11f),
    COP("Copper", Group.M, 1f),
    MAN("Manganese", Group.M, 2.3f),
    SEL("Selenium", Group.M, 0.055f),  //Fluoride -> there's always tiny trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    //PUFA's(Polyunsaturated fatty acids) https://www.healthline.com/nutrition/how-much-omega-3#general-guidelines
    //the daily value of these nutrients may be subjective
    O3("Omega 3", Group.F, 1600f),  //alpha-linolenic acid 3.8xOmega6 radio
    O6("Omega 6", Group.F, 420f); //linoleic acid

    val getAll = this.javaClass.enumConstants

    companion object {
        val ammountOfNutrients = values().size
    }

    enum class Group(val group: String) {
        A("Macro"),
        V("Vitamin"),
        M("Mineral"),
        F("Fatty acid")
    }
}

