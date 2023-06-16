package com.berwisata.travel.data.model.response.weatherrecommendation


import com.google.gson.annotations.SerializedName

data class WeatherRecommendationResponseItem(
    @SerializedName("place_desc")
    val placeDesc: String,
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("place_imgurl")
    val placeImgurl: String,
    @SerializedName("place_loc")
    val placeLoc: String,
    @SerializedName("place_name")
    val placeName: String
)