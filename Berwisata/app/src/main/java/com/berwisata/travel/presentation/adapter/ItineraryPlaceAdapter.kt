package com.berwisata.travel.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berwisata.travel.data.model.response.specificitinerary.SpecificItineraryResponseItem
import com.berwisata.travel.databinding.ItemItineraryPlaceBinding

class ItineraryPlaceAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ItineraryPlaceAdapter.ItineraryPlaceViewHolder>() {

    private val itemList = mutableListOf<SpecificItineraryResponseItem?>()

    inner class ItineraryPlaceViewHolder(private val binding: ItemItineraryPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SpecificItineraryResponseItem) {
            binding.apply {
                tvPlaceName.text  = item.placeName

                root.setOnClickListener {
                    itemClickListener.onItemPlaceListClicked(item.placeId)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryPlaceViewHolder {
        return ItineraryPlaceViewHolder(
            ItemItineraryPlaceBinding.inflate(
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
    fun setData(data: List<SpecificItineraryResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemPlaceListClicked(id: String)
    }


}