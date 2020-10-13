package apptentive.com.states.models

import com.google.gson.GsonBuilder

data class StateInfo(
    val name: String,
    val abbreviation: String,
    val capital: String,
    val largest_city: String,
    val established_date: String,
    val population: Int,
    val total_area_km2: Int,
    val land_area_km2: Int,
    val water_area_km2: Int,
    val number_of_reps: Int
) {
    override fun toString(): String {
        val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
        return gson.toJson(this).run {
            replace("{" , "")
                .replace("}", "")
                .replace(",", "")
                .replace("\"", " ")
                .replace("largest_city", "largest city")
                .replace("established_date","establish date")
                .replace("total_area_km2", "total area(square km)")
                .replace("land_area_km2", "land area(square km)")
                .replace("water_area_km2", "water area(square km)")
                .replace("number_of_reps", "numbers of reps")
        }

    }
}
