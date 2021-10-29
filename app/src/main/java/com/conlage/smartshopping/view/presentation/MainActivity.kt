package com.conlage.smartshopping.view.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
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
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    private val hostVm: MainViewModelImpl by viewModels {
        host_vm_factory.create()
    }


    @Inject
    lateinit var product_vm_factory: ProductViewModelImpl.ProductViewModelFactory.Factory

    @Inject
    lateinit var host_vm_factory: MainViewModelImpl.MainViewModelFactory.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appShoppingComponent.inject(this)


        setContent {


            //Permissions
            val navController = rememberNavController()

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

//
//    fun NavGraphBuilder.AppSettingsScreen(navController: NavController) {
//
//        composable(route = Screen.AppSettingsScreen.route) {
//            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//            val uri = Uri.fromParts("package", packageName, null)
//            intent.data = uri
//            startActivity(intent)
//            navController.navigate(Screen.MainScreen.route)
//        }
//    }

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

    private fun NavGraphBuilder.Product(navController: NavController) {
        composable(
            route = Screen.ProductScreen.route +
                    "/{${ArgumentKeys.ARG_ID}}" +
                    "/{${ArgumentKeys.ARG_BARCODE}}" +
                    "/{${ArgumentKeys.ARG_IS_ADDED}}",
            arguments = listOf(
                navArgument(ArgumentKeys.ARG_ID) {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                },
                navArgument(ArgumentKeys.ARG_BARCODE) {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                },
                navArgument(ArgumentKeys.ARG_IS_ADDED) {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                }
            )
        ) { entry ->
            val vm: ProductViewModelImpl by viewModels {
                product_vm_factory.create(
                    id = entry.arguments!!.getInt(ArgumentKeys.ARG_ID),
                    barcode = entry.arguments!!.getString(ArgumentKeys.ARG_BARCODE),
                    isAdded = entry.arguments!!.getBoolean(ArgumentKeys.ARG_IS_ADDED)
                )
            }
            //pass vm
            ProductScreen(navController)
        }
    }

    private fun NavGraphBuilder.Main(navController: NavController) {
        composable(
            route = Screen.MainScreen.route

        ) { entry ->
            // pass args


            MainScreen(hostVm, navController)
        }
    }


}





