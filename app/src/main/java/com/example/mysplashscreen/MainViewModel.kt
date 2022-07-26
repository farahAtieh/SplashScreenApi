package com.example.mysplashscreen

import android.util.Log
import androidx.lifecycle.*
import com.example.mysplashscreen.domain.GetProductUseCase
import com.example.mysplashscreen.domain.StoreRepository
import com.example.mysplashscreen.model.ProductResponse
import com.example.mysplashscreen.util.ApiResponseState
import com.example.mysplashscreen.util.Resource
import com.example.mysplashscreen.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val getProductUseCase: GetProductUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getProducts()
    }

    /*fun getProducts() = liveData(Dispatchers.IO){
        emit(Resource.success(null))
        try {
            val products = repository.getProducts()
            Log.i("FARAH96", "Success: ${products.results.size}")
            emit(Resource.success(products))
        } catch (exception: Exception) {
            Log.i("FARAH96", exception.message + "")
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/

    private fun getProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            val results = getProductUseCase.getProducts()
            if (results is ApiResponseState.Success) {
                val products = results.response as ProductResponse
                showLogs("successfully and size is ${products.results.size}")
                _isLoading.postValue(false)
            } else if (results is ApiResponseState.Error) {
                showLogs("error occurred")
                _isLoading.postValue(false)
            }
        }

    }

    private fun showLogs(txt: String) {
        Log.i("Farah96", txt)
    }
}