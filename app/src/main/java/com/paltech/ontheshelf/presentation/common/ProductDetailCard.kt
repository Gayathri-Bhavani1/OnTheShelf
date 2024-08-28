package com.paltech.ontheshelf.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.presentation.basket.viewmodel.BasketViewModel
import com.paltech.ontheshelf.presentation.home.viewModel.ShopViewModel
import com.paltech.ontheshelf.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    basketViewModel: BasketViewModel = hiltViewModel(),
    productId: Int,
    navController: NavController,
) {
    val viewModel: ShopViewModel = hiltViewModel()
    val productState by viewModel.productsOnSale.collectAsState()

    val product = productState.find { it.id == productId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
        ) {
            product?.let { product ->
                // Product Image
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center // Center the image within the Box
                ) {
                    Image(
                        painter = painterResource(id = product.imageResId),
                        contentDescription = null,
                        contentScale = ContentScale.Fit // Ensure the image fits within the defined size
                    )
                }

                // Product Details
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.Red,
                                    shape = RoundedCornerShape(10.dp),
                                )
                        ) {
                            Text(
                                text = "${product.discount}% OFF",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 5.dp, horizontal = 12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$${"%.2f".format(product.price)} / 1pc",
                        fontSize = 16.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.description,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Quantity Selector
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${product.quantity}pcs x $${"%.2f".format(product.price)}",
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp
                        )
                        Box(
                            modifier = Modifier
                                .border(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.Black,
                                    width = 0.8.dp
                                )
                        ) {
                            Row {
                                IconButton(onClick = {
                                    if (product.quantity > 1) {
                                        viewModel.updateProductQuantity(productId, product.quantity - 1)
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_remove_24),
                                        contentDescription = "Decrease",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    text = "${product.quantity}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                IconButton(onClick = {
                                    viewModel.updateProductQuantity(productId, product.quantity + 1)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "$${"%.2f".format(product.quantity * product.price)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OnShelfButton(
                        onClick = {
                            basketViewModel.addItem(
                                product.name,
                                product.imageResId,
                                product.price,
                                product.quantity
                            )
                            navController.navigate(Route.BasketScreen.route)
                        },
                        color = colorResource(id = R.color.green),
                        modifier = Modifier.fillMaxWidth(),
                        text = "Add to basket"
                    )
                }
            } ?: run {
                // Show a loading indicator or empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
