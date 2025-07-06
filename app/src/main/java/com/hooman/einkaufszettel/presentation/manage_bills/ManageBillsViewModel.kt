package com.hooman.einkaufszettel.presentation.manage_bills

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.usecase.DeleteBillUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsFromLocalUseCase
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
    private val deleteBillUseCase: DeleteBillUseCase,
    private val connectivityObserver: ConnectivityObserver
):ViewModel() {
    private val _state = mutableStateOf(ManageBillState())

    val state:State<ManageBillState> = _state

    private val _status = MutableStateFlow(Status.Unavailable)
    val status:MutableStateFlow<Status> get() = _status

    init {
        networkConnectionCheck()
    }

    private fun networkConnectionCheck(){
        viewModelScope.launch {
            connectivityObserver.observe().collect{ newStatus ->
                _status.value = newStatus
                if(_status.value == Status.Available){
                    //Call Get All Bills From Firebase Method
                }else{
                    getAllBillsFromLocal()
                }
            }
        }
    }

    fun getAllBillsFromLocal(){
        viewModelScope.launch {
            getAllBillsFromLocalUseCase().collect{ result ->
                withContext(Dispatchers.Main){
                    handleResult(result)
                }
            }
        }
    }

    private fun handleResult(result: Resource<List<Bill>>) {
        when(result){
            is Resource.Success -> {
                _state.value = _state.value.copy(
                    allBills = result.data ?: emptyList(),
                    isLoading = false
                )
            }
            is Resource.Loading -> {
                _state.value = _state.value.copy(
                    allBills = result.data ?: emptyList(),
                    isLoading = true
                )
            }
            is Resource.Error -> {
                _state.value = _state.value.copy(
                    allBills = result.data ?: emptyList(),
                    isLoading = false
                )
            }
        }
    }
}