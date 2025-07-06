package com.hooman.einkaufszettel.presentation.manage_bills

import com.hooman.einkaufszettel.domain.model.Bill

data class ManageBillState(
    val allBills:List<Bill>? = emptyList(),
    val isLoading:Boolean = false,
    val insertBillSuccess:Boolean = false,
    val insertBillError:String? = null
)
