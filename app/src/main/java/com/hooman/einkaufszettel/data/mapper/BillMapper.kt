package com.hooman.einkaufszettel.data.mapper

import com.hooman.einkaufszettel.data.local.relation.BillWithItems
import com.hooman.einkaufszettel.domain.model.Bill

fun BillWithItems.toBill():Bill{
    return Bill(
        id = bill.id,
        billDate = bill.billDate,
        items = items.map { it.toShoppingItem() }
    )
}