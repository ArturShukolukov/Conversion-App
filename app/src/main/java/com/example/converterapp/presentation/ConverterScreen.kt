package com.example.converterapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.converterapp.domain.ConversionRepositoryImpl
import com.example.converterapp.model.Conversion
import com.example.converterapp.ui.theme.ConverterAppTheme

@Composable
fun ConversionScreen(viewModel: ConversionViewModel) {
    val result by viewModel.result.collectAsState()

    var input by remember { mutableStateOf("") }
    var selectedConversion by remember { mutableStateOf<Conversion?>(null) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Input Value") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Dropdown for unit selection (to be implemented)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            selectedConversion?.let {
                viewModel.convert(input.toDoubleOrNull() ?: 0.0, it)
            }
        }) {
            Text("Convert")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Result: ${result ?: "N/A"}")
    }
}

@Preview
@Composable
fun ConversionScreenPreview(){
    ConverterAppTheme {
        val conversionRepository = ConversionRepositoryImpl()
        val viewModel = ConversionViewModel(conversionRepository)
//            ViewModelProvider(this, ConversionViewModelFactory(conversionRepository))
//            .get(ConversionViewModel::class.java)
        ConversionScreen(viewModel)
    }
}
