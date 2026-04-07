package com.aerc.cursotestingandroid.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.aerc.cursotestingandroid.productlist.presentation.ProductListScreen
import com.aerc.cursotestingandroid.settings.presentation.SettingsScreen

@Composable
fun NavGraph() {
    val backStack: NavBackStack<NavKey> = rememberNavBackStack(Screen.ProductList)

    val entries = entryProvider<NavKey> {
        entry<Screen.ProductList> {
            ProductListScreen(navigateToSettings = {backStack.add(Screen.Setting)})
        }
        entry<Screen.Cart> {
            Text(text = "Cart", fontSize = 30.sp)

        }
        entry<Screen.Setting> {
            SettingsScreen(
                onBack = { backStack.removeLastOrNull()}
            )
        }
        entry<Screen.ProductDetail> {
            Text(text = "ProductDetail", fontSize = 30.sp)

        }
    }
    NavDisplay(
        backStack = backStack,
        entryProvider = entries,
        onBack = { backStack.removeLastOrNull() }
    )
}
