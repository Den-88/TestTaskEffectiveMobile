package com.den.shak.effectivemobile.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.den.shak.domain.model.Offer
import com.den.shak.effectivemobile.databinding.ItemOfferBinding
import com.den.shak.effectivemobile.ui.utils.OfferViewUtils

class OffersAdapter : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    private val offers = mutableListOf<Offer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    override fun getItemCount(): Int = offers.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(offers: List<Offer>?) {
        this.offers.clear()
        offers?.let {
            this.offers.addAll(it)
        }
        notifyDataSetChanged()
    }

    inner class OfferViewHolder(private val binding: ItemOfferBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(offer?.link)
                }
                itemView.context.startActivity(intent)
            }

            val iconRes = OfferViewUtils.getIconResourceById(offer?.id)
            if (iconRes != null) {
                binding.offerIcon.setImageResource(iconRes)
                binding.offerIcon.visibility = View.VISIBLE
            } else {
                binding.offerIcon.visibility = View.GONE
            }

            binding.offerTitle.text = offer?.title?.trim()

            if (offer?.button != null) {
                binding.offerLink.text = offer.button?.text
                binding.offerLink.visibility = View.VISIBLE
                binding.offerTitle.maxLines = 2

            } else {
                binding.offerTitle.maxLines = 3
                binding.offerLink.visibility = View.GONE
            }

            val position = bindingAdapterPosition
            val params = binding.root.layoutParams as RecyclerView.LayoutParams

            if (position == 0) {
                params.leftMargin = OfferViewUtils.dpToPx(16)
            }
            if (position == itemCount - 1) {
                params.rightMargin = OfferViewUtils.dpToPx(16)
            }
        }
    }
}