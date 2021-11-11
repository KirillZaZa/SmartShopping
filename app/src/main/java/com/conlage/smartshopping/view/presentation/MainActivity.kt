package com.conlage.smartshopping.view.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
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
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
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


    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appShoppingComponent.inject(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            val navController = rememberAnimatedNavController()



            ProvideWindowInsets(consumeWindowInsets = false) {
                SmartShoppingTheme {

                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen.route,
                    ) {


                        Main(navController)

                        Scanner(navController)

                        Product(navController)


                    }
                }

            }


        }
    }

    @ExperimentalAnimationApi
    private fun NavGraphBuilder.Scanner(navController: NavController) {
        composable(route = Screen.ScannerScreen.route) {
            ScannerScreen(navController)
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalUnitApi
    private fun NavGraphBuilder.Product(navController: NavController) {
        composable(
            route = Screen.ProductScreen.route +
                    "/{${ArgumentKeys.ARG_ID}}" +
                    "/{${ArgumentKeys.ARG_BARCODE}}" +
                    "/{${ArgumentKeys.ARG_IS_DIALOG_OPEN}}",
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
                },
                navArgument(ArgumentKeys.ARG_IS_DIALOG_OPEN) {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(150, delayMillis = 25, easing = FastOutSlowInEasing)
                )
            }
        ) { entry ->


            val productVm: ProductViewModelImpl by viewModels {
                product_vm_factory.create()
            }

            val isVisible = entry.arguments!!.getBoolean(ArgumentKeys.ARG_IS_DIALOG_OPEN)

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

                    hostVm.handleBulbVisibility(isVisible = it)

                    entry.arguments!!.clear()

                },
                isVisible = isVisible
            )


        }
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    private fun NavGraphBuilder.Main(navController: NavController) {
        composable(
            route = Screen.MainScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(150, easing = FastOutSlowInEasing)
                )
            },

            ) {


            MainScreen(hostVm, navController, this@MainActivity)
        }
    }


}





