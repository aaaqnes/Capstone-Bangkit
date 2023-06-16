package com.berwisata.travel.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.berwisata.travel.databinding.ActivityAddToItineraryBinding
import com.berwisata.travel.presentation.adapter.ItineraryAdapter
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddToItineraryActivity : AppCompatActivity(),
    ItineraryAdapter.OnItemClickListener{

    private lateinit var binding: ActivityAddToItineraryBinding
    private val itineraryViewModel: ItineraryViewModel by viewModel()
    private lateinit var itineraryAdapter: ItineraryAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupPagingRecyclerView()
        setupViewModelObservers()
    }

    private fun setupPagingRecyclerView() {
        itineraryAdapter = ItineraryAdapter(this)
        binding.rvItinerary.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itineraryAdapter
        }
    }

    private fun setupViewModelObservers() {

        auth = FirebaseAuth.getInstance()

        var userToken: String? = null
        val user: FirebaseUser? = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userToken = task.result?.token
                itineraryViewModel.getAllItinerary("Bearer $userToken")
            } else {

            }
        }


        itineraryViewModel.listItinerary.observe(this) {
            itineraryAdapter.setData(it ?: emptyList())

        }
    }

    override fun onItemPlaceListClicked(itineraryName: String) {
        val placeName = intent?.getStringExtra("placeName")
        val placeId = intent?.getStringExtra("placeId")
        auth = FirebaseAuth.getInstance()

        var userToken: String?
        val user: FirebaseUser? = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userToken = task.result?.token
                itineraryViewModel.addPlace("Bearer $userToken",
                    placeName ?: "", placeId.toString(), itineraryName
                )
                Toast.makeText(this, "Add place to Itinerary $itineraryName Success!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {

            }
        }
    }


}