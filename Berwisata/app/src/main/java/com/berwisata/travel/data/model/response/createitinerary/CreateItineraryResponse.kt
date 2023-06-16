package com.berwisata.travel.data.model.response.createitinerary


import com.google.gson.annotations.SerializedName

data class CreateItineraryResponse(
    @SerializedName("message")
    val message: String
)