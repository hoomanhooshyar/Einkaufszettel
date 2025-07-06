package com.hooman.einkaufszettel.presentation.manage_bills

data class ManageShoppingItemState(
    val isLoading:Boolean = false,
    val insertShoppingItemSuccess:Boolean = false,
    val insertShoppingItemError:String? = null
)
