package com.berwisata.travel.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berwisata.travel.data.model.response.recommendplace.RecommendPlaceResponseItem
import com.berwisata.travel.databinding.ItemRecommendationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RecommendationAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    private val itemList = mutableListOf<RecommendPlaceResponseItem?>()

    inner class RecommendationViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecommendPlaceResponseItem) {
            binding.apply {
                Glide.with(root)
                    .load(item.placeImgurl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivRecommendation)
                tvPlaceName.text = item.placeName

                root.setOnClickListener {
                    itemClickListener.onItemPlaceListClicked(item.placeId)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        return RecommendationViewHolder(
            ItemRecommendationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<RecommendPlaceResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemPlaceListClicked(id: String)
    }


}