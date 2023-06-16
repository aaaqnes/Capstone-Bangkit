package com.berwisata.travel.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.berwisata.travel.BerwisataApp
import com.berwisata.travel.databinding.FragmentHomeBinding
import com.berwisata.travel.presentation.activity.AllPlaceActivity
import com.berwisata.travel.presentation.activity.DestinationDetailActivity
import com.berwisata.travel.presentation.activity.ExploreActivity
import com.berwisata.travel.presentation.activity.ExploreByWeather
import com.berwisata.travel.presentation.adapter.RecommendationAdapter
import com.berwisata.travel.presentation.viewmodel.DestinationViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), RecommendationAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private val destinationViewModel: DestinationViewModel by viewModel()
    private lateinit var recommendationAdapter: RecommendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val email = currentUser?.email

        // Set the email to the TextView
        binding.userName.text = email ?: "No Email Found"

//        binding.cvExplore.setOnClickListener {
//            startActivity(Intent(requireActivity(), ExploreActivity::class.java))
//        }

        binding.searchView.setOnClickListener {
            startActivity(Intent(BerwisataApp.context, AllPlaceActivity::class.java))
        }

        binding.tvExplore.setOnClickListener {
            startActivity(Intent(requireActivity(), ExploreByWeather::class.java))
        }

        setupPagingRecyclerView()
        setupViewModelObservers()
    }

    private fun setupPagingRecyclerView() {
        recommendationAdapter = RecommendationAdapter(this)
        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }
    }

    private fun setupViewModelObservers() {
        destinationViewModel.getRecommendPlace()

        destinationViewModel.listRecommendPlace.observe(viewLifecycleOwner) {
            recommendationAdapter.setData(it)
        }
    }

    override fun onItemPlaceListClicked(id: String) {
        val intent = Intent(requireContext(), DestinationDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}