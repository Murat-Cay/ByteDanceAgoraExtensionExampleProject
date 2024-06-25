package com.volvoxmobile.bytedanceagoracrashexample.bytedance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volvoxmobile.bytedanceagoracrashexample.databinding.ItemBeautyTabPageBinding
import javax.inject.Inject

class BeautyPageTitleAdapter @Inject constructor() : RecyclerView.Adapter<BeautyPageTitleAdapter.BeautyPageTitleViewHolder>() {

    private var items: List<String> = emptyList()
    private var onItemSelected: ((position: Int, text: String) -> Unit)? = null
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (position: Int, text: String) -> Unit) {
        onItemSelected = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeautyPageTitleViewHolder {
        val binding = ItemBeautyTabPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeautyPageTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeautyPageTitleViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onBindViewHolder(holder: BeautyPageTitleViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            holder.bind(position)
        } else {
            holder.bind(position, payloads)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class BeautyPageTitleViewHolder(private val binding: ItemBeautyTabPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = with(binding) {
            val item = items[position]
            binding.pageTitle = item
            if (selectedPosition == RecyclerView.NO_POSITION && position == 0) {
                selectedPosition = 0
            }
            binding.isSelectedBeauty = position == selectedPosition
            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition, "payload")
                notifyItemChanged(position, "payload")
                onItemSelected?.invoke(position, item)
            }
        }

        fun bind(position: Int, payloads: List<Any>) = with(binding) {
            binding.isSelectedBeauty = position == selectedPosition
        }
    }
}