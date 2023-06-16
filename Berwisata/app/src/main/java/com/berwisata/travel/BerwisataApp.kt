package com.berwisata.travel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.berwisata.travel.data.local.room.ItineraryDatabase
import com.berwisata.travel.data.model.response.createitinerary.CreateItineraryResponse
import com.berwisata.travel.data.remote.ApiService
import com.berwisata.travel.data.repository.BerwisataRepository
import com.berwisata.travel.presentation.viewmodel.DestinationViewModel
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import com.tanitama.green.data.remote.RetrofitClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

class BerwisataApp : Application() {
    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@BerwisataApp)
            modules(
                appModule,
                vmModule,
                repositoryModule
            )
        }


    }

    private val appModule = module {
        single { ItineraryDatabase.getInstance(context) }

        single {
            get<ItineraryDatabase>().cartDao()
        }
    }

    private val vmModule = module {
        viewModel { DestinationViewModel(get()) }
        viewModel { ItineraryViewModel(get())}
    }

    private val repositoryModule = module {
        single { RetrofitClient.createService<ApiService>() }
        single { BerwisataRepository(get()) }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}