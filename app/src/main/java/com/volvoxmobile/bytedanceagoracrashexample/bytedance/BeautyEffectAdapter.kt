package com.volvoxmobile.bytedanceagoracrashexample.bytedance

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.volvoxmobile.bytedanceagoracrashexample.Localizations
import com.volvoxmobile.bytedanceagoracrashexample.R
import com.volvoxmobile.bytedanceagoracrashexample.databinding.ItemBeautyEffectBinding
import javax.inject.Inject

class BeautyEffectAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemInfoList: List<ItemInfo> = emptyList()
    private var subItemInfoList: List<SubItemInfo> = emptyList()
    private var subItemInfo2List: List<SubItemInfo2> = emptyList()

    private var itemInfoAndPosition: ((itemInfo: ItemInfo, position: Int) -> Unit)? = null
    private var subItemInfoAndPosition: ((itemInfo: SubItemInfo, position: Int) -> Unit)? = null
    private var subItemInfo2AndPosition: ((itemInfo: SubItemInfo2, position: Int) -> Unit)? = null

    private var removeComposerItemInfo: ((itemInfo: ItemInfo) -> Unit)? = null
    private var removeComposerSubItemInfo: ((subItemInfo: SubItemInfo) -> Unit)? = null
    private var removeComposerSubItemInfo2: ((subItemInfo2: SubItemInfo2) -> Unit)? = null

    private var removeComposerSubItemInfoUnique: ((unique: String) -> Unit)? = null

    private var subItemTitle: String = ""
    private var itemInfoTitle: String = ""

    companion object {
        private const val ITEM_INFO_VIEW_TYPE = 0
        private const val SUB_ITEM_INFO_VIEW_TYPE = 1
        private const val SUB_ITEM_INFO2_VIEW_TYPE = 2
    }

    fun removeComposerItemInfo(listener: (itemInfo: ItemInfo) -> Unit) {
        removeComposerItemInfo = listener
    }

    fun removeComposerSubItemInfo(listener: (subItemInfo: SubItemInfo) -> Unit) {
        removeComposerSubItemInfo = listener
    }

    fun removeComposerSubItemInfoUnique(listener: (unique: String) -> Unit) {
        removeComposerSubItemInfoUnique = listener
    }

    fun removeComposerSubItemInfo2(listener: (subItemInfo2: SubItemInfo2) -> Unit) {
        removeComposerSubItemInfo2 = listener
    }

    fun setItemInfoAndPosition(listener: (itemInfo: ItemInfo, position: Int) -> Unit) {
        itemInfoAndPosition = listener
    }

    fun setSubItemInfoAndPosition(listener: (itemInfo: SubItemInfo, position: Int) -> Unit) {
        subItemInfoAndPosition = listener
    }

    fun setSubItemInfo2AndPosition(listener: (itemInfo: SubItemInfo2, position: Int) -> Unit) {
        subItemInfo2AndPosition = listener
    }
    init {
        setHasStableIds(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(itemInfoList: List<ItemInfo>? = null, subItemInfoList: List<SubItemInfo>? = null, subItemInfo2List: List<SubItemInfo2>? = null, itemInfoTitleValue: String? = null, subItemTitleValue: String? = null) {
        this.itemInfoList = itemInfoList ?: emptyList()
        this.subItemInfoList = subItemInfoList ?: emptyList()
        this.subItemInfo2List = subItemInfo2List ?: emptyList()
        subItemTitle = subItemTitleValue.orEmpty()
        itemInfoTitle = itemInfoTitleValue.orEmpty()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetAllSelections(
        itemInfoList: List<ItemInfo>? = null,
        subItemInfoList: List<SubItemInfo>? = null,
        subItemInfo2List: List<SubItemInfo2>? = null
    ) {
        itemInfoList?.forEach { item ->
            item.isSelected = false
            item.value = 0f
            item.onValueChanged.invoke(0.5f)
            item.deselectAllSubItems()
        }
        subItemInfoList?.forEach { item ->
            item.isSelected = false
            item.value = 0f
            item.onValueChanged.invoke(0.5f)
            item.deselectAllSubItems()
        }
        subItemInfo2List?.forEach { item ->
            item.isSelected = false
            item.value = 0f
            item.onValueChanged.invoke(0.5f)
        }
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            position < itemInfoList.size -> ITEM_INFO_VIEW_TYPE
            position < itemInfoList.size + subItemInfoList.size -> SUB_ITEM_INFO_VIEW_TYPE
            else -> SUB_ITEM_INFO2_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemBeautyEffectBinding = DataBindingUtil.inflate(inflater, R.layout.item_beauty_effect, parent, false)
        return when (viewType) {
            ITEM_INFO_VIEW_TYPE -> BeautyEffectItemInfoViewHolder(binding)
            SUB_ITEM_INFO_VIEW_TYPE -> BeautyEffectSubItemInfoViewHolder(binding)
            SUB_ITEM_INFO2_VIEW_TYPE -> BeautyEffectSubItemInfo2ViewHolder(binding)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_INFO_VIEW_TYPE -> {
                val itemInfo = itemInfoList[position]
                (holder as BeautyEffectItemInfoViewHolder).bind(itemInfo)
            }
            SUB_ITEM_INFO_VIEW_TYPE -> {
                val subItemInfo = subItemInfoList[position - itemInfoList.size]
                (holder as BeautyEffectSubItemInfoViewHolder).bind(subItemInfo)
            }
            SUB_ITEM_INFO2_VIEW_TYPE -> {
                val subItemInfo2 = subItemInfo2List[position - itemInfoList.size - subItemInfoList.size]
                (holder as BeautyEffectSubItemInfo2ViewHolder).bind(subItemInfo2)
            }
        }
    }

    override fun getItemId(position: Int): Long  = position.toLong()

    override fun getItemCount(): Int = itemInfoList.size + subItemInfoList.size + subItemInfo2List.size

    inner class BeautyEffectItemInfoViewHolder(val binding: ItemBeautyEffectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemInfo: ItemInfo) = with(binding) {
            val position = adapterPosition
            val currentItem = itemInfoList[position]
            val context = root.context

            imageSelectedValue = currentItem.selectedImg(context)
            ivIcon.setImageDrawable(itemInfo.setImage(context))
            textColor = currentItem.selectedTextColor(context)
            itemText = currentItem.name
            cardElevationValue = currentItem.cardElevation(context)

            root.setOnClickListener {
                val itemOldSelected = itemInfoList.find { it.isSelected }
                val oldSelectedPosition = itemInfoList.indexOf(itemOldSelected)

                if (!itemInfo.hasNavigation) {
                    when (itemInfoTitle) {
                        Localizations.get(context, "beauty_group_background_blur"), -> {
                            if (currentItem.isSelected) {
                                currentItem.isSelected = false
                                removeComposerItemInfo?.invoke(currentItem)
                            } else {
                                itemOldSelected?.isSelected = false
                                currentItem.isSelected = true
                                itemInfoAndPosition?.invoke(itemInfo, position)
                            }
                        }
                        else -> {
                            if (currentItem.isSelected) {
                                itemInfo.value = 0.5f
                                itemInfo.onValueChanged.invoke(0.5f)
                                currentItem.isSelected = false
                                removeComposerItemInfo?.invoke(currentItem)
                            } else {
                                currentItem.isSelected = true
                                itemInfoAndPosition?.invoke(itemInfo, position)
                            }
                        }
                    }
                } else {
                    currentItem.isSelected = true
                    itemInfoAndPosition?.invoke(itemInfo, position)
                }
                if (oldSelectedPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(oldSelectedPosition)
                }
                notifyItemChanged(position)
            }
        }
    }

    inner class BeautyEffectSubItemInfoViewHolder(val binding: ItemBeautyEffectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subItemInfo: SubItemInfo) = with(binding) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) return@with
            val currentItem = subItemInfoList[position]
            val context = root.context
            subItemInfo.setTintColor(ivIcon, context)

            imageSelectedValue = currentItem.selectedImg(context)
            ivIcon.setImageDrawable(subItemInfo.setImage(context))
            textColor = currentItem.selectedTextColor(context)
            itemText = currentItem.name
            cardElevationValue = currentItem.cardElevation(context)

            root.setOnClickListener {
                val itemOldSelected = subItemInfoList.find { it.isSelected }
                val oldSelectedPosition = subItemInfoList.indexOf(itemOldSelected)

                if (!subItemInfo.hasNavigation) {
                    when (subItemTitle) {
                        Localizations.get(context, "make_up_item_lenses"),
                        Localizations.get(context, "make_up_item_LipColor"),
                        Localizations.get(context, "make_up_item_HairColor"),
                        Localizations.get(context, "make_up_item_blusher"),
                        Localizations.get(context, "make_up_item_contour"),
                        Localizations.get(context, "make_up_item_eyebrow"), -> {
                            if (currentItem.isSelected) {
                                currentItem.isSelected = false
                                removeComposerSubItemInfoUnique?.invoke(currentItem.unique)
                            } else {
                                itemOldSelected?.isSelected = false
                                currentItem.isSelected = true
                                subItemInfoAndPosition?.invoke(subItemInfo, position)
                            }
                        }
                        else -> {
                            if (currentItem.isSelected) {
                                subItemInfo.value = 0.5f
                                subItemInfo.onValueChanged.invoke(0.5f)
                                currentItem.isSelected = false
                                removeComposerSubItemInfo?.invoke(currentItem)
                            } else {
                                currentItem.isSelected = true
                                subItemInfoAndPosition?.invoke(subItemInfo, position)
                            }
                        }
                    }
                } else {
                    currentItem.isSelected = true
                    subItemInfoAndPosition?.invoke(subItemInfo, position)
                }
                if (oldSelectedPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(oldSelectedPosition)
                }
                notifyItemChanged(position)
            }
        }
    }

    inner class BeautyEffectSubItemInfo2ViewHolder(val binding: ItemBeautyEffectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subItemInfo2: SubItemInfo2) = with(binding) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) return@with
            val context = root.context

            val currentItem = subItemInfo2List[position]

            imageSelectedValue = currentItem.selectedImg(context)
            ivIcon.setImageDrawable(subItemInfo2.setImage(context))
            textColor = currentItem.selectedTextColor(context)
            itemText = currentItem.name
            cardElevationValue = currentItem.cardElevation(context)
            subItemInfo2.setTintColor(ivIcon)

            root.setOnClickListener {
                val itemOldSelected = subItemInfo2List.find { it.isSelected }
                val oldSelectedPosition = subItemInfo2List.indexOf(itemOldSelected)

                if (currentItem.isSelected) {
                    subItemInfo2.value = 0.5f
                    subItemInfo2.onValueChanged.invoke(0.5f)
                    currentItem.isSelected = false
                    removeComposerSubItemInfo2?.invoke(currentItem)
                } else {
                    itemOldSelected?.isSelected = false
                    currentItem.isSelected = true
                    subItemInfo2AndPosition?.invoke(subItemInfo2, position)
                }

                if (oldSelectedPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(oldSelectedPosition)
                }

                notifyItemChanged(position)
            }
        }
    }
}