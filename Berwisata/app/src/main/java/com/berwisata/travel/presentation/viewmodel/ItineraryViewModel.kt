package com.berwisata.travel.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berwisata.travel.data.model.response.addplacetoitinerary.AddPlaceToItineraryResponse
import com.berwisata.travel.data.model.response.allitinerary.AllItineraryResponseItem
import com.berwisata.travel.data.model.response.createitinerary.CreateItineraryResponse
import com.berwisata.travel.data.model.response.specificitinerary.SpecificItineraryResponseItem
import com.berwisata.travel.data.repository.BerwisataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class ItineraryViewModel(
    private val berwisataRepository: BerwisataRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _createItinerary = MutableLiveData<CreateItineraryResponse?>()
    val createItinerary: LiveData<CreateItineraryResponse?> = _createItinerary

    private val _listItinerary = MutableLiveData<List<AllItineraryResponseItem?>>()
    val listItinerary: LiveData<List<AllItineraryResponseItem?>> = _listItinerary

    private val _listSpecificItinerary = MutableLiveData<List<SpecificItineraryResponseItem?>>()
    val listSpecificItinerary: LiveData<List<SpecificItineraryResponseItem?>> = _listSpecificItinerary

    private val _addPlace = MutableLiveData<AddPlaceToItineraryResponse?>()
    val addPlace: LiveData<AddPlaceToItineraryResponse?> = _addPlace

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private fun handleCreateItineraryError(response: Response<CreateItineraryResponse>) {
        val errorMessage = response.errorBody()?.string()
        if (errorMessage != null) {
            try {
                val errorJson = JSONObject(errorMessage)
                val error = errorJson.getString("message")
                Log.d("registerUserMessage", "error $error")
                _errorMessage.value = error
            } catch (e: JSONException) {
                Log.d("registerUserMessage", "JSON parsing error: ${e.message}")
                _errorMessage.value = "Unexpected error occurred"
            }
        } else {
            _errorMessage.value = "Unexpected error occurred"
        }
    }

    private fun handleAddPlaceItineraryError(response: Response<AddPlaceToItineraryResponse>) {
        val errorMessage = response.errorBody()?.string()
        if (errorMessage != null) {
            try {
                val errorJson = JSONObject(errorMessage)
                val error = errorJson.getString("message")
                Log.d("registerUserMessage", "error $error")
                _errorMessage.value = error
            } catch (e: JSONException) {
                Log.d("registerUserMessage", "JSON parsing error: ${e.message}")
                _errorMessage.value = "Unexpected error occurred"
            }
        } else {
            _errorMessage.value = "Unexpected error occurred"
        }
    }

    fun addPlace(idToken: String, placeName : String, placeId : String, itineraryName: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                berwisataRepository.addPlaceToItinerary(idToken, placeName, placeId, itineraryName)
            }.onSuccess { response ->
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _addPlace.value = response.body()
                    } else {
                        handleAddPlaceItineraryError(response)
                    }
                }
            }.onFailure { e ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message
                }
            }.also {
                withContext(Dispatchers.Main) {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun createItinerary(idToken : String, itineraryName: String, itineraryDate: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                berwisataRepository.createItinerary(idToken, itineraryName, itineraryDate)
            }.onSuccess { response ->
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _createItinerary.value = response.body()
                    } else {
                        handleCreateItineraryError(response)
                    }
                }
            }.onFailure { e ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message
                }
            }.also {
                withContext(Dispatchers.Main) {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun getAllItinerary(idToken: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = berwisataRepository.getAllItinerary(idToken)
                response.body()
            }.onSuccess { itinerary ->
                withContext(Dispatchers.Main) {
                    _listItinerary.value = itinerary
                }
            }.onFailure { throwable ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = throwable.message
                }
            }.also {
                withContext(Dispatchers.Main) {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun getSpecificItinerary(idToken: String, itineraryName: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = berwisataRepository.getSpecificItinerary(idToken, itineraryName)
                response.body()
            }.onSuccess { itinerary ->
                withContext(Dispatchers.Main) {
                    _listSpecificItinerary.value = itinerary
                }
            }.onFailure { throwable ->
                withContext(Dispatchers.Main) {
                    _errorMessage.value = throwable.message
                }
            }.also {
                withContext(Dispatchers.Main) {
                    _isLoading.postValue(false)
                }
            }
        }
    }
}