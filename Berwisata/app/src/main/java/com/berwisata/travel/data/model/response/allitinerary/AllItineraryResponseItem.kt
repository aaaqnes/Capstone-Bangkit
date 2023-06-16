package com.berwisata.travel.data.model.response.allitinerary


import com.google.gson.annotations.SerializedName

data class AllItineraryResponseItem(
    @SerializedName("itinerary_date")
    val itineraryDate: String,
    @SerializedName("itinerary_name")
    val itineraryName: String
)