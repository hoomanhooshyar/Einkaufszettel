package com.hooman.einkaufszettel.core.util

sealed class Screen(val route:String) {

    object HomeScreen:Screen("home_screen")
    object AddItemScreen:Screen("add_item_screen")

}