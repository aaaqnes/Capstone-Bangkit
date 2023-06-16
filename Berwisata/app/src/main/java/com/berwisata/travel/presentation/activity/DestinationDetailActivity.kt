package com.berwisata.travel.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.berwisata.travel.databinding.ActivityDestinationDetailBinding
import com.berwisata.travel.presentation.viewmodel.DestinationViewModel
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel

class DestinationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDestinationDetailBinding
    private val destinationViewModel: DestinationViewModel by viewModel()
    private val itineraryViewModel: ItineraryViewModel by viewModel()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")

        setupViewModelObservers()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnAddItinerary.setOnClickListener {


        }
    }

    private fun setupViewModelObservers() {

        auth = FirebaseAuth.getInstance()

        var userToken: String? = null

        val user: FirebaseUser? = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userToken = task.result?.token
            } else {

            }
        }

        val id = intent.getStringExtra("id")

        destinationViewModel.getAllPlaceById(id.toString())

        destinationViewModel.placeById.observe(this) { place ->
            Glide.with(this)
                .load(place?.placeImgurl)
                .into(binding.ivImage)
            binding.tvAddress.text = place?.placeLoc
            binding.tvPlaceName.text = place?.placeName
            binding.tvDescription.text = place?.placeDesc
            binding.btnAddItinerary.setOnClickListener {
                val itineraryName = intent?.getStringExtra("itineraryName")
                if(itineraryName != null) {
                    itineraryViewModel.addPlace("Bearer $userToken",
                        place?.placeName ?: "", id.toString(), itineraryName.toString())
                    Toast.makeText(this, "Add place to Itinerary $itineraryName Success!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, AddToItineraryActivity::class.java)
                    intent.putExtra("placeId", place?.placeId)
                    intent.putExtra("placeName", place?.placeName)
                    startActivity(intent)
                }
            }
        }
    }
}