package com.conlage.smartshopping.view.navigation

sealed class Screen(val route: String){

    object MainScreen : Screen("main_screen")
    object ScannerScreen : Screen("scanner_screen")
    object ProductScreen : Screen("product_screen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }
}
