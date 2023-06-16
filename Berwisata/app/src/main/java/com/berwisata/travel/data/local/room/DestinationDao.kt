package com.berwisata.travel.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.berwisata.travel.data.model.destination.Itinerary


@Dao
interface ItineraryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Itinerary)

    @Update
    fun update(item: Itinerary)

    @Delete
    fun delete(item: Itinerary)

    @Query("SELECT * FROM itinerary")
    fun getAll(): List<Itinerary>

}