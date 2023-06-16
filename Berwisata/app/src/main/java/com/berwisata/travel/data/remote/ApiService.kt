package com.berwisata.travel.data.remote

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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("API/place")
    suspend fun allPlace(
    ): Response<AllPlaceResponse>

    @GET("API/place/loc/{location}")
    suspend fun getPlacesByLoc(
        @Path("location") location: String
    ): Response<PlaceByLocResponse>

    @GET("API/place/{id}")
    suspend fun getPlacesById(
        @Path("id") id: String
    ): Response<PlaceByIdResponse>

    @GET("api/rec")
    suspend fun getRecommended(): Response<RecommendPlaceResponse>

    @FormUrlEncoded
    @POST("api/planner")
    suspend fun createItinerary(
        @Header("Authorization") idToken: String,
        @Field("itinerary_name") itineraryName: String,
        @Field("itinerary_date") itineraryDate: String
    ): Response<CreateItineraryResponse>

    @GET("api/planner/all")
    suspend fun getItineraryAll(
        @Header("Authorization") idToken: String
    ): Response<AllItineraryResponse>

    @FormUrlEncoded
    @POST("api/planner/add")
    suspend fun addPlaceToItinerary(
        @Header("Authorization") token: String,
        @Field("place_name") placeName: String,
        @Field("place_id") placeId: String,
        @Field("itinerary_name") itineraryName: String
    ): Response<AddPlaceToItineraryResponse>


    @GET("api/planner/specific")
    suspend fun getSpecificItinerary(
        @Header("Authorization") token: String,
        @Query("itinerary_name") itineraryName: String
    ): Response<SpecificItineraryResponse>

    @POST("api/weather")
    suspend fun getRecommendationByWeather(
        @Header("Authorization") token: String,
        @Body userCityRequest: UserCityRequest
    ): Response<WeatherRecommendationResponse>


}