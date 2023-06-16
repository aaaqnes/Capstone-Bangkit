package com.berwisata.travel.data.repository

import com.berwisata.travel.data.model.request.UserCityRequest
import com.berwisata.travel.data.model.response.addplacetoitinerary.AddPlaceToItineraryResponse
import com.berwisata.travel.data.model.response.allitinerary.AllItineraryResponse
import com.berwisata.travel.data.model.response.createitinerary.CreateItineraryResponse
import com.berwisata.travel.data.model.response.place.AllPlaceResponse
import com.berwisata.travel.data.model.response.placebyid.PlaceByIdResponse
import com.berwisata.travel.data.model.response.placebylocation.PlaceByLocResponse
import com.berwisata.travel.data.model.response.recommendplace.RecommendPlaceResponse
import com.berwisata.travel.data.model.response.specificitinerary.SpecificItineraryResponse
import com.berwisata.travel.data.model.response.weatherrecommendation.WeatherRecommendationResponse
import com.berwisata.travel.data.remote.ApiService
import retrofit2.Response

class BerwisataRepository(
    private val apiService: ApiService
) {


    suspend fun getAllPlace() : Response<AllPlaceResponse> {
        return apiService.allPlace()
    }

    suspend fun getPlaceByLoc(location: String): Response<PlaceByLocResponse> {
        return apiService.getPlacesByLoc(location)
    }

    suspend fun getPlaceById(id: String): Response<PlaceByIdResponse> {
        return apiService.getPlacesById(id)
    }

    suspend fun getRecommendedPlace() : Response<RecommendPlaceResponse> {
        return apiService.getRecommended()
    }

    suspend fun createItinerary(idToken: String, itineraryName: String, itineraryDate: String) : Response<CreateItineraryResponse> {
        return  apiService.createItinerary(idToken, itineraryName, itineraryDate)
    }

    suspend fun getAllItinerary(idToken: String) : Response<AllItineraryResponse> {
        return apiService.getItineraryAll(idToken)
    }

    suspend fun addPlaceToItinerary(idToken: String, placeName : String, placeId : String, itineraryName: String) : Response<AddPlaceToItineraryResponse> {
        return apiService.addPlaceToItinerary(idToken, placeName, placeId, itineraryName)
    }

    suspend fun getSpecificItinerary(idToken: String, itineraryName: String) : Response<SpecificItineraryResponse> {
        return apiService.getSpecificItinerary(idToken, itineraryName)
    }

    suspend fun getByWeather(idToken: String, userCityRequest: UserCityRequest) : Response<WeatherRecommendationResponse> {
        return apiService.getRecommendationByWeather(idToken, userCityRequest)
    }

}