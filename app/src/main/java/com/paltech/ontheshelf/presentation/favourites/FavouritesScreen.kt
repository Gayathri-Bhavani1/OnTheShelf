package com.paltech.ontheshelf.presentation.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paltech.ontheshelf.data.localdb.ProductEntity
import com.paltech.ontheshelf.presentation.basket.viewmodel.BasketViewModel
import com.paltech.ontheshelf.presentation.bottomnav.viewmodel.BottomNavViewModel
import com.paltech.ontheshelf.presentation.home.Product
import com.paltech.ontheshelf.presentation.home.ProductCard
import com.paltech.ontheshelf.presentation.home.viewModel.ShopViewModel
@Composable
fun FavouriteScreen(viewModel: ShopViewModel = hiltViewModel(),
                    basketViewModel: BasketViewModel = hiltViewModel(),
                    bottomNavViewModel: BottomNavViewModel = hiltViewModel(),
                    navController: NavHostController) {
    val favoriteProductIds by viewModel.favorites.collectAsState()
    val favoriteProducts = remember { mutableStateOf<List<ProductEntity>>(emptyList()) }

    LaunchedEffect(favoriteProductIds) {
        viewModel.getProductsByIds(favoriteProductIds) { products ->
            favoriteProducts.value = products
        }
    }
    Column {
        Text(text = "Favourites",    style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
if (favoriteProducts.value.isEmpty()){

    Text(text = "Your Favourites is empty..", modifier = Modifier.padding(10.dp))
}else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            content = {
                items(favoriteProducts.value) { productEntity ->
                    val product = Product(
                        id = productEntity.id,
                        name = productEntity.name,
                        price = productEntity.price,
                        discount = productEntity.discount,
                        imageResId = productEntity.imageResId,
                        quantity = productEntity.quantity,
                        isFavorite = remember { mutableStateOf(productEntity.isFavorite) },
                        description = productEntity.description
                    )

                    ProductCard(
                        basketViewModel = basketViewModel,
                        product = product, isOrder = false,
                        onFavoriteClick = {
                            viewModel.toggleFavorite(product)

                        },
                        bottomNavViewModel = bottomNavViewModel,
                       navController =  navController,

                    )

                }
            }
        )
    }}
}
