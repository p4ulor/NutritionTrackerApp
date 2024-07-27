# NutritionTrackerApp
Nutrition tracker android app. Similar and inspired by [MyFitnessPal](https://www.myfitnesspal.com/) and [Cronometer (very good)](https://cronometer.com/). See a cronometer [app demo](https://www.youtube.com/watch?v=9MKGDlWQO7s). My app aims to be simple and complete. It gives you the most ammount of freedom to track, store and costumize what you want and how you want.

## Code shortcuts
- [src](NutritionTracker/app/src/main/java/paulor/nutritiontrackerkotlin)
- [res](NutritionTracker/app/src/main/res)
- [layout](NutritionTracker/app/src/main/res/layout/)

## Nutrition data websites
- https://nutritiondata.self.com/ (the one I will use first)
- https://www.nutritionvalue.org/

## Main objects
So that you can understand what you can do
## Nutrient
### Compound
It's an enum class with a limited number of objects. Like: calories, protein, zinc, etc
### Nutrient
- **Compound**
- amount

### Food
A food has:
- name (unique)
- list of **Nutrient**
- price 
- amount (used for making meals and on calculation total nutritional values)
- unit (unit, grams, milligrams, etc)
- comment
- selfNutritionDataURL

### Meal
- name (unique)
- list of **Food**
- price (defined according to the list of foods price's and grams, or it can be manually edited)
- amount (used for making meals and on calculation total nutritional values)
- unit (unit, grams, milligrams, etc)
- comment

## Track
- date (unique)
- list of **Foods**
- list of **Meals**
- price (defined according to the food's price and grams and the price of each meals)
- comment

### Note
- The program works offline (no internet connection needed) except if you want to automatically get nutrient data for a food you want to add, using the website *SelfNutritionData* with a link like [this](https://nutritiondata.self.com/facts/nut-and-seed-products/3086/2)a
- The data is stored in the phone's local DB for the app.

Open using [Android Studio](https://developer.android.com/studio))
