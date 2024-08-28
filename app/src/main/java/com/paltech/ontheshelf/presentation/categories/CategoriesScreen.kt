package com.paltech.ontheshelf.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.presentation.categories.viewModel.CategoriesViewModel
import com.paltech.ontheshelf.presentation.home.Category

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val categories by viewModel.categories.observeAsState(emptyList())
    val subCategories by viewModel.subCategories.observeAsState(emptyMap())
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    // Wrap the Column in a vertical scroll modifier
    Box(modifier = Modifier
        .height(screenHeight)) {
        Column(
            modifier = Modifier
                .fillMaxHeight()

                .background(color = Color.White)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)

            // Enable vertical scrolling
        ) {
            Text(
                text = "Categories", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Static Categories
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(categories) { category ->
                        StaticCategoryCard(
                            categoryName = category.name,
                            iconResId = category.iconResId
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Expandable Sections
            ExpandableCategorySection(title = "Food", items = subCategories["Food"] ?: emptyList())
            ExpandableCategorySection(
                title = "Beverages",
                items = subCategories["Beverages"] ?: emptyList()
            )
            ExpandableCategorySection(
                title = "Other",
                items = subCategories["Other"] ?: emptyList()
            )
        }
    }
}

@Composable
fun StaticCategoryCard(categoryName: String, iconResId: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconResId), // Replace with actual icon resource
                contentDescription = categoryName,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF4B5CE4)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
    }
}

@Composable
fun ExpandableCategorySection(title: String, items: List<Category>) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Icon(
                painter = painterResource(id = if (expanded) R.drawable.baseline_keyboard_arrow_up_24 else R.drawable.baseline_keyboard_arrow_down_24),
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
        }

        if (expanded) {
            LazyVerticalGrid(

                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(items) { item ->
                        StaticCategoryCard(categoryName = item.name, iconResId = item.iconResId)
                    }
                }
            )
        }
    }
}
