package com.volvoxmobile.bytedanceagoracrashexample.bytedance

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.volvoxmobile.bytedanceagoracrashexample.Localizations
import com.volvoxmobile.bytedanceagoracrashexample.R

data class PageInfo(
    val name: String,
    val itemList: List<ItemInfo>,
    var isSelected: Boolean = false
) {
    fun selectedBackground(context: Context) = if (isSelected) ContextCompat.getColor(context, R.color.purple_background_color) else ContextCompat.getColor(context, android.R.color.transparent)
    fun selectedTextColor(context: Context) = if (isSelected) ContextCompat.getColor(context, R.color.wheel_text_color) else ContextCompat.getColor(context, R.color.cool_grey)
}

data class ItemInfo(
    val name: String,
    @DrawableRes val icon: Int,
    var value: Float = 0.0f,
    val onValueChanged: (value: Float) -> Unit = {},
    var isSelected: Boolean = false,
    var withPadding: Boolean = true,
    var hasNavigation: Boolean = false,
    val subItemInfo: List<SubItemInfo> = emptyList(),
    val unique: String = ""
    ) {

    fun deselectAllSubItems() {
        subItemInfo.forEach { subItem ->
            val subItemSelected = subItem.subItemInfo2.filter { it.isSelected }
            subItemSelected.forEach {
                it.isSelected = false
            }
            subItem.subItemInfo2.forEach { subSubItem ->
                subSubItem.isSelected = false
            }
            subItem.isSelected = false
        }
    }

    fun setImage(context: Context): Drawable? = ContextCompat.getDrawable(context, icon)

    private fun isSubItemSelectedVisibility(): Boolean = if (hasNavigation) subItemInfo.any { it.isSelected } else true

    fun selectedTextColor(context: Context): Int = if (isSelected && isSubItemSelectedVisibility()) ContextCompat.getColor(context, R.color.wheel_text_color) else ContextCompat.getColor(context, R.color.cool_grey)
    fun selectedImg(context: Context): Int = if (isSelected && name != Localizations.get(context, "beauty_item_none") && isSubItemSelectedVisibility()) View.VISIBLE else View.INVISIBLE

    fun cardElevation(context: Context): Int = if (isSelected && name != Localizations.get(context, "beauty_item_none") && isSubItemSelectedVisibility()) R.dimen.dimen_4dp else R.dimen.dimen_0dp
}

data class SubItemInfo(
    val name: String,
    @DrawableRes val icon: Int,
    var value: Float = 0.0f,
    val onValueChanged: (value: Float) -> Unit = {},
    var isSelected: Boolean = false,
    var hasNavigation: Boolean = false,
    val unique: String,
    val subItemInfo2: List<SubItemInfo2> = emptyList(),
    val tintColor: String = ""
) {

    fun deselectAllSubItems() {
        subItemInfo2.forEach {
            it.isSelected = false
        }
    }

    fun setImage(context: Context):Drawable? = ContextCompat.getDrawable(context, icon)

    private fun isSubItemSelectedVisibility(): Boolean = if (hasNavigation) subItemInfo2.any { it.isSelected } else true

    fun selectedTextColor(context: Context): Int = if (isSelected && isSubItemSelectedVisibility() && name != Localizations.get(context, "beauty_item_none")) ContextCompat.getColor(context, R.color.wheel_text_color) else ContextCompat.getColor(context, R.color.cool_grey)
    fun selectedImg(context: Context): Int = if (isSelected && isSubItemSelectedVisibility() && name != Localizations.get(context, "beauty_item_none")) View.VISIBLE else View.INVISIBLE

    fun cardElevation(context: Context): Int = if (isSelected && name != Localizations.get(context, "beauty_item_none")) R.dimen.dimen_4dp else R.dimen.dimen_0dp

    fun setTintColor(appCompatImageView: ImageView, context: Context) = if (isSelected.not() && tintColor.isNotEmpty()) {
        appCompatImageView.setColorFilter(Color.parseColor(tintColor))
    }else if(isSelected && tintColor.isNotEmpty()) {
        appCompatImageView.setColorFilter(Color.parseColor(tintColor))
    }  else  appCompatImageView.clearColorFilter()
}

data class SubItemInfo2(
    val name: String,
    @DrawableRes val icon: Int,
    var value: Float = 0.0f,
    val onValueChanged: (value: Float) -> Unit,
    var isSelected: Boolean = false,
    val unique: String,
    val tintColor: String = ""
) {
    fun setImage(context: Context):Drawable? = ContextCompat.getDrawable(context, icon)

    fun selectedTextColor(context: Context): Int = if (isSelected) ContextCompat.getColor(context, R.color.wheel_text_color) else ContextCompat.getColor(context, R.color.cool_grey)
    fun selectedImg(context: Context): Int = if (isSelected && name != Localizations.get(context, "beauty_item_none")) View.VISIBLE else View.INVISIBLE

    fun cardElevation(context: Context): Int = if (isSelected && name != Localizations.get(context, "beauty_item_none")) R.dimen.dimen_4dp else R.dimen.dimen_0dp

    fun setTintColor(appCompatImageView: ImageView) = if (isSelected.not() && tintColor.isNotEmpty())
        appCompatImageView.setColorFilter(Color.parseColor(tintColor)) else appCompatImageView.clearColorFilter()
}