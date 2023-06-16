package com.berwisata.travel.data.model.response.specificitinerary


import com.google.gson.annotations.SerializedName

data class SpecificItineraryResponseItem(
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("place_name")
    val placeName: String
)