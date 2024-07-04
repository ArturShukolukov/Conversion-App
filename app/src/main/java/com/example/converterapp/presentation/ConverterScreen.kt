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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.converterapp.domain.ConversionRepositoryImpl
import com.example.converterapp.model.Conversion
import com.example.converterapp.ui.theme.ConverterAppTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ConversionScreen(viewModel: ConversionViewModel) {
    val result by viewModel.result.collectAsState()
    val conversions by viewModel.conversions.collectAsState()

    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
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

        // Dropdown and input field for the first unit
        ConversionInputField(
            value = input,
            onValueChange = { input = it },
            label = "Input Value",
            conversions = conversions,
            selectedConversion = selectedConversion,
            onConversionChange = { selectedConversion = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown and input field for the second unit
        ConversionInputField(
            value = output,
            onValueChange = { output = it },
            label = "Output Value",
            conversions = conversions,
            selectedConversion = selectedConversion,
            onConversionChange = { selectedConversion = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Numeric keypad for input
        NumericKeypad(
            onNumberClick = { number ->
                if (input.isEmpty() || input == "0") {
                    input = number.toString()
                } else {
                    input += number.toString()
                }
                selectedConversion?.let {
                    viewModel.convert(input.toDoubleOrNull() ?: 0.0, it)
                }
            },
            onClearClick = {
                input = ""
                viewModel.clearResult()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display result
        Text(
            text = "Result: ${result ?: "N/A"}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun ConversionInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    conversions: List<Conversion>,
    selectedConversion: Conversion?,
    onConversionChange: (Conversion) -> Unit
) {
    Column {
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dropdown menu for unit selection
            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedConversion?.let { "${it.fromUnit} to ${it.toUnit}" } ?: "Select Conversion")
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    conversions.forEach { conversion ->
                        DropdownMenuItem(onClick = {
                            onConversionChange(conversion)
                            expanded = false
                        }, text = { Text("${conversion.fromUnit} to ${conversion.toUnit}") })
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // TextField for input value
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                readOnly = true, // Make the TextField read-only to disable the system keyboard
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun NumericKeypad(
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit
) {
    val numbers = (1..9).toList() + listOf(0)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(numbers.size) { index ->
            Button(
                onClick = { onNumberClick(numbers[index]) },
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
            ) {
                Text(numbers[index].toString())
            }
        }
        // Clear button
        item {
            Button(
                onClick = onClearClick,
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
            ) {
                Text("C")
            }
        }
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

