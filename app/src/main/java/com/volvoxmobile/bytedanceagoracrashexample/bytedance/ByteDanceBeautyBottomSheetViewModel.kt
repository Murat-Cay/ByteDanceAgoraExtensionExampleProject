package com.volvoxmobile.bytedanceagoracrashexample.bytedance

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fun2lite.app.ui.home.party.agent.bytedance.Quad
import com.volvoxmobile.bytedanceagoracrashexample.Localizations
import com.volvoxmobile.bytedanceagoracrashexample.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ByteDanceBeautyBottomSheetViewModel @Inject constructor() : ViewModel() {

    private val _isSelectedItemIndex: MutableLiveData<Quad<Int, Int, Int, Int>> = MutableLiveData(
        Quad(0,0,0, 0)
    )
    val isSelectedItemIndex: LiveData<Quad<Int, Int, Int, Int>> = _isSelectedItemIndex

    private val _navigateRvAdapterIndex: MutableLiveData<Int> = MutableLiveData()
    val navigateRvAdapterIndex: LiveData<Int> = _navigateRvAdapterIndex

    private val _readyEffect: MutableLiveData<Boolean> = MutableLiveData()
    val readyEffect: LiveData<Boolean> = _readyEffect

    var itemInfoAnySelected = false
    var subItemInfoAnySelected = false
    var subItemInfo2AnySelected = false

    private var pageInfoCache: List<PageInfo>

    private val byteDanceBeautySdk = ByteDanceBeautySDK
    val beautyConfig = byteDanceBeautySdk.beautyConfig

    init {
        pageInfoCache = emptyList()
    }

    fun updateReadyEffect(ready: Boolean) {
        _readyEffect.value = ready
    }

    fun isSelectedItemAny() : Boolean {
        pageInfoCache.forEach { pageInfo ->
            pageInfo.itemList.forEach {
                if (it.unique != "ready_effects") {
                    if (it.isSelected) {
                        itemInfoAnySelected = true
                        return@forEach
                    }
                }
            }
            pageInfo.itemList.forEach { itemInfo ->
                subItemInfoAnySelected = itemInfo.subItemInfo.any { it.isSelected }
                itemInfo.subItemInfo.forEach { subItemInfo ->
                    subItemInfo2AnySelected = subItemInfo.subItemInfo2.any { it.isSelected }
                }
            }
        }
        return itemInfoAnySelected || subItemInfoAnySelected || subItemInfo2AnySelected
    }

    fun allDisabledEffects() {
        beautyConfig.removeAllEffect()
        pageInfoCache.forEach { pageInfo ->
            pageInfo.itemList.forEach { itemInfo ->
                itemInfo.isSelected = false
                itemInfo.subItemInfo.forEach { subItemInfo ->
                    subItemInfo.isSelected = false
                    subItemInfo.subItemInfo2.forEach { subItemInfo2 ->
                        subItemInfo2.isSelected = false
                    }
                }
            }
        }
    }

    fun updateRvAdapterIndex(position: Int) {
        _navigateRvAdapterIndex.value = position
    }
    fun setSelectedItemInfo(pageIndex: Int, itemIndex: Int, subItemIndex: Int, subItemIndex2: Int) {
        _isSelectedItemIndex.value = Quad(pageIndex, itemIndex, subItemIndex, subItemIndex2)
    }

    fun removeComposerWithItemName(itemName:String, context: Context) {
        beautyConfig.getPathForProperty(itemName, context)
    }

    fun removeComposerWithItemUnique(unique: String) {
        beautyConfig.getPathForUnique(unique)
    }

    fun getPageTitle(context: Context): MutableList<String> {
        val pageInfo = onPageListCreate(context)
        return pageInfo.map { it.name }.toMutableList()
    }

    fun getItemInfo(pageIndex: Int): MutableList<ItemInfo> {
        return pageInfoCache[pageIndex].itemList.toMutableList()
    }

    fun getSubItemInfo(pageIndex: Int, itemIndex: Int): MutableList<SubItemInfo> {
        return pageInfoCache[pageIndex].itemList[itemIndex].subItemInfo.toMutableList()
    }

    fun getSubItemInfo2(pageIndex: Int, itemIndex: Int, position: Int): MutableList<SubItemInfo2> {
        if (pageIndex < 0 || pageIndex >= pageInfoCache.size) {
            return mutableListOf()
        }
        val page = pageInfoCache[pageIndex]
        if (itemIndex < 0 || itemIndex >= page.itemList.size) {
            return mutableListOf()
        }
        val item = page.itemList[itemIndex]

        if (position < 0 || position >= item.subItemInfo.size) {
            return mutableListOf()
        }
        val subItem = item.subItemInfo[position]
        return subItem.subItemInfo2.toMutableList()
    }

    fun isSliderVisibility(hasNavigation: Boolean, title: String, context: Context, unique: String? = null): Boolean {
        return when {
            title == Localizations.get(context, "beauty_item_none") -> false
            title == Localizations.get(context, "beauty_item_face") -> false
            title == Localizations.get(context, "beauty_group_beauty_effects") -> false
            title == Localizations.get(context, "beauty_group_image_quality") -> false
            title == Localizations.get(context, "beauty_group_makeup") -> false
            title == Localizations.get(context, "beauty_group_background_blur") -> false
            title == Localizations.get(context, "background_blur_item_default") -> false
            title == Localizations.get(context, "background_blur_item_enhanced") -> false
            title == Localizations.get(context, "beauty_group_default_filters") -> false
            title == Localizations.get(context, "ready_effects") -> false
            unique == "make_up_item_HairColor" -> false
            hasNavigation -> false
            else -> true
        }
    }

    private fun onPageListCreate(context: Context): List<PageInfo> {
        if (pageInfoCache.isEmpty()) {
            pageInfoCache = listOf(
                PageInfo(Localizations.get(context = context, "beauty_group_beauty_effects"),
                    listOf(ItemInfo(
                        Localizations.get(context = context, "beauty_item_none"),
                        R.drawable.ic_beauty_none,
                        onValueChanged = { _ ->
                            beautyConfig.pagePosition0to0Remover()
                        }),
                        ItemInfo(Localizations.get(context = context, "beauty_item_face"),
                            R.drawable.ic_beauty_item_face,
                            hasNavigation = true,
                            subItemInfo = listOf(SubItemInfo(
                                name = Localizations.get(context = context, "beauty_item_none"),
                                icon = R.drawable.ic_beauty_none,
                                onValueChanged = { _ ->
                                    beautyConfig.pagePosition0to1Remover()
                                },
                                unique = "PE_TurnOff"
                            ), SubItemInfo(name = Localizations.get(
                                context = context,
                                "beauty_item_jawbone"
                            ),
                                icon = R.drawable.ic_beauty_item_face,
                                unique = "PE_000",
                                value = beautyConfig.shrinkJawbone,
                                onValueChanged = { value ->
                                    beautyConfig.shrinkJawbone = value
                                }
                            ), SubItemInfo(
                                name = Localizations.get(
                                    context = context,
                                    "beauty_item_deform"
                                ),
                                icon = R.drawable.ic_beauty_item_face,
                                value = beautyConfig.shrinkCheekbone,
                                onValueChanged = { value ->
                                    beautyConfig.shrinkCheekbone = value
                                },
                                unique = "PE_001"
                            )
                            )
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "beauty_item_eye"),
                            R.drawable.ic_beauty_item_eye,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = {
                                        beautyConfig.pagePosition0to2Remover()
                                    },
                                    unique = "PE_TurnOff"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "beauty_item_eye_shadow"
                                    ),
                                    icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                    unique = "beauty_item_eye_shadow",
                                    hasNavigation = true,
                                    subItemInfo2 = listOf(
                                        SubItemInfo2(
                                            name = Localizations.get(context = context, "beauty_item_none"),
                                            icon = R.drawable.ic_beauty_none,
                                            onValueChanged = {
                                                beautyConfig.eyeShadowDisabled()
                                            },
                                            unique = "PE_TurnOff"
                                        ),
                                        SubItemInfo2(
                                            name = Localizations.get(
                                                context = context,
                                                "beauty_item_eye_shadow_red"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "bingtangshanzha",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "bingtangshanzha") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "bingtangshanzha", value)
                                            },
                                            unique = "PE_001",
                                            tintColor = "#B17463"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context,
                                                "beauty_item_eye_shadow_light_pink"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "dadizong",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "dadizong") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "dadizong", value)
                                            },
                                            unique = "PE_002",
                                            tintColor = "#C9897F"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context,
                                                "beauty_item_eye_shadow_light_coffee"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "hanxi",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "hanxi") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "hanxi", value)
                                            },
                                            unique = "PE_003",
                                            tintColor = "#D79485"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_pink"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "jiaotangzong",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "jiaotangzong") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "jiaotangzong", value)
                                            },
                                            unique = "PE_004",
                                            tintColor = "#CC8475"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_red2"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "meizihong",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "meizihong") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "meizihong", value)
                                            },
                                            unique = "PE_005",
                                            tintColor = "#A7655A"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_caramel"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "naichase",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "naichase") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "naichase", value)
                                            },
                                            unique = "PE_006",
                                            tintColor = "#B87767"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_orange"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "nvshen",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "nvshen") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "nvshen", value)
                                            },
                                            unique = "PE_007",
                                            tintColor = "#46201A"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_brown"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "qizhifen",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "qizhifen") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "qizhifen", value)
                                            },
                                            unique = "PE_008",
                                            tintColor = "#BF746A"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_sekoya"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "shaonvfen",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "shaonvfen") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "shaonvfen", value)
                                            },
                                            unique = "PE_009",
                                            tintColor = "#A9484A"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_brown2"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "wanxiahong",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "wanxiahong") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "wanxiahong", value)
                                            },
                                            unique = "PE_0010",
                                            tintColor = "#AF684A"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_coral"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "xingzi",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "xingzi") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "xingzi", value)
                                            },
                                            unique = "PE_0011",
                                            tintColor = "#D28A78"
                                        ), SubItemInfo2(
                                            name = Localizations.get(
                                                context = context, "beauty_item_eye_shadow_sun_red"
                                            ),
                                            icon = R.drawable.ic_beauty_item_eye_shadow_sub,
                                            isSelected = beautyConfig.makeUpEyeShadow?.style == "yuanqiju",
                                            value = if (beautyConfig.makeUpEyeShadow?.style == "yuanqiju") beautyConfig.makeUpEyeShadow?.identity ?: 0.5f else 0.5f,
                                            onValueChanged = { value ->
                                                beautyConfig.makeUpEyeShadow = ByteDanceBeautySDK.MakeUpItem(context, "yuanqiju", value)
                                            },
                                            unique = "PE_0012",
                                            tintColor = "#CA705D"
                                        )
                                    )
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "beauty_item_eye_size"
                                    ),
                                    icon = R.drawable.ic_beautfy_eye_sub_item_eye_size,
                                    onValueChanged = { value -> beautyConfig.enlargeEye = value },
                                    value = beautyConfig.enlargeEye,
                                    unique = "beauty_item_eye_size"
                                )
                            )
                        ), ItemInfo(
                            Localizations.get(context = context, "beauty_item_nose"),
                            R.drawable.ic_beauty_item_nose,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = {
                                        beautyConfig.disabledNarrow()
                                    },
                                    unique = "PE_TurnOff"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "beauty_item_nose_deform"
                                    ),
                                    icon = R.drawable.ic_beautfy_sub_item_nose_deform,
                                    onValueChanged = { value -> beautyConfig.narrowNose = value },
                                    value = beautyConfig.narrowNose,
                                    unique = "beauty_item_nose_deform"
                                )
                            )
                        ), ItemInfo(
                            Localizations.get(context = context, "beauty_item_mouth"),
                            R.drawable.ic_beauty_item_mouth,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = {
                                        beautyConfig.pagePosition0to3Remover()
                                    },
                                    unique = "PE_TurnOff"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "beauty_item_mouth_zoom"
                                    ),
                                    icon = R.drawable.ic_beauty_mouth_smile_pasif,
                                    value = beautyConfig.mouthSize,
                                    onValueChanged = { value -> beautyConfig.mouthSize = value },
                                    unique = "beauty_item_mouth_zoom",
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "beauty_item_mouth_whiten_teeth"
                                    ),
                                    icon = R.drawable.ic_beauty_teeth_whitening_pasif,
                                    value = beautyConfig.whiteTeeth,
                                    onValueChanged = { value -> beautyConfig.whiteTeeth = value },
                                    unique = "beauty_item_mouth_whiten_teeth"
                                )
                            )
                        )
                    )
                ),
                PageInfo(
                    Localizations.get(context = context, "beauty_group_image_quality"), listOf(
                        ItemInfo(
                            Localizations.get(context = context, "beauty_item_none"),
                            R.drawable.ic_beauty_none,
                            onValueChanged = { _ ->
                                beautyConfig.pagePosition0to4Remover()
                            }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_clear"),
                            R.drawable.ic_beauty_clear,
                            value = beautyConfig.clearImageQuality,
                            onValueChanged = { value -> beautyConfig.clearImageQuality = value }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_smooth"),
                            R.drawable.ic_beauty_smooth,
                            value = beautyConfig.smooth,
                            onValueChanged = { value -> beautyConfig.smooth = value }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_whiteen"),
                            R.drawable.ic_beauty_whiteen,
                            value = beautyConfig.whiten,
                            onValueChanged = { value ->
                                beautyConfig.whiten = value
                            }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_sharp"),
                            R.drawable.ic_beauty_sharp,
                            value = beautyConfig.redden,
                            onValueChanged = { value ->
                                beautyConfig.redden = value
                            }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_contrast"),
                            R.drawable.ic_image_quality_contrast,
                            value = beautyConfig.contrast,
                            onValueChanged = { value ->
                                beautyConfig.contrast = value
                            }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_tone"),
                            R.drawable.ic_image_quality_hue,
                            value = beautyConfig.hue,
                            onValueChanged = { value ->
                                beautyConfig.hue = value
                            }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_saturation"),
                            R.drawable.ic_image_quality_saturation,
                            value = beautyConfig.saturation,
                            onValueChanged = { value ->
                                beautyConfig.saturation = value
                            }),
                        ItemInfo(
                            Localizations.get(context = context, "image_quality_item_temperature"),
                            R.drawable.ic_image_quality_temperature,
                            value = beautyConfig.temperature,
                            onValueChanged = { value ->
                                beautyConfig.temperature = value
                            })
                    )
                ),
                PageInfo(
                    Localizations.get(context = context, "beauty_group_makeup"), listOf(
                        ItemInfo(
                            Localizations.get(context = context, "beauty_item_none"),
                            R.drawable.ic_beauty_none,
                            onValueChanged = { _ ->
                                beautyConfig.pagePosition0to5Remover()
                            },
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "make_up_item_lenses"),
                            R.drawable.ic_make_up_lenses,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = { beautyConfig.makeUpLenses = null },
                                    unique = "beauty_item_none",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(context = context, "make_up_item_lens_brown"),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "chujianhui",
                                    value = if (beautyConfig.makeUpLenses?.style == "chujianhui") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(context, "chujianhui", value)
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#B17463"
                                ), SubItemInfo(
                                    name = Localizations.get(context = context, "make_up_item_lens_cocoa_coffee"),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "huilv",
                                    value = if (beautyConfig.makeUpLenses?.style == "huilv") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(context, "huilv", value)
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#71695E"
                                ), SubItemInfo(
                                    name = Localizations.get(context = context, "make_up_item_lens_peach_pink"),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "huitong",
                                    value = if (beautyConfig.makeUpLenses?.style == "huitong") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(context, "huitong", value)
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#69614E"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_lens_water_light_black"
                                    ),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "hunxuezong",
                                    value = if (beautyConfig.makeUpLenses?.style == "hunxuezong") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(
                                            context,
                                            "hunxuezong",
                                            value
                                        )
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#807257"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_lens_star_blue"
                                    ),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "huoxing",

                                    value = if (beautyConfig.makeUpLenses?.style == "huoxing") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(
                                            context,
                                            "huoxing",
                                            value
                                        )
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#6B5743"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_lens_amber"
                                    ),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "kekezong",
                                    value = if (beautyConfig.makeUpLenses?.style == "kekezong") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(
                                            context,
                                            "kekezong",
                                            value
                                        )
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#866651"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_lens_rose_red"
                                    ),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "mitaofen",
                                    value = if (beautyConfig.makeUpLenses?.style == "mitaofen") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(
                                            context,
                                            "mitaofen",
                                            value
                                        )
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#584337"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_lens_gray"
                                    ),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "shuiguanghei",
                                    value = beautyConfig.makeUpLenses?.identity ?: 0.9f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(
                                            context,
                                            "shuiguanghei",
                                            value
                                        )
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#785656"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_lens_light_gray"
                                    ),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "xingkonglan",
                                    value = if (beautyConfig.makeUpLenses?.style == "xingkonglan") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(
                                            context,
                                            "xingkonglan",
                                            value
                                        )
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#59575E"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_lens_coffee_with_milk"
                                    ),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "xinxinzi",
                                    value = if (beautyConfig.makeUpLenses?.style == "xinxinzi") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(
                                            context,
                                            "xinxinzi",
                                            value
                                        )
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#59575E"
                                ), SubItemInfo(
                                    name = Localizations.get(context = context, "make_up_item_lens_dark_brown"),
                                    icon = R.drawable.ic_sub_item_eye_lens,
                                    isSelected = beautyConfig.makeUpLenses?.style == "yuansheng",
                                    value = if (beautyConfig.makeUpLenses?.style == "yuansheng") beautyConfig.makeUpLenses?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLenses = ByteDanceBeautySDK.MakeUpItem(context, "yuansheng", value)
                                    },
                                    unique = "make_up_item_lens",
                                    tintColor = "#5D534A"
                                )
                            )
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "make_up_item_LipColor"),
                            R.drawable.ic_make_up_lip_color,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = { beautyConfig.makeUpLip = null },
                                    unique = "beauty_item_none",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick1"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "doushafen",
                                    value = if (beautyConfig.makeUpLip?.style == "doushafen") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "doushafen", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#C95D64"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick2"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "fuguhong",
                                    value = if (beautyConfig.makeUpLip?.style == "fuguhong") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "fuguhong", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#952123"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick3"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "meizise",
                                    value = if (beautyConfig.makeUpLip?.style == "meizise") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "meizise", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#BF4156"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick4"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "shanhuse",
                                    value = if (beautyConfig.makeUpLip?.style == "shanhuse") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "shanhuse", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#DC5658"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick5"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "shaonvfen",
                                    value = if (beautyConfig.makeUpLip?.style == "shaonvfen") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "shaonvfen", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#DE4E59"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick6"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "sironghong",
                                    value = if (beautyConfig.makeUpLip?.style == "sironghong") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "sironghong", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#D92433"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick7"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "xiguahong",
                                    value = if (beautyConfig.makeUpLip?.style == "xiguahong") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "xiguahong", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#D33D4A"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick8"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "xiyouse",
                                    value = if (beautyConfig.makeUpLip?.style == "xiyouse") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "xiyouse", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#CF3F44"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick9"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "yuanqiju",
                                    value = if (beautyConfig.makeUpLip?.style == "yuanqiju") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "yuanqiju", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#DF4743"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_LipStick10"
                                    ),
                                    icon = R.drawable.ic_make_up_lip_color,
                                    isSelected = beautyConfig.makeUpLip?.style == "zangjuse",
                                    value = if (beautyConfig.makeUpLip?.style == "zangjuse") beautyConfig.makeUpLip?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpLip = ByteDanceBeautySDK.MakeUpItem(context, "zangjuse", value)
                                    },
                                    unique = "make_up_item_Lip",
                                    tintColor = "#B24036"
                                ),
                            )
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "make_up_item_HairColor"),
                            R.drawable.ic_make_up_hair_color,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = { _ ->
                                        beautyConfig.pagePosition0to6Remover()
                                    },
                                    unique = "beauty_item_none",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_HairColor_dark_blue"
                                    ),
                                    icon = R.drawable.ic_make_up_hair_color,
                                    isSelected = beautyConfig.makeUpHairColor?.style == "anlan",
                                    value = if (beautyConfig.makeUpHairColor?.style == "anlan") beautyConfig.makeUpHairColor?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpHairColor = ByteDanceBeautySDK.MakeUpItem(context, "anlan", value)
                                    },
                                    unique = "make_up_item_HairColor",
                                    tintColor = "#1A2026"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_HairColor_dark_green"
                                    ),
                                    icon = R.drawable.ic_make_up_hair_color,
                                    isSelected = beautyConfig.makeUpHairColor?.style == "molv",
                                    value = if (beautyConfig.makeUpHairColor?.style == "molv") beautyConfig.makeUpHairColor?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpHairColor = ByteDanceBeautySDK.MakeUpItem(context, "molv", value)
                                    },
                                    unique = "make_up_item_HairColor",
                                    tintColor = "#26231B"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_HairColor_dark_brown"
                                    ),
                                    icon = R.drawable.ic_make_up_hair_color,
                                    isSelected = beautyConfig.makeUpHairColor?.style == "shenzong",
                                    value = if (beautyConfig.makeUpHairColor?.style == "shenzong") beautyConfig.makeUpHairColor?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpHairColor = ByteDanceBeautySDK.MakeUpItem(context, "shenzong", value)
                                    },
                                    unique = "make_up_item_HairColor",
                                    tintColor = "#3D241E"
                                ),
                            )
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "make_up_item_blusher"),
                            R.drawable.ic_make_up_blush,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = { _ ->
                                        beautyConfig.pagePosition0to7Remover()
                                    },
                                    unique = "beauty_item_none",
                                    tintColor = "#B17463"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "make_up_item_blush1"
                                    ),
                                    icon = R.drawable.ic_make_up_blush,
                                    isSelected = beautyConfig.makeUpBlush?.style == "mitao",
                                    value = if (beautyConfig.makeUpBlush?.style == "mitao") beautyConfig.makeUpBlush?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpBlush = ByteDanceBeautySDK.MakeUpItem(context, "mitao", value)
                                    },
                                    unique = "make_up_item_blush",
                                    tintColor = "#DF8687"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "make_up_item_blush2"
                                    ),
                                    icon = R.drawable.ic_make_up_blush,
                                    isSelected = beautyConfig.makeUpBlush?.style == "qiaopi",
                                    value = if (beautyConfig.makeUpBlush?.style == "qiaopi") beautyConfig.makeUpBlush?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpBlush = ByteDanceBeautySDK.MakeUpItem(context, "qiaopi", value)
                                    },
                                    unique = "make_up_item_blush",
                                    tintColor = "#E48D92"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "make_up_item_blush3"
                                    ),
                                    icon = R.drawable.ic_make_up_blush,
                                    isSelected = beautyConfig.makeUpBlush?.style == "richang",
                                    value = if (beautyConfig.makeUpBlush?.style == "richang") beautyConfig.makeUpBlush?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpBlush = ByteDanceBeautySDK.MakeUpItem(context, "richang", value)
                                    },
                                    unique = "make_up_item_blush",
                                    tintColor = "#D17C6F"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "make_up_item_blush4"
                                    ),
                                    icon = R.drawable.ic_make_up_blush,
                                    isSelected = beautyConfig.makeUpBlush?.style == "shaishang",
                                    value = if (beautyConfig.makeUpBlush?.style == "shaishang") beautyConfig.makeUpBlush?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpBlush = ByteDanceBeautySDK.MakeUpItem(context, "shaishang", value)
                                    },
                                    unique = "make_up_item_blush",
                                    tintColor = "#F39B85"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "make_up_item_blush5"
                                    ),
                                    icon = R.drawable.ic_make_up_blush,
                                    isSelected = beautyConfig.makeUpBlush?.style == "tiancheng",
                                    value = if (beautyConfig.makeUpBlush?.style == "tiancheng") beautyConfig.makeUpBlush?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpBlush = ByteDanceBeautySDK.MakeUpItem(context, "tiancheng", value)
                                    },
                                    unique = "make_up_item_blush",
                                    tintColor = "#DD9079"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "make_up_item_blush6"
                                    ),
                                    icon = R.drawable.ic_make_up_blush,
                                    isSelected = beautyConfig.makeUpBlush?.style == "weixun",
                                    value = if (beautyConfig.makeUpBlush?.style == "weixun") beautyConfig.makeUpBlush?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpBlush = ByteDanceBeautySDK.MakeUpItem(context, "weixun", value)
                                    },
                                    unique = "make_up_item_blush",
                                    tintColor = "#FDA19D"
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context,
                                        "make_up_item_blush7"
                                    ),
                                    icon = R.drawable.ic_make_up_blush,
                                    isSelected = beautyConfig.makeUpBlush?.style == "xinji",
                                    value = if (beautyConfig.makeUpBlush?.style == "xinji") beautyConfig.makeUpBlush?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpBlush = ByteDanceBeautySDK.MakeUpItem(context, "xinji", value)
                                    },
                                    unique = "make_up_item_blush",
                                    tintColor = "#E2937E"
                                ),
                            )
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "make_up_item_contour"),
                            R.drawable.ic_make_up_contour,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = { _ ->
                                        beautyConfig.pagePosition0to8Remover()
                                    },
                                    unique = "beauty_item_none",
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_contour1"
                                    ),
                                    icon = R.drawable.ic_make_up_contour,
                                    isSelected = beautyConfig.makeUpContour?.style == "fajixian",
                                    value = if (beautyConfig.makeUpContour?.style == "fajixian") beautyConfig.makeUpContour?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpContour = ByteDanceBeautySDK.MakeUpItem(context, "fajixian", value)
                                    },
                                    unique = "make_up_item_contour",
                                    tintColor = "#E7AD99"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_contour2"
                                    ),
                                    icon = R.drawable.ic_make_up_contour,
                                    isSelected = beautyConfig.makeUpContour?.style == "gaoguang",
                                    value = if (beautyConfig.makeUpContour?.style == "gaoguang") beautyConfig.makeUpContour?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpContour = ByteDanceBeautySDK.MakeUpItem(context, "gaoguang", value)
                                    },
                                    unique = "make_up_item_contour",
                                    tintColor = "#F8B3B8"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_contour3"
                                    ),
                                    icon = R.drawable.ic_make_up_contour,
                                    isSelected = beautyConfig.makeUpContour?.style == "jingzhi",
                                    value = if (beautyConfig.makeUpContour?.style == "jingzhi") beautyConfig.makeUpContour?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpContour = ByteDanceBeautySDK.MakeUpItem(context, "jingzhi", value)
                                    },
                                    unique = "make_up_item_contour",
                                    tintColor = "#F7C293"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_contour4"
                                    ),
                                    icon = R.drawable.ic_make_up_contour,
                                    isSelected = beautyConfig.makeUpContour?.style == "xiaov",
                                    value = if (beautyConfig.makeUpContour?.style == "xiaov") beautyConfig.makeUpContour?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpContour = ByteDanceBeautySDK.MakeUpItem(context, "xiaov", value)
                                    },
                                    unique = "make_up_item_contour",
                                    tintColor = "#F0A894"
                                ), SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_contour5"
                                    ),
                                    icon = R.drawable.ic_make_up_contour,
                                    isSelected = beautyConfig.makeUpContour?.style == "ziran",
                                    value = if (beautyConfig.makeUpContour?.style == "ziran") beautyConfig.makeUpContour?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpContour = ByteDanceBeautySDK.MakeUpItem(context, "ziran", value)
                                    },
                                    unique = "make_up_item_contour",
                                    tintColor = "#F4B990"
                                )
                            )
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "make_up_item_eyebrow"),
                            R.drawable.ic_make_up_eyebrow,
                            hasNavigation = true,
                            subItemInfo = listOf(
                                SubItemInfo(
                                    name = Localizations.get(context = context, "beauty_item_none"),
                                    icon = R.drawable.ic_beauty_none,
                                    onValueChanged = {
                                        beautyConfig.pagePosition0to9Remover()
                                    },
                                    unique = "beauty_item_none",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow1"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "standard/biaozhun",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "standard/biaozhun") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "standard/biaozhun", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow2"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "lite/BK01",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "lite/BK01") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "lite/BK01", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow3"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "lite/BK02",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "lite/BK02") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "lite/BK02", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow4"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "lite/BK03",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "lite/BK03") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "lite/BK03", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow5"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "lite/BR01",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "lite/BR01") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "lite/BR01", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow6"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "standard/liuye",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "standard/liuye") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "standard/liuye", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow7"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "standard/rongrong",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "standard/rongrong") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "standard/rongrong", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                                SubItemInfo(
                                    name = Localizations.get(
                                        context = context, "make_up_item_eyebrow8"
                                    ),
                                    icon = R.drawable.ic_make_up_eyebrow,
                                    isSelected = beautyConfig.makeUpEyeBrow?.style == "standard/yesheng",
                                    value = if (beautyConfig.makeUpEyeBrow?.style == "standard/yesheng") beautyConfig.makeUpEyeBrow?.identity ?: 0.5f else 0.5f,
                                    onValueChanged = { value ->
                                        beautyConfig.makeUpEyeBrow = ByteDanceBeautySDK.MakeUpItem(context, "standard/yesheng", value)
                                    },
                                    unique = "make_up_item_eyebrow",
                                ),
                            )
                        ),
                        ItemInfo(
                            Localizations.get(context = context, "make_up_item_acne_removal"),
                            R.drawable.ic_make_up_acne_removal,
                            value = beautyConfig.acneRemoval,
                            onValueChanged = { value ->
                                beautyConfig.acneRemoval = value
                            }),
                    )
                ),
                PageInfo(
                    Localizations.get(context = context, "beauty_group_background_blur"),
                    listOf(
                        ItemInfo(
                            Localizations.get(context = context, "beauty_item_none"),
                            R.drawable.ic_beauty_none,
                            onValueChanged = { _ ->
                                beautyConfig.pagePosition0to10Remover()
                            }), ItemInfo(
                            Localizations.get(context = context, "background_blur_item_default"),
                            R.drawable.ic_blur_default,
                            isSelected = beautyConfig.sticker == "background_blur",
                            onValueChanged = { _ ->
                                beautyConfig.sticker = "background_blur"
                                beautyConfig.stickerParam = 0f
                            }), ItemInfo(
                            Localizations.get(context = context, "background_blur_item_enhanced"),
                            R.drawable.ic_blur_enhanced,
                            isSelected = beautyConfig.sticker == "bg_blur_strong",
                            onValueChanged = { _ ->
                                beautyConfig.sticker = "bg_blur_strong"
                                beautyConfig.stickerParam = 0f
                            }), ItemInfo(
                            Localizations.get(context = context, "background_blur_item_adjustable"),
                            R.drawable.ic_blur_adjustable,
                            value = beautyConfig.stickerParam,
                            onValueChanged = { value ->
                                beautyConfig.sticker = null
                                beautyConfig.stickerParam = value
                            })
                    )
                ),

                PageInfo(
                    Localizations.get(context = context, "beauty_group_default_filters"), listOf(
                        ItemInfo(
                            Localizations.get(context = context, "ready_effects_item_none"),
                            R.drawable.ic_beauty_none,
                            onValueChanged = { _ -> },
                            unique = "ready_effects_item_none"
                        ),

                        ItemInfo(
                            Localizations.get(context = context, "ready_effects"),
                            R.drawable.ic_beauty_item_face,
                            onValueChanged = {},
                            unique = "ready_effects"),
                    )
                ),
            )
        }
        return pageInfoCache
    }
}