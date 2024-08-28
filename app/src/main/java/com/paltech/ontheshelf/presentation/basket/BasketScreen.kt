

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.data.localdb.BasketItemEntity
import com.paltech.ontheshelf.presentation.basket.viewmodel.BasketViewModel
import com.paltech.ontheshelf.presentation.common.OnShelfButton
import com.paltech.ontheshelf.presentation.navigation.Route

@Composable
fun BasketScreen(
    basketViewModel: BasketViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val basketItems by basketViewModel.basketItems.observeAsState(emptyList())
    val subtotal by basketViewModel.subtotal
    val deliveryFee = basketViewModel.deliveryFee
    val total by basketViewModel.total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Your Basket",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (basketItems.isEmpty()) {
            Text(text = "Your basket is empty.")
        } else {
            LazyColumn {
                items(basketItems) { basketItem ->
                    BasketItemRow(
                        basketItem = basketItem,
                        onIncrement = { basketViewModel.incrementQuantity(basketItem) },
                        onDecrement = { basketViewModel.decrementQuantity(basketItem) },
                        onRemove = { basketViewModel.removeItem(basketItem) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BasketSummary(
            subtotal = subtotal,
            delivery = deliveryFee,
            total = total,
            onOrderClick = {
                if(basketItems.isNotEmpty()){
                navController.navigate(Route.OrderNow.route);
                }
            }
        )
    }
}

@Composable
fun BasketItemRow(
    basketItem: BasketItemEntity,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = basketItem.imageResId),
            contentDescription = basketItem.name,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = basketItem.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Price: $${basketItem.price}", style = MaterialTheme.typography.bodyMedium)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDecrement) {
                Icon(painter = painterResource(id = R.drawable.baseline_remove_24), contentDescription = "Decrease Quantity")
            }
            Text(text = basketItem.quantity.toString(), style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = onIncrement) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase Quantity")
            }
            IconButton(onClick = onRemove) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Item")
            }
        }
    }
}

@Composable
fun BasketSummary(
    subtotal: Double,
    delivery: Double,
    total: Double,
    onOrderClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Subtotal")
            Text(text = "$${"%.2f".format(subtotal)}")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Delivery")
            Text(text = "$${"%.2f".format(delivery)}")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${"%.2f".format(total)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OnShelfButton(
            onClick = onOrderClick,
            color = colorResource(id = R.color.green),
            modifier = Modifier.fillMaxWidth(),
            text = "order now"
        )
    }
}
