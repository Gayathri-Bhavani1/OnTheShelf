package com.paltech.ontheshelf.presentation.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay


@Composable
fun OrderStepperScreen(
    onComplete: () -> Unit, // Change here from @Composable () -> Unit to () -> Unit
    navController: NavHostController
) {
    val steps = listOf("Order placed", "Processing", "Delivery", "Delivered")
    var currentStep by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (currentStep < steps.size) {
            delay(1000) // Move to next step every second
            currentStep++
        }
        onComplete() // Callback when all steps are completed
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Order #2294 details", style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        steps.forEachIndexed { index, step ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = if (index <= currentStep) Icons.Default.CheckCircle else Icons.Default.Check,
                    contentDescription = null,
                    tint = if (index <= currentStep) Color.Blue else Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (index <= currentStep) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                    if (index <= currentStep) {
                        Text(
                            text = "Your order is ${step.lowercase()}.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )
                    }
                }
            }
            if (index < steps.size - 1) {
                Divider(
                    modifier = Modifier.padding(start = 16.dp),
                    color = Color.Gray,
                    thickness = 1.dp
                )
            }
        }
    }
}
