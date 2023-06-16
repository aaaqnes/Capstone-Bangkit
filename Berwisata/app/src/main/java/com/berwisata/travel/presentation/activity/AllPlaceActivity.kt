package com.berwisata.travel.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.berwisata.travel.databinding.ActivityAllPlaceBinding
import com.berwisata.travel.presentation.adapter.AllPlaceAdapter
import com.berwisata.travel.presentation.viewmodel.DestinationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllPlaceActivity : AppCompatActivity(),
    AllPlaceAdapter.OnItemClickListener {

    private lateinit var binding: ActivityAllPlaceBinding
    private val destinationViewModel: DestinationViewModel by viewModel()
    private lateinit var allPlaceAdapter: AllPlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRv()
        setupVmObserver()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun setupRv() {
        allPlaceAdapter = AllPlaceAdapter(this)
        binding.rvAllDestination.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allPlaceAdapter
        }
    }

    private fun setupVmObserver() {
        destinationViewModel.getAllPlace()

        destinationViewModel.listPlace.observe(this) {
            allPlaceAdapter.setData(it)
        }
    }

    override fun onItemPlaceListClicked(id: String) {
        val intent = Intent(this, DestinationDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onbtnAddItineraryClicked(placeName: String, placeId: String) {
        val intent = Intent(this, AddToItineraryActivity::class.java)
        intent.putExtra("placeId", placeId)
        intent.putExtra("placeName", placeName)
        startActivity(intent)
    }
}