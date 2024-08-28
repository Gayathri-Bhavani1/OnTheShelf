package com.paltech.ontheshelf.presentation.Vegetables


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.di.AppModule
import com.paltech.ontheshelf.presentation.home.Product

@Composable
fun ProductScreen(viewModel: ProductViewModel = hiltViewModel()) {
    val products = viewModel.products

    Column(modifier = Modifier.padding(16.dp)

        ) {
        Text(text = "Vegetables", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(product = product, onFavoriteClick = { viewModel.toggleFavorite(it) })
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onFavoriteClick: (Product) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier.padding(8.dp), // Reduced padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row with Favorite Icon and Discount Container
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Favorite Icon
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_24), // Replace with your favorite icon resource
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .size(40.dp) // Smaller icon size
                            .clickable {
                                product.isFavorite.value =
                                    !product.isFavorite.value // Update the value
                            } // Toggle favorite state
                            .padding(4.dp),
                        tint = if (product.isFavorite.value) Color(0xFFE91E63) else Color.LightGray // Pink when favorite, light gray otherwise
                    )


//                        Box(
//                            modifier = Modifier
//                                .background(Color.Red, shape = RoundedCornerShape(10.dp))
//                                .padding(6.dp) // Reduced padding
//                        ) {
//                            Text(
//                                text = "-10%",
//                                color = Color.White,
//                                style = TextStyle(fontSize = 12.sp) // Smaller font size
//                            )
//
//                    }
                }

                // Image
                Spacer(modifier = Modifier.height(6.dp)) // Reduced space
                Image(
                    painter = painterResource(id = product.imageResId), // Replace with your image resource
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(100.dp) // Smaller image size
                        .padding(4.dp) // Reduced padding
                )

                // Fruit Name
                Text(
                    text = product.name,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                )

                // Price
                Row {
                    Text(
                        text = product.price.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray) // Smaller font size
                    )
                    Text(
                        text = product.discount.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.Red) // Smaller font size
                    )
                }

                // Add to Cart Button
                Spacer(modifier = Modifier.height(4.dp)) // Reduced space
                Button(
                    onClick = { /* Handle add to cart action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff069750)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                ) {
                    Text(text = "Add", color = Color.White) // Shortened button text
                }
            }
    }
}}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    val viewModel = ProductViewModel(AppModule.provideProductRepository())
    ProductScreen(viewModel)
}

