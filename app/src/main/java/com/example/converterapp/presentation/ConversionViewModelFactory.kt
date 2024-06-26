package com.example.converterapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.converterapp.repository.ConversionRepository

//class ConversionViewModelFactory(private val conversionRepository: ConversionRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ConversionViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ConversionViewModel(conversionRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}