package com.example.converterapp.domain

import com.example.converterapp.model.Conversion
import com.example.converterapp.repository.ConversionRepository



class ConversionRepositoryImpl : ConversionRepository {
    private val conversionFactors = mapOf(
        "KilometersToMiles" to 0.621371,
        "MilesToKilometers" to 1.60934,
        "PoundsToKilograms" to 0.453592,
        "KilogramsToPounds" to 2.20462,
        "MetersPerSecondToKilometersPerHour" to 3.6
    )

    override fun getConversionFactor(fromUnit: String, toUnit: String): Double {
        val key = fromUnit + "To" + toUnit
        return conversionFactors[key] ?: throw IllegalArgumentException("Conversion factor not found for $fromUnit to $toUnit")
    }

    override fun getConversionsForType(type: String): List<Conversion> {
        return when (type) {
            "Area" -> listOf(
                Conversion("Acres", "Square metres", 4046.8564224),
                Conversion("Square metres", "Acres", 0.0002471054)
            )
            "Length" -> listOf(
                Conversion("Kilometers", "Miles", 0.621371),
                Conversion("Miles", "Kilometers", 1.60934)
            )
            "Temperature" -> listOf(
                Conversion("Celsius", "Fahrenheit", 1.8),
                Conversion("Fahrenheit", "Celsius", 0.5556)
            )
            "Volume" -> listOf(
                Conversion("Liters", "Gallons", 0.264172),
                Conversion("Gallons", "Liters", 3.78541)
            )
            "Mass" -> listOf(
                Conversion("Kilograms", "Pounds", 2.20462),
                Conversion("Pounds", "Kilograms", 0.453592)
            )
            else -> emptyList()
        }
    }
}
