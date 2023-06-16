package com.berwisata.travel.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.berwisata.travel.databinding.FragmentItineraryBinding
import com.berwisata.travel.presentation.activity.CreateNewPlanActivity
import com.berwisata.travel.presentation.activity.DestinationDetailActivity
import com.berwisata.travel.presentation.activity.ItineraryPlanActivity
import com.berwisata.travel.presentation.adapter.ItineraryAdapter
import com.berwisata.travel.presentation.adapter.RecommendationAdapter
import com.berwisata.travel.presentation.viewmodel.ItineraryViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItineraryFragment : Fragment(),
    ItineraryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentItineraryBinding
    private val itineraryViewModel: ItineraryViewModel by viewModel()
    private lateinit var itineraryAdapter: ItineraryAdapter
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItineraryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddItinerary.setOnClickListener {
            startActivity(Intent(requireContext(), CreateNewPlanActivity::class.java))
        }

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

        val user = firebaseAuth.currentUser

        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val idToken = task.result?.token
                itineraryViewModel.getAllItinerary("Bearer $idToken")
            } else {
                Log.w("testBearer", "Failed to get ID Token", task.exception)
            }
        }


        itineraryViewModel.listItinerary.observe(viewLifecycleOwner) {
            itineraryAdapter.setData(it ?: emptyList())

        }
    }

    override fun onItemPlaceListClicked(itineraryName: String) {
        val intent = Intent(requireContext(), ItineraryPlanActivity::class.java)
        intent.putExtra("itineraryName", itineraryName)
        startActivity(intent)
    }
}