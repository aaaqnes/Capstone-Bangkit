package com.berwisata.travel.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.berwisata.travel.data.model.destination.Itinerary

@Database(
    entities =[Itinerary::class],
    version = 1
)
abstract class ItineraryDatabase : RoomDatabase() {

    abstract fun cartDao() : ItineraryDao

    companion object {
        private var INSTANCE : ItineraryDatabase? = null
        fun getInstance (context: Context) : ItineraryDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ItineraryDatabase::class.java,
                    "destination-db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}