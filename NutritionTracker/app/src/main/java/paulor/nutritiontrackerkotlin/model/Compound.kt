package paulor.nutritiontrackerkotlin.model

// default dailyValues are set according to: https://www.fda.gov/food/new-nutrition-facts-label/daily-value-new-nutrition-and-supplement-facts-labels
// also for reference: https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/618167/government_dietary_recommendations.pdf
// and https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/743790/Dietary_Reference_Values_-_A_Guide__1991_.pdf
// https://www.rapidtables.com/convert/weight/index.html
// to convert micro grams to miligrams -> https://www.thecalculatorsite.com/conversions/common/mcg-to-mg.php value*0.001,
// to convert IU to mg https://mypharmatools.com/othertools/iu

enum class CompoundsRDAProfile(val compound: Compound, var dailyValueAmount: Float){ //this enum contains the Recommended Daily Amount for each Compound(Nutrient). Unfortunetely, this design makes it hard to quickly view  whats the unit of measure being used for the indicated amount
    CAL(Compound.CAL, 2000f),
    CARBS(Compound.CARBS, 275f),
    SUG(Compound.SUG, 50f),
    PRO(Compound.PRO, 50f),
    FAT(Compound.FAT, 78f),
    FIB(Compound.FIB, 28f),

    VA(Compound.VA, 0.9f),
    VC(Compound.VC, 0.9f),
    VE(Compound.VE, 15f),
    VK(Compound.VK, 0.12f),
    V1(Compound.V1, 1.2f),
    V2(Compound.V2, 1.3f),
    V3(Compound.V3, 16f),
    V5(Compound.V5, 5f),
    V6(Compound.V6, 1.7f),
    V9(Compound.V9, 0.4f),
    V12(Compound.V12, 0.0024f),

    CALC(Compound.CALC, 700f),
    IR(Compound.IR, 18f),
    MAG(Compound.MAG, 420f),
    PH(Compound.PH, 1250f),
    POT(Compound.POT, 4700f),
    SOD(Compound.SOD, 2300f),
    ZI(Compound.ZI, 11f),
    COP(Compound.COP, 1f),
    MAN(Compound.MAN, 2.3f),
    SEL(Compound.SEL, 0.055f),

    O3(Compound.O3, 1600f),
    O6(Compound.O6, 420f)
}

enum class Compound(val fullName: String, val group: Group, val unit: CompoundUnit) {
    //5#, macros are all in g(grams)
    CAL("Calories", Group.C, CompoundUnit.U),
    CARBS("Carbs", Group.A, CompoundUnit.G),
    SUG("Sugar", Group.A, CompoundUnit.G),
    PRO("Protein", Group.A, CompoundUnit.G),
    FAT("Fat", Group.A, CompoundUnit.G),
    FIB("Fiber", Group.A, CompoundUnit.G),

    //11# vitamins / B's Micros. All in mg(milligrams)
    //https://en.wikipedia.org/wiki/Vitamin#List
    //https://en.wikipedia.org/wiki/B_vitamins               Alternative nutrient names(according to google and nutritiondata.self.com):
    VA("Vitamin A", Group.V, CompoundUnit.IU),        //Retinol, retinal, retinoic acid, beta-carotene
    VC("Vitamin C", Group.V, CompoundUnit.MG),        //Ascorbic acid and ascorbate
    VE("Vitamin E", Group.V, CompoundUnit.MG),         //alpha tocopherol, alpha-tocopherol, tocotrienol
    VK("Vitamin K", Group.V, CompoundUnit.MCG),       //antihemorrhagic factor, menadiol, menadione (vitamin K-3), menaquinone (vitamin K-2), methylphytyl naphthoquinone, phylloquinone (vitamin K-1), phytonadione
    V1("Vitamin B1", Group.V, CompoundUnit.MG),       //Thiamin
    V2("Vitamin B2", Group.V, CompoundUnit.MG),       //Riboflavin
    V3("Vitamin B3", Group.V, CompoundUnit.MG),        //Niacin
 // V4                                                                        Choline -> there's always trace amounts in everything / easy to take the %DV /  un-established %DV / kinda irrelevant https://iubmb.onlinelibrary.wiley.com/doi/pdf/10.1080/15216540500078939
    V5("Vitamin B5", Group.V, CompoundUnit.MG),        //Pantothenic Acid
    V6("Vitamin B6", Group.V, CompoundUnit.MG),      //Pyridoxine

 // V7 / V8 / Vitamin H                                       Biotin
    V9("Vitamin B9", Group.V, CompoundUnit.MCG),      //Folate (Folic Acid)
    // V10 Para-aminobenzoic acid (PABA) https://www.healthline.com/nutrition/vitamin-b-10#what-it-is
    // V11 similar to B9 https://rxharun.com/vitamin-b11-deficiency-symptoms-food-source-health-benefit/
    V12("Vitamin B12", Group.V, CompoundUnit.MCG), //Cobalamin
    // Betaine, functions very closely with choline, folic acid, vitamin B12 -> there's always trace amounts in everything / easy to take the %DV /  un-established %DV / kinda irrelevant

    //10# minerals
    CALC("Calcium", Group.M, CompoundUnit.MG),      // https://nutritionfacts.org/topics/calcium/     https://bmcmedicine.biomedcentral.com/articles/10.1186/s12916-020-01815-3#:~:text=sufficient%20dietary%20calcium%20(%E2%89%A5%E2%80%89700%E2%80%89mg/day)
    IR("Iron", Group.M, CompoundUnit.MG),
    MAG("Magnesium", Group.M, CompoundUnit.MG),
    PH("Phosphorus", Group.M, CompoundUnit.MG),
    POT("Potassium", Group.M, CompoundUnit.MG),
    SOD("Sodium", Group.M, CompoundUnit.MG),
    ZI("Zinc", Group.M, CompoundUnit.MG),
    COP("Copper", Group.M, CompoundUnit.MG),
    MAN("Manganese", Group.M, CompoundUnit.MG),
    SEL("Selenium", Group.M, CompoundUnit.MCG),
    //Fluoride -> there's always tiny trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    //PUFA's(Polyunsaturated fatty acids) https://www.healthline.com/nutrition/how-much-omega-3#general-guidelines
    //the daily value of these nutrients may be subjective
    O3("Omega 3", Group.F, CompoundUnit.MG),  //alpha-linolenic acid 3.8xOmega6 radio
    O6("Omega 6", Group.F, CompoundUnit.MG); //linoleic acid

    companion object {
        val size = values().size
        fun getCompound(string: String) : Compound? {
            values().forEach {
                if(it.fullName==string) return it
            }
            return null
        }

        fun getAllAsStringArray() : ArrayList<String> {
            val list = ArrayList<String>()
            values().forEach {
                list.add(it.fullName)
            }
            return list
        }

        fun getAsStringArrayExcept(except: ArrayList<Nutrient>) : ArrayList<String> {
            val list = ArrayList<String>()
            val compounds = Nutrient.getCompounds(except)
            values().forEach {
                if(!compounds.contains(it)){
                    list.add(it.fullName)
                }
            }
            return list
        }
    }

    enum class Group(val fullName: String) {
        A("Macro"),
        V("Vitamin"),
        M("Mineral"),
        F("Fatty acid"),
        C("Calories")
    }

    enum class CompoundUnit(val fullName: String)  {
        U("Unit"),
        G("Grams"),
        MG("Milligrams"),
        MCG("Micrograms"),
        IU("International Unit")
    }
}

