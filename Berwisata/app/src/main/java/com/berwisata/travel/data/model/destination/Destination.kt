package com.berwisata.travel.data.model.destination

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Itinerary(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id : Int?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("itemImage")
    val itemImage : String?,
    @SerializedName("address")
    var amount : String?,
    @SerializedName("description")
    val price : String?
)