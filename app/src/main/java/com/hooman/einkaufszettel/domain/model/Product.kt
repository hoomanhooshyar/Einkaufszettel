package com.hooman.einkaufszettel.domain.model

import com.hooman.einkaufszettel.data.local.entity.ProductEntity

data class Product(
    val id:Long,
    val name:String,
    val image:String?,
    val price:Double
){
    fun toProductEntity():ProductEntity{
        return ProductEntity(
            id = id,
            name = name,
            image = image,
            price = price
        )
    }
}
