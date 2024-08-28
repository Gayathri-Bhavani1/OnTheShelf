package com.paltech.ontheshelf.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.presentation.basket.viewmodel.BasketViewModel
import com.paltech.ontheshelf.presentation.bottomnav.viewmodel.BottomNavViewModel
import com.paltech.ontheshelf.presentation.common.OnShelfButton
import com.paltech.ontheshelf.presentation.home.viewModel.ShopViewModel
import com.paltech.ontheshelf.presentation.navigation.Route

@Composable
fun ShopScreen(
    viewModel: ShopViewModel = hiltViewModel(),
    navController: NavController,
    basketViewModel: BasketViewModel = hiltViewModel(),
    bottomNavViewModel: BottomNavViewModel = hiltViewModel()
) {
    val productsOnSale by viewModel.productsOnSale.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val orderAgainProducts by viewModel.orderAgainProducts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {

        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.splash_icon),
                contentDescription = "Icon",
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "OnTheShelf.",
                style = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Spacer(modifier = Modifier.width(160.dp))
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "", modifier = Modifier
                .size(40.dp)

                .clickable {
                    viewModel.logout()
                    navController.navigate(Route.LoginNavigation.route) {
                        popUpTo(Route.MainNavigation.route) { inclusive = true }
                        // Make the new destination the top of the stack
                        launchSingleTop = true
                    }
                })
        }

        SearchBar()
        Spacer(modifier = Modifier.height(10.dp))
        PromotionBanner()
        OnSaleSection(productsOnSale, viewModel, basketViewModel, navController,bottomNavViewModel)
        CategoriesSection(categories)
        OrderAgainSection(orderAgainProducts, viewModel, basketViewModel,bottomNavViewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = { /* Handle search query */ },
        placeholder = { Text("Search...", color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),

        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),

        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun PromotionBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4B5CE4), RoundedCornerShape(8.dp))

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.background(
                    color = colorResource(id = R.color.purple_700),
                    shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                )
            ) {
                Text(
                    text = "50 % off ",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "All bakery products \n after 9PM every day!",
                style = TextStyle(
                    fontSize = 20.sp,

                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White

                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun OnSaleSection(
    products: List<Product>,
    viewModel: ShopViewModel,
    basketViewModel: BasketViewModel,
    navController: NavController,
    bottomNavViewModel: BottomNavViewModel
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ){
        Text(
            text = "On Sale",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "show all",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W400),
            modifier = Modifier.padding(16.dp)
        )
    }

    LazyRow {
        items(products) { product ->
            ProductCard(basketViewModel, product, isOrder = false, onFavoriteClick = {
                viewModel.toggleFavorite(it)
            }, navController = navController,
                bottomNavViewModel = bottomNavViewModel
                )
        }
    }
}

@Composable
fun CategoriesSection(categories: List<Category>) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        ) {
        Text(
            text = "Categories",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "show all",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W400),
            modifier = Modifier.padding(16.dp)
        )
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
        ) {
        items(categories) { category ->
            CategoryCard(category)
        }
    }
}

@Composable
fun OrderAgainSection(
    products: List<Product>,
    viewModel: ShopViewModel,
    basketViewModel: BasketViewModel,
    bottomNavViewModel: BottomNavViewModel,
    navController: NavController
) {
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
        ){
        Text(
            text = "Order Again",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "show all",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W400),
            modifier = Modifier.padding(16.dp)
        )
    }

    LazyRow {
        items(products) { product ->
            ProductCard(basketViewModel, product, isOrder = true, onFavoriteClick = {
                viewModel.toggleFavorite(it)
            }, navController = navController,
                bottomNavViewModel =bottomNavViewModel
                )
        }
    }
}

@Composable
fun ProductCard(
    basketViewModel: BasketViewModel,
    product: Product,
    isOrder: Boolean,
    bottomNavViewModel: BottomNavViewModel,
    onFavoriteClick: (Product) -> Unit,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate("details/${product.id}")
            },

        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { onFavoriteClick(product) }
                        .padding(4.dp),
                    tint = if (product.isFavorite.value) Color(0xFFE91E63) else Color.LightGray
                )

                if (product.discount!=0) {
                    Box(
                        modifier = Modifier
                            .background(Color.Red, shape = RoundedCornerShape(10.dp))
                            .padding(6.dp)
                    ) {
                        Text(
                            text = "${product.discount.toString()} %",
                            color = Color.White,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .size(60.dp)
                    .padding(4.dp)
            )

            Text(
                text = product.name,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )

            Row {
                Text(
                    text = "$ ${product.price.toInt()}  ",
                    style = TextStyle(fontSize = 16.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "\\ ${product.quantity}",
                    modifier = Modifier.padding(5.dp),
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            OnShelfButton(
                onClick = {
                    basketViewModel.addItem(product.name, product.imageResId, product.price,product.quantity)
                    navController.navigate(Route.BasketScreen.route)

                },
                color =Color(0xff069750),
                text = "Add to Basket",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
        }
    }
}

@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painterResource(id = category.iconResId),
                contentDescription = category.name,
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.blue)),
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = category.name,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}
