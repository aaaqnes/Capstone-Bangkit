package com.berwisata.travel.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.berwisata.travel.R
import com.berwisata.travel.databinding.ActivityExploreBinding
import com.berwisata.travel.presentation.adapter.AllPlaceAdapter
import com.berwisata.travel.presentation.adapter.PlaceByLocAdapter
import com.berwisata.travel.presentation.viewmodel.DestinationViewModel
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreActivity : AppCompatActivity(),
    PlaceByLocAdapter.OnItemClickListener {

    private lateinit var binding: ActivityExploreBinding
    private val itineraryViewModel: ItineraryViewModel by viewModel()
    private val destinationViewModel: DestinationViewModel by viewModel()
    private lateinit var placeByLocAdapter: PlaceByLocAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("locPref", Context.MODE_PRIVATE)

        setupRv()
        setupVmObserver()



        when (sharedPref.getString("location", null)) {
            "jakpus" -> {
                destinationViewModel.getAllPlaceByLoc("jakpus")
                binding.tvTitle.text = "Tourist Attraction at Jakarta Pusat"
            }
            "jakbar" -> {
                destinationViewModel.getAllPlaceByLoc("jakbar")
                binding.tvTitle.text = "Tourist Attraction at Jakarta Barat"
            }
            "jaksel" -> {
                destinationViewModel.getAllPlaceByLoc("jaksel")
                binding.tvTitle.text = "Tourist Attraction at Jakarta Selatan"

            }
            "jaktim" -> {
                destinationViewModel.getAllPlaceByLoc("jaktim")
                binding.tvTitle.text = "Tourist Attraction at Jakarta Timur"

            }
            "jakut" -> {
                destinationViewModel.getAllPlaceByLoc("jakut")
                binding.tvTitle.text = "Tourist Attraction at Jakarta Utara"

            }
        }


        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRv() {
        placeByLocAdapter = PlaceByLocAdapter(this)
        binding.rvExplore.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeByLocAdapter
        }
    }

    private fun setupVmObserver() {
        destinationViewModel.listPlaceByLoc.observe(this) {
            placeByLocAdapter.setData(it ?: emptyList())
        }
    }

    override fun onItemPlaceListClicked(id: String) {
        val itineraryName = intent?.getStringExtra("itineraryName")

        val intent = Intent(this, DestinationDetailActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("itineraryName", itineraryName)
        startActivity(intent)
    }

    override fun onbtnAddItineraryClicked(placeName: String, placeId: String) {
        val intent = Intent(this, AddToItineraryActivity::class.java)
        intent.putExtra("placeId", placeId)
        intent.putExtra("placeName", placeName)
        startActivity(intent)
    }

}