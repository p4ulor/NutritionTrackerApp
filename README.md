# NutritionTrackerApp
Nutrition tracker android app for macros and micros

* The program works offline(no internet connection need)
* It's like a MyFitnessPal, but it requires you to config your data more manually before actually using it(that is, before you can calculate your macros and micros for the day or the macros and micros of a meal per example, you're supposed to have some data to work with). By data I mean your individual foods and meals. You can edit these by directly accessing the foods and meals data files or by calling methods to change that data.
* You basically have the information(names, macro and micros) of your foods and meals in a .txt file. And per example, if you want to calculate what macros you hit in a certain meal or day, you select what foods you ate and their ammounts in grams, and this app will calculate the macros and micros for each nutrients and the % of the DV(Daily Value, AKA Reference Daily Intake). It will also let you add foods, or edit the macros or micros of a food, delete it from the .txt file, make a meal based on a group of foods, etc.

The Console folder contains the 2 project files:
- NutritionTracker (java version)
- NutritionTrackerKotlin

That are designed to deploy the program into an Android app (and should be open using [Android Studio](https://developer.android.com/studio))


I also made this project because I wanted to have a tool like MyFitnessPal, but that doesnt require internet connection and that doesn't have so many restrictions on the foods and nutritional values of the foods that you can track. I wanted to create something more pratical.



Enable assertions like [this](https://se-education.org/guides/tutorials/intellijUsefulSettings.html)