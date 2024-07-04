package com.example.converterapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
    val conversions by viewModel.conversions.collectAsState()

    var input by remember { mutableStateOf("") }
    var selectedConversion by remember { mutableStateOf<Conversion?>(null) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Area", "Length", "Temperature", "Volume", "Mass")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // TabRow for selecting conversion type
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        viewModel.updateConversions(title)
                    },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown for selecting specific conversion
        var expanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { expanded = true }) {
                Text(selectedConversion?.let { "${it.fromUnit} to ${it.toUnit}" } ?: "Select Conversion")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                conversions.forEach { conversion ->
                    DropdownMenuItem(onClick = {
                        selectedConversion = conversion
                        expanded = false
                    }, text = { Text("${conversion.fromUnit} to ${conversion.toUnit}") })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Input Value") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedConversion?.let {
                    viewModel.convert(input.toDoubleOrNull() ?: 0.0, it)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Result: ${result ?: "N/A"}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
fun ConversionScreenPreview() {
    ConverterAppTheme {
        val conversionRepository = ConversionRepositoryImpl()
        val viewModel = ConversionViewModel(conversionRepository)
        ConversionScreen(viewModel)
    }
}

