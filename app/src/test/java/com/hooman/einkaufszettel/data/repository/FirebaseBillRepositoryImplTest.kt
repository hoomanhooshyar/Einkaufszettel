package com.hooman.einkaufszettel.data.repository

import com.google.common.truth.Truth.assertThat
import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.remote.FakeFirebaseService
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class FirebaseBillRepositoryImplTest {

    private lateinit var repository:FirebaseBillRepositoryImpl
    private lateinit var service:FakeFirebaseService

    @Before
    fun setup(){
        service = FakeFirebaseService()
        repository = FirebaseBillRepositoryImpl(service.billDataSource)
    }

    @Test
    fun insertBill_shouldReturnSuccess() = runTest {
        val billId = 1L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user123"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user123"
        )

        val emission = repository.insertBill(bill)
        val loadingStatus = emission.first()
        val successStatus = emission.last()
        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(successStatus).isInstanceOf(Resource.Success::class.java)
        assertThat(successStatus.data).isEqualTo(Unit)
    }

    @Test
    fun getBillByBillId_shouldReturnCorrectBill() = runTest {
        val billId = 1L
        val billId2 = 2L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user123"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user123"
        )

        val item2 = ShoppingItem(
            id = "2",
            billId = billId2,
            productId = 2L,
            itemCount = 3,
            productName = "Test1",
            productPrice = 4.2,
            productImage = "image",
            userId = "user123"
        )
        val items1 = mutableListOf<ShoppingItem>()
        items1.add(item2)
        val bill1 = Bill(
            id = billId2,
            billDate = date,
            items = items1,
            userId = "user123"
        )

        repository.insertBill(bill).toList()
        repository.insertBill(bill1).toList()
        val emission = repository.getBillById(bill1.id.toString()).toList()
        val loading = emission.first()
        val success = emission.last()
        assertThat(loading).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).containsExactly(bill1)
    }

    @Test
    fun getBillByBillId_shouldReturnEmptyList_whenIdNotFound() = runTest {
        val billId = 1L
        val fakeId = 2L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user123"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user123"
        )

        repository.insertBill(bill).toList()
        val emission = repository.getBillById(fakeId.toString()).toList()
        val loadingStatus = emission.first()
        val success = emission.last()
        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).isEmpty()
    }

    @Test
    fun getAllBillsByUserId_shouldReturnOnlyMatchingUserBills() = runTest {
        val billId = 1L
        val billId2 = 2L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user123"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user123"
        )

        val item2 = ShoppingItem(
            id = "2",
            billId = billId2,
            productId = 2L,
            itemCount = 3,
            productName = "Test1",
            productPrice = 4.2,
            productImage = "image",
            userId = "user"
        )
        val items1 = mutableListOf<ShoppingItem>()
        items1.add(item2)
        val bill1 = Bill(
            id = billId2,
            billDate = date,
            items = items1,
            userId = "user"
        )

        repository.insertBill(bill).toList()
        repository.insertBill(bill1).toList()
        val emission = repository.getAllBillsByUserId("user").toList()
        val loadingStatus = emission.first()
        val success = emission.last()
        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).containsExactly(bill1)
    }

    @Test
    fun getAllBillsByUserId_shouldReturnEmptyList_whenUserHasNoBills() = runTest {
        val billId = 1L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user123"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user123"
        )

        repository.insertBill(bill).toList()
        val emission = repository.getAllBillsByUserId("123").toList()
        val loadingStatus = emission.first()
        val success = emission.last()
        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).isEmpty()
    }

    @Test
    fun deleteBill_shouldRemoveBill() = runTest {
        val billId = 1L
        val billId2 = 2L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user123"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user123"
        )

        val item2 = ShoppingItem(
            id = "2",
            billId = billId2,
            productId = 2L,
            itemCount = 3,
            productName = "Test1",
            productPrice = 4.2,
            productImage = "image",
            userId = "user"
        )
        val items1 = mutableListOf<ShoppingItem>()
        items1.add(item2)
        val bill1 = Bill(
            id = billId2,
            billDate = date,
            items = items1,
            userId = "user"
        )

        repository.insertBill(bill).toList()
        repository.insertBill(bill1).toList()

        val getBill = repository.getBillById(bill.id.toString()).toList()
        val firstSuccess = getBill.last()
        assertThat(firstSuccess).isInstanceOf(Resource.Success::class.java)
        assertThat(firstSuccess.data).hasSize(1)
        assertThat(firstSuccess.data).containsExactly(bill)

        val emission = repository.deleteBill(billId = bill.id.toString()).toList()
        val loadingStatus = emission.first()
        val deleteSuccess = emission.last()
        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(deleteSuccess).isInstanceOf(Resource.Success::class.java)

        val getEmptyList = repository.getBillById(bill.id.toString()).toList()
        val finalSuccess = getEmptyList.last()
        assertThat(finalSuccess.data).isEmpty()
    }

    @Test
    fun deletingNoneExistingBill_shouldNotCrash() = runTest {
        val emission = repository.deleteBill("123")
        val loadingStatus = emission.first()
        val success = emission.last()
        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).isEqualTo(Unit)
    }

    @Test
    fun insertingDuplicatedBillIds_shouldOverridePrevious() = runTest {
        val billId = 1L
        val billId2 = 2L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user"
        )

        val item2 = ShoppingItem(
            id = "2",
            billId = billId2,
            productId = 2L,
            itemCount = 3,
            productName = "Test1",
            productPrice = 4.2,
            productImage = "image",
            userId = "user"
        )
        val items1 = mutableListOf<ShoppingItem>()
        items1.add(item2)
        val bill1 = Bill(
            id = billId,
            billDate = date,
            items = items1,
            userId = "user"
        )

        repository.insertBill(bill).toList()
        repository.insertBill(bill1).toList()
        val emission = repository.getAllBillsByUserId("user")
        val loadingStatus = emission.first()
        val success = emission.last()
        assertThat(loadingStatus).isInstanceOf(Resource.Loading::class.java)
        assertThat(success).isInstanceOf(Resource.Success::class.java)
        assertThat(success.data).hasSize(1)
        assertThat(success.data).containsExactly(bill1)
    }

    @Test
    fun getBillById_afterInsertAndDelete_shouldReturnEmpty() = runTest{
        val billId = 1L
        val localDate = LocalDate.now()
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val item = ShoppingItem(
            id = "1",
            billId = billId,
            productId = 1L,
            itemCount = 2,
            productName = "Test",
            productPrice = 1.2,
            productImage = "image",
            userId = "user"
        )
        val items = mutableListOf<ShoppingItem>()
        items.add(item)
        val bill = Bill(
            id = billId,
            billDate = date,
            items = items,
            userId = "user"
        )

        repository.insertBill(bill).toList()
        val firstGetBill = repository.getBillById(bill.id.toString()).toList()
        val loadingStatus1 = firstGetBill.first()
        val success1 = firstGetBill.last()
        assertThat(loadingStatus1).isInstanceOf(Resource.Loading::class.java)
        assertThat(success1.data).containsExactly(bill)

        repository.deleteBill(bill.id.toString()).toList()
        val lastGet = repository.getBillById(bill.id.toString()).toList()
        val success2 = lastGet.last()
        assertThat(success2.data).isEmpty()
    }
}