package com.conlage.smartshopping.view.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.conlage.smartshopping.application.appShoppingComponent
import com.conlage.smartshopping.ui.theme.SmartShoppingTheme
import com.conlage.smartshopping.view.navigation.ArgumentKeys
import com.conlage.smartshopping.view.navigation.Screen
import com.conlage.smartshopping.view.screen.MainScreen
import com.conlage.smartshopping.view.screen.ProductScreen
import com.conlage.smartshopping.view.screen.ScannerScreen
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl
import com.conlage.smartshopping.viewmodel.impl.ProductViewModelImpl
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import javax.inject.Inject


@ExperimentalPermissionsApi
@ExperimentalUnitApi
class MainActivity : ComponentActivity() {

    private val hostVm: MainViewModelImpl by viewModels {
        host_vm_factory.create()
    }


    @Inject
    lateinit var product_vm_factory: ProductViewModelImpl.ProductViewModelFactory.ProductFactory

    @Inject
    lateinit var host_vm_factory: MainViewModelImpl.MainViewModelFactory.Factory


    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appShoppingComponent.inject(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()

            ProvideWindowInsets{
                SmartShoppingTheme {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen.route
                    ) {


                        Main(navController)

                        Scanner(navController)

                        Product(navController)


                    }
                }

            }


        }
    }

    @Composable
    private fun RequireCameraPermission() {

    }

    @Composable
    private fun RequireStoragePermission() {

    }

    private fun NavGraphBuilder.Scanner(navController: NavController) {
        composable(route = Screen.ScannerScreen.route) {
            ScannerScreen(navController)
        }
    }

    @ExperimentalComposeUiApi
    @ExperimentalUnitApi
    private fun NavGraphBuilder.Product(navController: NavController) {
        composable(
            route = Screen.ProductScreen.route +
                    "/{${ArgumentKeys.ARG_ID}}" +
                    "/{${ArgumentKeys.ARG_BARCODE}}",
            arguments = listOf(
                navArgument(ArgumentKeys.ARG_ID) {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument(ArgumentKeys.ARG_BARCODE) {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            )
        ) { entry ->


            val productVm: ProductViewModelImpl by viewModels {
                product_vm_factory.create()
            }

            Log.e("MainActivity", "entry: ${entry.arguments}")
            if (!entry.arguments!!.isEmpty) {
                productVm.start(
                    productId = entry.arguments!!.getInt(ArgumentKeys.ARG_ID),
                    barcode = entry.arguments!!.getString(ArgumentKeys.ARG_BARCODE)
                )
            } else productVm.clear()



            ProductScreen(
                navController,
                productVm,
                close = {
                    entry.arguments!!.clear()
                }
            )


        }
    }

    @ExperimentalComposeUiApi
    private fun NavGraphBuilder.Main(navController: NavController) {
        composable(
            route = Screen.MainScreen.route
        ) { entry ->
            // pass args
            MainScreen(hostVm, navController, this@MainActivity)
        }
    }


}





