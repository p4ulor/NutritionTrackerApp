package paulor.nutritiontrackerkotlin.model

// default dailyValues are set according to: https://www.fda.gov/food/new-nutrition-facts-label/daily-value-new-nutrition-and-supplement-facts-labels
// also for reference: https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/618167/government_dietary_recommendations.pdf
// and https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/743790/Dietary_Reference_Values_-_A_Guide__1991_.pdf

// to convert micro grams to miligrams -> https://www.thecalculatorsite.com/conversions/common/mcg-to-mg.php value*0.001,
// to convert IU to mg https://mypharmatools.com/othertools/iu

enum class Compound(val fullName: String, val group: Group, var dailyValue: Float, var unit: EdibleUnit) {
    //5#, macros are all in g(grams)
    CAL("Calories", Group.C, 2000f, EdibleUnit.U),
    CARBS("Carbs", Group.A, 275f, EdibleUnit.G),
    SUG("Sugar", Group.A,50f, EdibleUnit.G),
    PRO("Protein", Group.A, 50f, EdibleUnit.G),
    FAT("Fat", Group.A, 78f, EdibleUnit.G),
    FIB("Fiber", Group.A, 28f, EdibleUnit.G),

    //11# vitamins / B's Micros. All in mg(milligrams)
    //https://en.wikipedia.org/wiki/Vitamin#List
    //https://en.wikipedia.org/wiki/B_vitamins               Alternative nutrient names(according to google and nutritiondata.self.com):
    VA("Vitamin A", Group.V, 0.9f, EdibleUnit.IU),        //Retinol, retinal, retinoic acid, beta-carotene
    VC("Vitamin C", Group.V, 0.9f, EdibleUnit.MG),        //Ascorbic acid and ascorbate
    VE("Vitamin E", Group.V, 15f, EdibleUnit.MG),         //alpha tocopherol, alpha-tocopherol, tocotrienol
    VK("Vitamin K", Group.V, 0.12f, EdibleUnit.MCG),       //antihemorrhagic factor, menadiol, menadione (vitamin K-3), menaquinone (vitamin K-2), methylphytyl naphthoquinone, phylloquinone (vitamin K-1), phytonadione
    V1("Vitamin B1", Group.V, 1.2f, EdibleUnit.MG),       //Thiamin
    V2("Vitamin B2", Group.V, 1.3f, EdibleUnit.MG),       //Riboflavin
    V3("Vitamin B3", Group.V, 16f, EdibleUnit.MG),        //Niacin
 // V4                                                                        Choline -> there's always trace amounts in everything / easy to take the %DV /  un-established %DV / kinda irrelevant https://iubmb.onlinelibrary.wiley.com/doi/pdf/10.1080/15216540500078939
    V5("Vitamin B5", Group.V, 5f, EdibleUnit.MG),        //Pantothenic Acid
    V6("Vitamin B6", Group.V, 1.7f, EdibleUnit.MG),      //Pyridoxine

 // V7 / V8 / Vitamin H                                       Biotin
    V9("Vitamin B9", Group.V, 0.4f, EdibleUnit.MCG),      //Folate (Folic Acid)
    // V10 Para-aminobenzoic acid (PABA) https://www.healthline.com/nutrition/vitamin-b-10#what-it-is
    // V11 similar to B9 https://rxharun.com/vitamin-b11-deficiency-symptoms-food-source-health-benefit/
    V12("Vitamin B12", Group.V, 0.0024f, EdibleUnit.MCG), //Cobalamin
    // Betaine, functions very closely with choline, folic acid, vitamin B12 -> there's always trace amounts in everything / easy to take the %DV /  un-established %DV / kinda irrelevant

    //10# minerals
    CALC("Calcium", Group.M, 700f, EdibleUnit.MG),      // https://nutritionfacts.org/topics/calcium/     https://bmcmedicine.biomedcentral.com/articles/10.1186/s12916-020-01815-3#:~:text=sufficient%20dietary%20calcium%20(%E2%89%A5%E2%80%89700%E2%80%89mg/day)
    IR("Iron", Group.M, 18f, EdibleUnit.MG),
    MAG("Magnesium", Group.M, 420f, EdibleUnit.MG),
    PH("Phosphorus", Group.M, 1250f, EdibleUnit.MG),
    POT("Potassium", Group.M, 4700f, EdibleUnit.MG),
    SOD("Sodium", Group.M, 2300f, EdibleUnit.MG),
    ZI("Zinc", Group.M, 11f, EdibleUnit.MG),
    COP("Copper", Group.M, 1f, EdibleUnit.MG),
    MAN("Manganese", Group.M, 2.3f, EdibleUnit.MG),
    SEL("Selenium", Group.M, 0.055f, EdibleUnit.MCG),
    //Fluoride -> there's always tiny trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    //PUFA's(Polyunsaturated fatty acids) https://www.healthline.com/nutrition/how-much-omega-3#general-guidelines
    //the daily value of these nutrients may be subjective
    O3("Omega 3", Group.F, 1600f, EdibleUnit.MG),  //alpha-linolenic acid 3.8xOmega6 radio
    O6("Omega 6", Group.F, 420f, EdibleUnit.MG); //linoleic acid

    companion object {
        val size = values().size
        fun getCompound(string: String) : Compound? {
            values().forEach {
                if(it.name==string) return it
            }
            return null
        }
    }

    enum class Group(val fullName: String) {
        A("Macro"),
        V("Vitamin"),
        M("Mineral"),
        F("Fatty acid"),
        C("Calories")
    }
}

