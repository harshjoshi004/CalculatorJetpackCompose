package com.example.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyUI(vm:CalculatorViewModel, innerPadding:PaddingValues){
    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = vm.equation.value, fontSize = 24.sp, maxLines = 5,modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = vm.result.value, fontSize = 30.sp, maxLines = 2,modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(8.dp))

        LazyVerticalGrid(
            modifier = Modifier.padding(8.dp),
            columns = GridCells.Fixed(4)
        ) {
            items(vm.buttonList) { symbol->
                if(symbol.toIntOrNull() != null)
                    OutlinedButton(modifier = Modifier.padding(8.dp), onClick = { vm.click(symbol) }) {
                        Text(text = symbol, fontSize = 35.sp)
                    }
                else if(symbol == "=" || symbol == "A" || symbol == "C")
                    Button(modifier = Modifier.padding(8.dp), onClick = { vm.click(symbol) }) {
                        Text(text = symbol, fontSize = 35.sp)
                    }
                else
                    ElevatedButton(modifier = Modifier.padding(8.dp), onClick = { vm.click(symbol) }) {
                        Text(text = symbol, fontSize = 35.sp)
                    }
            }
        }
    }
}
