package com.volvoxmobile.bytedanceagoracrashexample.bytedance

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.volvoxmobile.bytedanceagoracrashexample.BaseBottomSheetDialogFragment
import com.volvoxmobile.bytedanceagoracrashexample.Localizations
import com.volvoxmobile.bytedanceagoracrashexample.R
import com.volvoxmobile.bytedanceagoracrashexample.databinding.FragmentByteDanceBeautyBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ByteDanceBeautyBottomSheetFragment : BaseBottomSheetDialogFragment<FragmentByteDanceBeautyBottomSheetBinding>() {

    private lateinit var  viewModel: ByteDanceBeautyBottomSheetViewModel

    @Inject
    lateinit var beautyPageTitleAdapter: BeautyPageTitleAdapter

    @Inject
    lateinit var beautyEffectAdapter: BeautyEffectAdapter

    private var cameraStatusChangeClickListener: ((Boolean) -> Unit)? = null
    private var dismissClickListener: (() -> Unit)? = null
    private var beautyEnabledClickListener: (() -> Unit)? = null

    private var infoTextTitle = ""
    var pageIndex = 0
    var itemIndexM = 0
    var subClassItemPosition = 0

    fun setCameraStatusChangeClickListener(listener: (Boolean) -> Unit) {
        cameraStatusChangeClickListener = listener
    }

    fun setDismissClickListener(listener: () -> Unit) {
        dismissClickListener = listener
    }

    fun setBeautyEnabledClickListener(listener: () -> Unit) {
        beautyEnabledClickListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ByteDanceBeautyBottomSheetViewModel::class.java)
        showing = true
        initObserver()
        handleRecycleAdapters()
        setImageArrowClicked()
        setCameraStatusClicked()
        setIvCompareClicked()
        setImageButtonClicked()
        removeComposerListeners()
    }

    private fun initObserver() = with(viewModel) {
        isSelectedItemIndex.observe(viewLifecycleOwner) {
            if (it.first == 4 && it.second == 0) {
                updateReadyEffect(true)
            }
        }
        navigateRvAdapterIndex.observe(viewLifecycleOwner) {}

/*        readyEffect.observe(viewLifecycleOwner) {
            allDisabledEffects()
            //beautyEffectAdapter.notifyDataSetChanged()
        }*/
    }

    private fun handleRecycleAdapters() = with(binding) {
        rvPageTitle.apply {
            adapter = beautyPageTitleAdapter
            setHasFixedSize(true)
        }
        rvBeautyEffect.apply {
            adapter = beautyEffectAdapter
        }
        val getPageTitle = viewModel.getPageTitle(requireContext())

        viewModel.setSelectedItemInfo(pageIndex, 0, 0, 0)
        beautyPageTitleAdapter.setItems(getPageTitle)
        getInitialItemInfo()
        isSliderVisibility(true, "") // initial
        viewModel.updateRvAdapterIndex(0)

        beautyPageTitleAdapter.setOnItemClickListener { position, title ->
            beautyEffectAdapter.setItems(itemInfoList = viewModel.getItemInfo(position), itemInfoTitleValue = title)
            setBeautyEffectAdapterScrollToPosition()
            viewModel.updateRvAdapterIndex(0)
            pageIndex = position
        }
        beautyEffectAdapter.setItemInfoAndPosition { itemInfo, position ->
            viewModel.setSelectedItemInfo(pageIndex,position, 0,0)
            isSliderVisibility(itemInfo.hasNavigation, itemInfo.name)
            if (itemInfo.hasNavigation) {
                infoTextTitle = itemInfo.name
                textTitle.text = infoTextTitle
                itemIndexM = position
                beautyPageTitleVisibility(false)
                beautyEffectAdapter.setItems(subItemInfoList = viewModel.getSubItemInfo(pageIndex, position), subItemTitleValue = infoTextTitle)
                setBeautyEffectAdapterScrollToPosition()
                viewModel.updateRvAdapterIndex(1)
            } else {
                if (itemInfo.name == Localizations.get(root.context, "beauty_item_none")) {
                    showClearFilters {
                        beautyEffectAdapter.resetAllSelections(itemInfoList = viewModel.getItemInfo(pageIndex))
                    }
                }
                else if (itemInfo.unique == "ready_effects_item_none") {
                    if (viewModel.isSelectedItemAny()) {
                        showClearFilters {
                            viewModel.allDisabledEffects()
                        }
                    }
                }
                else if (itemInfo.name == Localizations.get(root.context, "ready_effects")) {
                    if (viewModel.isSelectedItemAny()) {
                        showClearFilters {
                            viewModel.allDisabledEffects()
                            viewModel.beautyConfig.effectsReady()
                        }
                    } else {
                        viewModel.beautyConfig.effectsReady()
                    }
                }
                else {
                    itemInfo.onValueChanged.invoke(itemInfo.value)
                    slider.clearOnChangeListeners()
                    slider.clearOnSliderTouchListeners()

                    slider.value = itemInfo.value

                    slider.addOnChangeListener { _, value, _ ->
                        itemInfo.value = value
                        itemInfo.onValueChanged.invoke(value)
                    }
                }
            }
        }

        beautyEffectAdapter.setSubItemInfoAndPosition { itemInfo, position ->
            isSliderVisibility(itemInfo.hasNavigation, itemInfo.name, itemInfo.unique)
            viewModel.setSelectedItemInfo(pageIndex,itemIndexM, position,0)
            subClassItemPosition = position
            if (itemInfo.subItemInfo2.isNotEmpty()) {
                textTitle.text = itemInfo.name
                beautyEffectAdapter.setItems(subItemInfo2List = viewModel.getSubItemInfo2(pageIndex, itemIndexM, position))
                setBeautyEffectAdapterScrollToPosition()
                viewModel.updateRvAdapterIndex(2)
            } else {
                if (itemInfo.name == Localizations.get(root.context, "beauty_item_none")) {
                    beautyEffectAdapter.resetAllSelections(subItemInfoList = viewModel.getSubItemInfo(pageIndex, itemIndexM))
                } else {
                    itemInfo.onValueChanged.invoke(itemInfo.value)
                    slider.clearOnChangeListeners()
                    slider.clearOnSliderTouchListeners()

                    slider.value = itemInfo.value

                    slider.addOnChangeListener { _, value, _ ->
                        itemInfo.value = value
                        itemInfo.onValueChanged.invoke(value)
                    }
                }
            }
        }

        beautyEffectAdapter.setSubItemInfo2AndPosition { itemInfo, position ->
            viewModel.setSelectedItemInfo(pageIndex,itemIndexM, subClassItemPosition ,position)
            isSliderVisibility(false, itemInfo.name, itemInfo.unique)

            if (itemInfo.name == Localizations.get(root.context, "beauty_item_none")) {
                beautyEffectAdapter.resetAllSelections(subItemInfo2List = viewModel.getSubItemInfo2(pageIndex, itemIndexM, subClassItemPosition))
            } else {
                itemInfo.onValueChanged.invoke(itemInfo.value)
                slider.clearOnChangeListeners()
                slider.clearOnSliderTouchListeners()

                slider.value = itemInfo.value

                slider.addOnChangeListener { _, value, _ ->
                    itemInfo.value = value
                    itemInfo.onValueChanged.invoke(value)
                }
            }
        }
    }


    private fun setImageButtonClicked() = with(binding) {
        imageBackButton.setOnClickListener {
            when(viewModel.navigateRvAdapterIndex.value) {
                2 -> {
                    beautyEffectAdapter.setItems(subItemInfoList = viewModel.getSubItemInfo(
                        viewModel.isSelectedItemIndex.value?.first ?: 0,
                        viewModel.isSelectedItemIndex.value?.second ?: 0
                    ))
                    //setBeautyEffectAdapterScrollToPosition()
                    textTitle.text = infoTextTitle
                    isSliderVisibility(true, "")
                    viewModel.updateRvAdapterIndex(1)
                }

                1 -> {
                    beautyEffectAdapter.setItems(itemInfoList = viewModel.getItemInfo(viewModel.isSelectedItemIndex.value?.first ?: 0))
                    //setBeautyEffectAdapterScrollToPosition()
                    beautyPageTitleVisibility(true)
                    isSliderVisibility(true, "")
                    viewModel.updateRvAdapterIndex(0)
                }

                0 -> {
                    getInitialItemInfo()
                    isSliderVisibility(true, "")
                }
            }
        }
    }

    private fun removeComposerListeners() {
        beautyEffectAdapter.removeComposerItemInfo { itemInfo ->
            if (itemInfo.unique == "ready_effects") {
                viewModel.removeComposerWithItemUnique(itemInfo.unique)
            } else {
                viewModel.removeComposerWithItemName(itemInfo.name, requireContext())
            }
            hideSlider()
        }
        beautyEffectAdapter.removeComposerSubItemInfo { subItem ->
            viewModel.removeComposerWithItemName(subItem.name, requireContext())
            hideSlider()
        }
        beautyEffectAdapter.removeComposerSubItemInfoUnique {
            viewModel.removeComposerWithItemUnique(it)
            hideSlider()
        }
        beautyEffectAdapter.removeComposerSubItemInfo2 { subItem2 ->
            viewModel.removeComposerWithItemName(subItem2.name, requireContext())
            hideSlider()
        }
    }

    private fun getInitialItemInfo() {
        beautyEffectAdapter.setItems(viewModel.getItemInfo(0))  // initial value
        setBeautyEffectAdapterScrollToPosition()
    }

    private fun setBeautyEffectAdapterScrollToPosition() {
        binding.rvBeautyEffect.scrollToPosition(0)
    }

    private fun beautyPageTitleVisibility(isVisible: Boolean) = with(binding) {
        val visibilityForTitleAndButton = if (isVisible) View.INVISIBLE else View.VISIBLE
        val visibilityForPageTitle = if (isVisible) View.VISIBLE else View.INVISIBLE
        textTitle.visibility = visibilityForTitleAndButton
        imageBackButton.visibility = visibilityForTitleAndButton
        rvPageTitle.visibility = visibilityForPageTitle
    }

    private fun showClearFilters(make: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogStyle).setTitle(Localizations.get(requireContext(), "clear_filters"))
            .setMessage(Localizations.get(requireContext(), "clear_filters_content"))
            .setCancelable(false)
            .setPositiveButton(Localizations.get(requireContext(), "okey")) { dialog, _ ->
                make.invoke()
            }
            .setNegativeButton(Localizations.get(requireContext(), "cancel")) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun setIvCompareClicked() = with(binding) {
        ivCompare.setOnClickListener { beautyEnabledClickListener?.invoke() }
    }
    private fun setCameraStatusClicked() = with(binding) {
        imageRotate.setOnClickListener { cameraStatusChangeClickListener?.invoke(true) }
    }

    private fun setImageArrowClicked() = with(binding) {
        imageArrow.setOnClickListener { dismissClickListener?.invoke() }
    }

    private fun isSliderVisibility(hasNavigation: Boolean, title: String, unique: String = "") {
        val isSliderVisible = viewModel.isSliderVisibility(hasNavigation, title, requireContext(), unique = unique)
        binding.slider.visibility = if (isSliderVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun hideSlider() = with(binding) {
        slider.visibility = View.INVISIBLE
    }

    override fun getLayoutResId(): Int = R.layout.fragment_byte_dance_beauty_bottom_sheet

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        showing = false
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTransparentTheme

    companion object {

        private var showing = false
        fun isShowing() = showing
        @JvmStatic
        fun newInstance() = ByteDanceBeautyBottomSheetFragment().apply { arguments = Bundle().apply {} }
    }
}