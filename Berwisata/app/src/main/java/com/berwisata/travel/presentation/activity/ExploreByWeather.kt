package com.berwisata.travel.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.berwisata.travel.data.model.request.UserCityRequest
import com.berwisata.travel.databinding.ActivityExploreByWeatherBinding
import com.berwisata.travel.presentation.adapter.PlaceByWeatherAdapter
import com.berwisata.travel.presentation.viewmodel.DestinationViewModel
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreByWeather : AppCompatActivity(),
    PlaceByWeatherAdapter.OnItemClickListener {

    private lateinit var binding: ActivityExploreByWeatherBinding
    private val itineraryViewModel: ItineraryViewModel by viewModel()
    private val destinationViewModel: DestinationViewModel by viewModel()
    private lateinit var placeByWeatherAdapter: PlaceByWeatherAdapter
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreByWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("locPref", Context.MODE_PRIVATE)

        auth = FirebaseAuth.getInstance()

        var userToken: String? = null

        val user: FirebaseUser? = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userToken = task.result?.token
                when (sharedPref.getString("location", null)) {
                    "jakpus" -> {
                        destinationViewModel.getRecomByWeather("Bearer $userToken", UserCityRequest("Jakarta Pusat"))
                    }
                    "jakbar" -> {
                        destinationViewModel.getRecomByWeather("Bearer $userToken", UserCityRequest("Jakarta Barat"))
                    }
                    "jaksel" -> {
                        destinationViewModel.getRecomByWeather("Bearer $userToken", UserCityRequest("Jakarta Selatan"))
                    }
                    "jaktim" -> {
                        destinationViewModel.getRecomByWeather("Bearer $userToken", UserCityRequest("Jakarta Timur"))
                    }
                    "jakut" -> {
                        destinationViewModel.getRecomByWeather("Bearer $userToken", UserCityRequest("Jakarta Utara"))
                    }
                }
            } else {

            }
        }

        setupRv()
        setupVmObserver()




        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRv() {
        placeByWeatherAdapter = PlaceByWeatherAdapter(this)
        binding.rvExplore.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeByWeatherAdapter
        }
    }

    private fun setupVmObserver() {
        destinationViewModel.listByWeather.observe(this) {
            placeByWeatherAdapter.setData(it ?: emptyList())
        }
    }

    override fun onItemPlaceListClicked(id: String) {
        val intent = Intent(this, DestinationDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}