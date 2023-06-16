package com.berwisata.travel.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.berwisata.travel.databinding.ActivityItineraryPlanBinding
import com.berwisata.travel.presentation.adapter.AllPlaceAdapter
import com.berwisata.travel.presentation.adapter.ItineraryPlaceAdapter
import com.berwisata.travel.presentation.adapter.RecommendationAdapter
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItineraryPlanActivity : AppCompatActivity(),
    ItineraryPlaceAdapter.OnItemClickListener{

    private lateinit var binding: ActivityItineraryPlanBinding
    private val itineraryViewModel: ItineraryViewModel by viewModel()
    private lateinit var itineraryPlaceAdapter: ItineraryPlaceAdapter
    private lateinit var auth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItineraryPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itineraryName = intent?.getStringExtra("itineraryName")

        binding.tvTitle.text = "$itineraryName"

        setupRecyclerView()
        setupViewModelObservers()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnAddPlace.setOnClickListener {
            val intent = Intent(this, ExploreActivity::class.java)
            intent.putExtra("itineraryName", itineraryName)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        itineraryPlaceAdapter = ItineraryPlaceAdapter(this)
        binding.rvPlace.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itineraryPlaceAdapter
        }
    }

    private fun setupViewModelObservers() {
        val itineraryName = intent?.getStringExtra("itineraryName")


        auth = FirebaseAuth.getInstance()

        var userToken: String? = null

        val user: FirebaseUser? = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userToken = task.result?.token
                itineraryViewModel.getSpecificItinerary("Bearer $userToken", itineraryName.toString())
            } else {

            }
        }

        itineraryViewModel.listSpecificItinerary.observe(this) {
            itineraryPlaceAdapter.setData(it ?: emptyList())
        }
    }

    override fun onItemPlaceListClicked(id: String) {
        val intent = Intent(this, DestinationDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}