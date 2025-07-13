package com.hooman.einkaufszettel.presentation.manage_bills

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.usecase.DeleteBillFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertBillToLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertShoppingItemToLocalUseCase
import com.hooman.einkaufszettel.presentation.util.internet_connection.ConnectivityObserver
import com.hooman.einkaufszettel.presentation.util.internet_connection.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManageBillsViewModel @Inject constructor(
    private val getAllBillsFromLocalUseCase: GetAllBillsFromLocalUseCase,
    private val getAllBillsFromRemoteUseCase: GetAllBillsFromRemoteUseCase,
    private val deleteBillUseCase: DeleteBillFromLocalUseCase,
    private val insertShoppingItemUseCase: InsertShoppingItemToLocalUseCase,
    private val insertBillToLocalUseCase: InsertBillToLocalUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val _stateBill = mutableStateOf(ManageBillState())

    val stateBill: State<ManageBillState> = _stateBill

    private val _stateShoppingItem = mutableStateOf(ManageShoppingItemState())
    val stateShoppingItem: State<ManageShoppingItemState> = _stateShoppingItem

    private val _status = MutableStateFlow(Status.Unavailable)
    val status: MutableStateFlow<Status> get() = _status


    init {
        networkConnectionCheck()
    }

    private fun networkConnectionCheck() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { newStatus ->
                _status.value = newStatus
                if (_status.value == Status.Available) {
                    getAllBillsFromRemote()
                } else {
                    getAllBillsFromLocal()
                }
            }
        }
    }

    fun getAllBillsFromLocal() {
        viewModelScope.launch {
            getAllBillsFromLocalUseCase().collect { result ->
                withContext(Dispatchers.Main) {
                    handleResult(result)
                }
            }
        }
    }

    fun getAllBillsFromRemote(){
        viewModelScope.launch {
            getAllBillsFromRemoteUseCase().collect{result ->
                withContext(Dispatchers.Main){
                    handleResult(result)
                }
            }
        }
    }

    fun insertBill(bill: Bill) {
        viewModelScope.launch {
            insertBillToLocalUseCase(bill).collect { result ->
                _stateBill.value =
                    when (result) {
                        is Resource.Loading -> {
                            _stateBill.value.copy(
                                isLoading = true,
                                insertBillSuccess = false,
                                insertBillError = null
                            )
                        }

                        is Resource.Success -> {
                            _stateBill.value.copy(
                                isLoading = false,
                                insertBillSuccess = true,
                                insertBillError = null
                            )
                        }

                        is Resource.Error -> {
                            _stateBill.value.copy(
                                isLoading = false,
                                insertBillSuccess = false,
                                insertBillError = result.message
                            )
                        }
                    }
            }
        }
    }

    fun insertShoppingItem(shoppingItem: ShoppingItem,billId:Long) {
        viewModelScope.launch {
            insertShoppingItemUseCase(shoppingItem,billId).collect { result ->
                _stateShoppingItem.value =
                    when (result) {
                        is Resource.Loading -> {
                            _stateShoppingItem.value.copy(
                                isLoading = true,
                                insertShoppingItemSuccess = false,
                                insertShoppingItemError = null
                            )
                        }

                        is Resource.Success -> {
                            _stateShoppingItem.value.copy(
                                isLoading = false,
                                insertShoppingItemSuccess = true,
                                insertShoppingItemError = null
                            )
                        }

                        is Resource.Error -> {
                            _stateShoppingItem.value.copy(
                                isLoading = false,
                                insertShoppingItemSuccess = false,
                                insertShoppingItemError = result.message
                            )
                        }
                    }
            }
        }
    }

    fun clearBillInsertStatus() {
        _stateBill.value = _stateBill.value.copy(
            isLoading = false,
            insertBillSuccess = false,
            insertBillError = null
        )
    }

    fun clearShoppingItemInsertStatus() {
        _stateShoppingItem.value = _stateShoppingItem.value.copy(
            isLoading = false,
            insertShoppingItemSuccess = false,
            insertShoppingItemError = null
        )
    }

    private fun handleResult(result: Resource<List<Bill>>) {
        when (result) {
            is Resource.Success -> {
                _stateBill.value = _stateBill.value.copy(
                    allBills = result.data ?: emptyList(),
                    isLoading = false
                )
            }

            is Resource.Loading -> {
                _stateBill.value = _stateBill.value.copy(
                    allBills = result.data ?: emptyList(),
                    isLoading = true
                )
            }

            is Resource.Error -> {
                _stateBill.value = _stateBill.value.copy(
                    allBills = result.data ?: emptyList(),
                    isLoading = false
                )
            }
        }
    }
}