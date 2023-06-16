package com.berwisata.travel.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berwisata.travel.data.model.request.UserCityRequest
import com.berwisata.travel.data.model.response.place.AllPlaceResponseItem
import com.berwisata.travel.data.model.response.placebyid.PlaceByIdResponse
import com.berwisata.travel.data.model.response.placebylocation.PlaceByLocResponseItem
import com.berwisata.travel.data.model.response.recommendplace.RecommendPlaceResponseItem
import com.berwisata.travel.data.model.response.weatherrecommendation.WeatherRecommendationResponseItem
import com.berwisata.travel.data.repository.BerwisataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DestinationViewModel(
    private val berwisataRepository: BerwisataRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listPlace = MutableLiveData<List<AllPlaceResponseItem?>>()
    val listPlace: LiveData<List<AllPlaceResponseItem?>> = _listPlace

    private val _listByWeather = MutableLiveData<List<WeatherRecommendationResponseItem?>>()
    val listByWeather: LiveData<List<WeatherRecommendationResponseItem?>> = _listByWeather

    private val _listPlaceByLoc = MutableLiveData<List<PlaceByLocResponseItem?>>()
    val listPlaceByLoc: LiveData<List<PlaceByLocResponseItem?>> = _listPlaceByLoc

    private val _listRecommendPlace = MutableLiveData<List<RecommendPlaceResponseItem?>>()
    val listRecommendPlace: LiveData<List<RecommendPlaceResponseItem?>> = _listRecommendPlace

    private val _placeById = MutableLiveData<PlaceByIdResponse?>()
    val placeById: LiveData<PlaceByIdResponse?> = _placeById

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getAllPlace() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = berwisataRepository.getAllPlace()
                response.body()
            }.onSuccess { allPlace ->
                withContext(Dispatchers.Main) {
                    _listPlace.value = allPlace
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

    fun getRecomByWeather(idToken:String, userCityRequest: UserCityRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = berwisataRepository.getByWeather(idToken, userCityRequest)
                response.body()
            }.onSuccess { allPlace ->
                withContext(Dispatchers.Main) {
                    _listByWeather.value = allPlace
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

    fun getAllPlaceByLoc(location: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = berwisataRepository.getPlaceByLoc(location)
                response.body()
            }.onSuccess { allPlace ->
                withContext(Dispatchers.Main) {
                    _listPlaceByLoc.value = allPlace
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

    fun getAllPlaceById(id: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = berwisataRepository.getPlaceById(id)
                response.body()
            }.onSuccess { place ->
                withContext(Dispatchers.Main) {
                    _placeById.value = place
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

    fun getRecommendPlace() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = berwisataRepository.getRecommendedPlace()
                response.body()
            }.onSuccess { recommend ->
                withContext(Dispatchers.Main) {
                    _listRecommendPlace.value = recommend
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