package com.berwisata.travel.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berwisata.travel.data.model.response.weatherrecommendation.WeatherRecommendationResponseItem
import com.berwisata.travel.databinding.ItemExploreBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PlaceByWeatherAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PlaceByWeatherAdapter.PlaceByWeatherViewHolder>() {

    private val itemList = mutableListOf<WeatherRecommendationResponseItem?>()

    inner class PlaceByWeatherViewHolder(private val binding: ItemExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherRecommendationResponseItem) {
            binding.apply {
                tvDestinationName.text  = item.placeName
                tvDestLocation.text = item.placeLoc
                Glide.with(root)
                    .load(item.placeImgurl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage)

                root.setOnClickListener {
                    itemClickListener.onItemPlaceListClicked(item.placeId)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceByWeatherViewHolder {
        return PlaceByWeatherViewHolder(
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

    override fun onBindViewHolder(holder: PlaceByWeatherViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<WeatherRecommendationResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemPlaceListClicked(id: String)
    }


}