package com.example.converterapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.converterapp.domain.ConversionRepositoryImpl
import com.example.converterapp.presentation.ConversionScreen
import com.example.converterapp.presentation.ConversionViewModel
import com.example.converterapp.ui.theme.ConverterAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConverterAppTheme {
                Scaffold(modifier = Modifier.
                    fillMaxSize()
                    .systemBarsPadding()) {
                    val conversionRepository = ConversionRepositoryImpl()
                    val viewModel = ConversionViewModel(conversionRepository)
                    ConversionScreen(viewModel = viewModel)
                }
            }
        }
    }
}
