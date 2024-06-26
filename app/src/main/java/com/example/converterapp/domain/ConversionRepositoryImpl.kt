package com.example.converterapp.domain

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
}