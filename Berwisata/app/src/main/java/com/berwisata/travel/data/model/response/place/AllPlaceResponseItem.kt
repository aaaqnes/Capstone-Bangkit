package com.berwisata.travel.data.model.response.place


import com.google.gson.annotations.SerializedName

data class AllPlaceResponseItem(
    @SerializedName("place_desc")
    val placeDesc: String,
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("place_loc")
    val placeLoc: String,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("place_imgurl")
    val placeImage: String
)