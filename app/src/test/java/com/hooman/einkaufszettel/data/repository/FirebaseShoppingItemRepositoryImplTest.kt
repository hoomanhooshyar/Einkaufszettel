package com.hooman.einkaufszettel.data.repository

import com.google.common.truth.Truth.assertThat
import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.remote.FakeFirebaseService
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FirebaseShoppingItemRepositoryImplTest {

    private lateinit var repository:FirebaseShoppingItemRepositoryImpl
    private lateinit var service:FakeFirebaseService

    @Before
    fun setup(){
        service = FakeFirebaseService()
        repository = FirebaseShoppingItemRepositoryImpl(service.shoppingItemDataSource)
    }

    @Test
    fun insertItem_shouldReturnSuccess() = runTest {
        val item = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user"
        )

        val insert = repository.insertShoppingItem(item).toList()
        val loadingStatus = insert.first()
        val success = insert.last()

        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).isEqualTo(Unit)
    }

    @Test
    fun insertItem_shouldReturnError_whenProductNameIsBlank() = runTest {
        val item = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "",
            productPrice = 1.0,
            productImage = "image",
            userId = "user"
        )

        val insert = repository.insertShoppingItem(item).toList()
        val loading = insert.first()
        val error = insert.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(error).isInstanceOf(Resource.Error::class.java)
        assertThat(error.message).isEqualTo("Product name is empty")
    }

    @Test
    fun insertItem_shouldReturnError_whenItemCountIs0() = runTest {
        val item = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 0,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user"
        )

        val insert = repository.insertShoppingItem(item).toList()
        val loading = insert.first()
        val error = insert.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(error).isInstanceOf(Resource.Error::class.java)
        assertThat(error.message).isEqualTo("Item count should bigger than 0")
    }

    @Test
    fun insertItem_shouldReturnError_whenProductPriceIs0() = runTest {
        val item = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 0.0,
            productImage = "image",
            userId = "user"
        )

        val insert = repository.insertShoppingItem(item).toList()
        val loading = insert.first()
        val error = insert.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(error).isInstanceOf(Resource.Error::class.java)
        assertThat(error.message).isEqualTo("Product price should be bigger than 0.0")
    }

    @Test
    fun insertItem_shouldReturnError_whenUserIdIsBlank() = runTest {
        val item = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = ""
        )

        val insert = repository.insertShoppingItem(item).toList()
        val loading = insert.first()
        val error = insert.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(error).isInstanceOf(Resource.Error::class.java)
        assertThat(error.message).isEqualTo("Invalid user id")
    }

    @Test
    fun getAllItemsByUserId_shouldReturnOnlyMatchingItems() = runTest {
        val item1 = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user1"
        )
        val item2 = ShoppingItem(
            id = "2",
            billId = 2L,
            productId = 2L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user2"
        )

        repository.insertShoppingItem(item1).toList()
        repository.insertShoppingItem(item2).toList()

        val getItem = repository.getAllShoppingItemsByUserId(item2.userId)
        val loading = getItem.first()
        val success = getItem.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).hasSize(1)
        assertThat(success.data).containsExactly(item2)
    }

    @Test
    fun getAllItemsByUserId_shouldReturnEmptyList_whenUserHasNoItems() = runTest {
        val item1 = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user1"
        )

        repository.insertShoppingItem(item1).toList()

        val getItem = repository.getAllShoppingItemsByUserId("user2").toList()
        val loading = getItem.first()
        val success = getItem.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).isEmpty()
    }

    @Test
    fun getShoppingItemByBillId_shouldReturnCorrectItems() = runTest {
        val item1 = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user1"
        )
        val item2 = ShoppingItem(
            id = "2",
            billId = 2L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user1"
        )

        repository.insertShoppingItem(item1).toList()
        repository.insertShoppingItem(item2).toList()

        val getItem = repository.getShoppingItemByBillId(item2.billId).toList()
        val loading = getItem.first()
        val success = getItem.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).containsExactly(item2)
    }

    @Test
    fun getShoppingItemByBillId_shouldReturnEmptyList_whenIdNotFound() = runTest {
        val getItem = repository.getShoppingItemByBillId(1L).toList()
        val loading = getItem.first()
        val result = getItem.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isEmpty()
    }

    @Test
    fun deleteShoppingItem_shouldRemoveItem() = runTest {
        val item = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user1"
        )

        repository.insertShoppingItem(item).toList()
        val firstGet = repository.getShoppingItemByBillId(item.billId).toList()
        val firstResult = firstGet.last()
        assertThat(firstResult).isInstanceOf(Resource.Success::class.java)
        assertThat(firstResult.data).containsExactly(item)

        repository.deleteShoppingItem(item.id).toList()

        val secondGet = repository.getShoppingItemByBillId(item.billId).toList()
        val secondResult = secondGet.last()
        assertThat(secondResult).isInstanceOf(Resource.Success::class.java)
        assertThat(secondResult.data).isEmpty()
    }

    @Test
    fun deleteShoppingItem_shouldReturnSuccess_whenItemNotExists() = runTest {
        val deleteItem = repository.deleteShoppingItem("1")

        val loading = deleteItem.first()
        val success = deleteItem.last()

        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).isEqualTo(Unit)
    }

    @Test
    fun insertingDuplicateItemIds_shouldOverridePrevious() = runTest {
        val item = ShoppingItem(
            id = "1",
            billId = 1L,
            productId = 1L,
            itemCount = 1,
            productName = "name",
            productPrice = 1.0,
            productImage = "image",
            userId = "user1"
        )

        repository.insertShoppingItem(item).toList()
        repository.insertShoppingItem(item).toList()

        val getItem = repository.getShoppingItemByBillId(item.billId)
        val loading = getItem.first()
        val success = getItem.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(success.data).hasSize(1)
        assertThat(success.data).containsExactly(item)
    }

}