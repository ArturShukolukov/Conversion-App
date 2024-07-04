package com.example.converterapp.repository

import com.example.converterapp.model.Conversion

interface ConversionRepository {
    fun getConversionFactor(fromUnit: String, toUnit: String): Double
    fun getConversionsForType(type: String): List<Conversion>
}