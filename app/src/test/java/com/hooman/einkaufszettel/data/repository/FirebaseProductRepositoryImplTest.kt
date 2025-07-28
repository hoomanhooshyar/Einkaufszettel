package com.hooman.einkaufszettel.data.repository

import com.google.common.truth.Truth.assertThat
import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.remote.FakeFirebaseService
import com.hooman.einkaufszettel.domain.model.Product
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FirebaseProductRepositoryImplTest {

   private lateinit var repository:FirebaseProductRepositoryImpl
   private lateinit var service:FakeFirebaseService

    @Before
    fun setup(){
        service = FakeFirebaseService()
        repository = FirebaseProductRepositoryImpl(service.productDataSource)
    }

    @Test
    fun insertProduct_shouldReturnSuccess() = runTest{
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )
        val emission = repository.insertProduct(product).toList()

        val last = emission.last()
        val first = emission.first()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(last).isInstanceOf(Resource.Success::class.java)
        assertThat(last.data).isEqualTo(Unit)
    }

    @Test
    fun insertProduct_shouldReturnError_whenNameIsEmpty() = runTest {
        val product = Product(
            id = 1L,
            name = "",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )
        val emission = repository.insertProduct(product).toList()
        val last = emission.last()
        val first = emission.first()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(last).isInstanceOf(Resource.Error::class.java)
        assertThat(last.message).isEqualTo("Name shouldn't be empty")
    }

    @Test
    fun insertProduct_shouldReturnError_whenUserIsEmpty() = runTest {
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "",
            price = 2.0,
            image = "imageTest"
        )

        val emission = repository.insertProduct(product).toList()
        val last = emission.last()
        val first = emission.first()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(last).isInstanceOf(Resource.Error::class.java)
        assertThat(last.message).isEqualTo("User id is empty")
    }

    @Test
    fun insertProduct_shouldReturnError_whenPriceIsLessThat0() = runTest {
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 0.0,
            image = "imageTest"
        )

        val emission = repository.insertProduct(product).toList()
        val last = emission.last()
        val first = emission.first()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(last).isInstanceOf(Resource.Error::class.java)
        assertThat(last.message).isEqualTo("Price is not correct")
    }

    @Test
    fun getProductById_shouldReturnCorrectProduct() = runTest {
        val product1 = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        val product2 = Product(
            id = 2L,
            name = "M",
            userId = "user1",
            price = 3.0,
            image = "imageTest"
        )

        repository.insertProduct(product1).toList()
        repository.insertProduct(product2).toList()
        val emission = repository.getProductById(product1.id).toList()
        val first = emission.first()
        val last = emission.last()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(last).isInstanceOf(Resource.Success::class.java)
        assertThat(last.data).containsExactly(product1)
    }

    @Test
    fun getProductById_IdNotExists_shouldReturnEmptyList() = runTest {
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        repository.insertProduct(product).toList()
        val emission = repository.getProductById(2L).toList()
        val first = emission.first()
        val last = emission.last()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(last).isInstanceOf(Resource.Success::class.java)
        assertThat(last.data).isEmpty()
    }

    @Test
    fun getAllProductsByUserId_shouldReturnOnlyMatchingUserProducts() = runTest {
        val product1 = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        val product2 = Product(
            id = 2L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        val product3 = Product(
            id = 3L,
            name = "Milk",
            userId = "user1",
            price = 2.0,
            image = "imageTest"
        )

        repository.insertProduct(product1).toList()
        repository.insertProduct(product2).toList()
        repository.insertProduct(product3).toList()

        val emission = repository.getAllProductsByUserId("user123").toList()
        val first = emission.first()
        val success = emission.last()
        val result = (success as Resource.Success).data
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(result).hasSize(2)
        assertThat(result?.get(0)).isEqualTo(product1)
        assertThat(result?.get(1)).isEqualTo(product2)
    }

    @Test
    fun getAllProductsByUserId_shouldReturnEmptyList_ifUserHasNoProducts() = runTest {
        val product1 = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        val product2 = Product(
            id = 2L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        repository.insertProduct(product1).toList()
        repository.insertProduct(product2).toList()
        val emission = repository.getAllProductsByUserId("123").toList()
        val first = emission.first()
        val success = emission.last()
        val result = (success as Resource.Success).data
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(result).isEmpty()
    }

    @Test
    fun getProductByName_shouldReturnMatchingProducts() = runTest{
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )
        val product1 = Product(
            id = 1L,
            name = "Cheese",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )
        repository.insertProduct(product).toList()
        repository.insertProduct(product1).toList()
        val emission = repository.getProductByName("Milk").toList()
        val first = emission.first()
        val success = emission.last()
        val result = (success as Resource.Success).data
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(result).isNotEmpty()
        assertThat(result).hasSize(1)
        assertThat(result).containsExactly(product)
    }

    @Test
    fun getProductByName_shouldReturnEmptyList_whenNameNotFound() = runTest {
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        repository.insertProduct(product).toList()
        val emission = repository.getProductByName("Cheese").toList()
        val first = emission.first()
        val success = emission.last()
        val result = (success as Resource.Success).data
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(result).isEmpty()
    }

    @Test
    fun deleteProduct_shouldRemoveProduct() = runTest {
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )
        //Add product to List
        repository.insertProduct(product).toList()
        val emission = repository.getProductById(product.id).toList()
        val success = emission.last()
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        val result = (success as Resource.Success).data

        assertThat(result).containsExactly(product) //Check that it has been added
        val removing = repository.deleteProduct(product.id).toList() //Remove the product

        val removedResult = removing.last()
        assertThat(removedResult).isInstanceOf(Resource.Success::class.java)//Check the Resource's kind
        assertThat(removedResult.data).isEqualTo(Unit) //Check the result of deleting

        //Looking for the removed product
        val findItem = repository.getProductById(product.id).toList()
        val resultFind = findItem.last().data
        assertThat(resultFind).isEmpty()

    }

    @Test
    fun insertingDuplicateProductIds_shouldStoreBothOrOverride_accordingToLogic() = runTest {
        val product1 = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        val product2 = Product(
            id = 1L,
            name = "Cheese",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        repository.insertProduct(product1).toList()
        repository.insertProduct(product2).toList()
        val emission = repository.getAllProductsByUserId("user123").toList()
        val first = emission.first()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        val success = emission.last()
        val result = (success as Resource.Success).data
        assertThat(result).hasSize(1)
        assertThat(result).containsExactly(product2)
    }

    @Test
    fun deletingNonExistentProduct_shouldNotCrash() = runTest {
        val emission = repository.deleteProduct(1L).toList()
        val first = emission.first()
        val success = emission.last()
        assertThat(first).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).isEqualTo(Unit)
    }

    @Test
    fun getProductById_afterDelete_shouldReturnEmpty() = runTest{
        val product = Product(
            id = 1L,
            name = "Milk",
            userId = "user123",
            price = 2.0,
            image = "imageTest"
        )

        repository.insertProduct(product).toList()
        val getProduct = repository.getProductById(product.id).toList()
        val getProductResult = getProduct.last()


        assertThat(getProductResult).isInstanceOf(Resource.Success::class.java)//Check the Result of Resource
        assertThat(getProductResult.data).containsExactly(product)//Check product is exists

        val deleteProduct = repository.deleteProduct(product.id).toList()
        val deleteProductResult = deleteProduct.last()

        //Check that product has been deleted successfully
        assertThat(deleteProductResult).isInstanceOf(Resource.Success::class.java)
        assertThat(deleteProductResult.data).isEqualTo(Unit)

        val getProductAgain = repository.getProductById(product.id)
        val getProductAgainResult = getProductAgain.last()
        //Check the Product---Result should be empty
        assertThat(getProductAgainResult).isInstanceOf(Resource.Success::class.java)
        assertThat(getProductAgainResult.data).isEmpty()
    }
}