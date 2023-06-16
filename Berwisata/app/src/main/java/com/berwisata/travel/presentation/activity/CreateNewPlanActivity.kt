package com.berwisata.travel.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.berwisata.travel.R
import com.berwisata.travel.data.model.request.ItineraryRequestBody
import com.berwisata.travel.databinding.ActivityCreateNewPlanBinding
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateNewPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewPlanBinding
    private val itineraryViewModel: ItineraryViewModel by viewModel()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        var userToken: String? = null
        val user: FirebaseUser? = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userToken = task.result?.token
            } else {

            }
        }

        binding.btnCreateNewPlan.setOnClickListener {
            val itineraryName = binding.etPlanName.text.toString()

            val datePicker = binding.dpDate
            val day = datePicker.dayOfMonth
            val month = datePicker.month
            val year = datePicker.year

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
            val dateString = format.format(calendar.time)

            itineraryViewModel.createItinerary("Bearer $userToken", itineraryName, dateString)

            val intent = Intent(this, ItineraryPlanActivity::class.java)
            intent.putExtra("itineraryName", itineraryName)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}