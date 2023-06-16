package com.berwisata.travel.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berwisata.travel.data.model.response.placebylocation.PlaceByLocResponseItem
import com.berwisata.travel.databinding.ItemExploreBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PlaceByLocAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PlaceByLocAdapter.PlaceByLocViewHolder>() {

    private val itemList = mutableListOf<PlaceByLocResponseItem?>()

    inner class PlaceByLocViewHolder(private val binding: ItemExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceByLocResponseItem) {
            binding.apply {
                tvDestinationName.text  = item.placeName
                tvDestLocation.text = item.placeLoc
                Glide.with(root)
                    .load(item.placeImgurl)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceByLocViewHolder {
        return PlaceByLocViewHolder(
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

    override fun onBindViewHolder(holder: PlaceByLocViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PlaceByLocResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemPlaceListClicked(id: String)
        fun onbtnAddItineraryClicked(placeName: String, placeId: String)
    }


}