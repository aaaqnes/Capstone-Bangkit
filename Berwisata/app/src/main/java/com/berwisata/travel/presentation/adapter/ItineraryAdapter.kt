package com.berwisata.travel.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berwisata.travel.data.model.response.allitinerary.AllItineraryResponseItem
import com.berwisata.travel.data.model.response.place.AllPlaceResponseItem
import com.berwisata.travel.databinding.ItemExploreBinding
import com.berwisata.travel.databinding.ItemItineraryBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ItineraryAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ItineraryAdapter.ItineraryPlaceViewHolder>() {

    private val itemList = mutableListOf<AllItineraryResponseItem?>()

    inner class ItineraryPlaceViewHolder(private val binding: ItemItineraryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllItineraryResponseItem) {
            binding.apply {
                tvTitle.text  = item.itineraryName
                tvDate.text = item.itineraryDate

                root.setOnClickListener {
                    itemClickListener.onItemPlaceListClicked(item.itineraryName)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryPlaceViewHolder {
        return ItineraryPlaceViewHolder(
            ItemItineraryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItineraryPlaceViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AllItineraryResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemPlaceListClicked(itineraryName: String)
    }


}