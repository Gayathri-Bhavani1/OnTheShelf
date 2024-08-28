import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.data.category.CategoriesRepository
import com.paltech.ontheshelf.presentation.home.Category

class CategoriesRepositoryImpl : CategoriesRepository {
    override fun getCategories(): List<Category> {
        // Mock data
        return listOf(
            Category("New in", R.drawable.store),
            Category("Super sale", R.drawable.sale)
        )
    }

    override fun getSubCategoriesForCategory(category: String): List<Category> {
        return when (category) {
            "Food" -> listOf(
                Category("Bakery", R.drawable.bakery),
                Category("Fruits", R.drawable.apple),
                Category("Meat", R.drawable.leg),
                Category("Fish", R.drawable.fish),
                Category("Vegetables", R.drawable.plant),
                Category("Dairy", R.drawable.box),
                Category("Snacks", R.drawable.taco),
                Category("Sweets", R.drawable.donut),

                )
            "Beverages" -> listOf(
                Category("Alcohol", R.drawable.wine),
                Category("Juices", R.drawable.apple),
                Category("Coffee", R.drawable.coffee),
                Category("Soda", R.drawable.soda),

                // Add other subcategories
            )
            "Other" -> listOf(
                Category("Pharmacy", R.drawable.pharmacy),
                Category("Beauty", R.drawable.cosmetics),
                Category("Toiletries", R.drawable.shower),
                Category("Household", R.drawable.home),


                // Add other subcategories
            )
            else -> emptyList()
        }
    }
}
