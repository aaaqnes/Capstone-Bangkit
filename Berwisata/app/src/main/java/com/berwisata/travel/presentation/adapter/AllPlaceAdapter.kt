package com.berwisata.travel.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berwisata.travel.data.model.response.place.AllPlaceResponseItem
import com.berwisata.travel.databinding.ItemExploreBinding
import com.berwisata.travel.databinding.ItemRecommendationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AllPlaceAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AllPlaceAdapter.AllPlaceViewHolder>() {

    private val itemList = mutableListOf<AllPlaceResponseItem?>()

    inner class AllPlaceViewHolder(private val binding: ItemExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllPlaceResponseItem) {
            binding.apply {
                tvDestinationName.text  = item.placeName
                tvDestLocation.text = item.placeLoc
                Glide.with(root)
                    .load(item.placeImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage)

                btnAddToItinerary.setOnClickListener {
                    itemClickListener.onbtnAddItineraryClicked(item.placeName, item.placeId)
                }

                root.setOnClickListener {
                    itemClickListener.onItemPlaceListClicked(item.placeId)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPlaceViewHolder {
        return AllPlaceViewHolder(
            ItemExploreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: AllPlaceViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AllPlaceResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemPlaceListClicked(id: String)
        fun onbtnAddItineraryClicked(placeName: String, placeId: String)
    }


}