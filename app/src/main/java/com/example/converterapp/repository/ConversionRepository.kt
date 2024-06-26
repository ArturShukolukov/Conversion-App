package com.example.converterapp.repository

interface  ConversionRepository {
    fun getConversionFactor(fromUnit: String, toUnit: String): Double
}