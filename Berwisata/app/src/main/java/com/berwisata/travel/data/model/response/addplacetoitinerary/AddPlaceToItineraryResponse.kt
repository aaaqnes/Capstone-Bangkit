package com.berwisata.travel.data.model.response.addplacetoitinerary


import com.google.gson.annotations.SerializedName

data class AddPlaceToItineraryResponse(
    @SerializedName("message")
    val message: String
)