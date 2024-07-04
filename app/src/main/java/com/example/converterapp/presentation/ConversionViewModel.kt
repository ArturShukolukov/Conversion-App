package com.example.converterapp.presentation

import androidx.lifecycle.ViewModel
import com.example.converterapp.model.Conversion
import com.example.converterapp.repository.ConversionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConversionViewModel(private val conversionRepository: ConversionRepository) : ViewModel() {
    private val _result = MutableStateFlow<Double?>(null)
    val result: StateFlow<Double?> get() = _result

    private val _conversions = MutableStateFlow<List<Conversion>>(emptyList())
    val conversions: StateFlow<List<Conversion>> get() = _conversions

    fun convert(value: Double, conversion: Conversion) {
        _result.value = value * conversion.factor
    }

    fun updateConversions(type: String) {
        _conversions.value = conversionRepository.getConversionsForType(type)
    }
}
