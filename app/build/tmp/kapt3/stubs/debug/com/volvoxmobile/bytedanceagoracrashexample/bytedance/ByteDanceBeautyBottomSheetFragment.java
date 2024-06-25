package com.volvoxmobile.bytedanceagoracrashexample.bytedance;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0007\u0018\u0000 G2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001GB\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\'\u001a\u00020\f2\u0006\u0010(\u001a\u00020\u0015H\u0002J\b\u0010)\u001a\u00020\fH\u0002J\b\u0010*\u001a\u00020\u001aH\u0014J\b\u0010+\u001a\u00020\u001aH\u0016J\b\u0010,\u001a\u00020\fH\u0002J\b\u0010-\u001a\u00020\fH\u0002J\b\u0010.\u001a\u00020\fH\u0002J\"\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\u00152\u0006\u00101\u001a\u00020\u00182\b\b\u0002\u00102\u001a\u00020\u0018H\u0002J\u0010\u00103\u001a\u00020\f2\u0006\u00104\u001a\u000205H\u0016J\u001a\u00106\u001a\u00020\f2\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:H\u0016J\b\u0010;\u001a\u00020\fH\u0002J\b\u0010<\u001a\u00020\fH\u0002J\u0014\u0010=\u001a\u00020\f2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u001a\u0010?\u001a\u00020\f2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\f0\u0014J\b\u0010@\u001a\u00020\fH\u0002J\u0014\u0010A\u001a\u00020\f2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\b\u0010B\u001a\u00020\fH\u0002J\b\u0010C\u001a\u00020\fH\u0002J\b\u0010D\u001a\u00020\fH\u0002J\u0016\u0010E\u001a\u00020\f2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\f\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR\u001a\u0010\"\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001c\"\u0004\b$\u0010\u001eR\u000e\u0010%\u001a\u00020&X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006H"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/ByteDanceBeautyBottomSheetFragment;", "Lcom/volvoxmobile/bytedanceagoracrashexample/BaseBottomSheetDialogFragment;", "Lcom/volvoxmobile/bytedanceagoracrashexample/databinding/FragmentByteDanceBeautyBottomSheetBinding;", "()V", "beautyEffectAdapter", "Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/BeautyEffectAdapter;", "getBeautyEffectAdapter", "()Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/BeautyEffectAdapter;", "setBeautyEffectAdapter", "(Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/BeautyEffectAdapter;)V", "beautyEnabledClickListener", "Lkotlin/Function0;", "", "beautyPageTitleAdapter", "Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/BeautyPageTitleAdapter;", "getBeautyPageTitleAdapter", "()Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/BeautyPageTitleAdapter;", "setBeautyPageTitleAdapter", "(Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/BeautyPageTitleAdapter;)V", "cameraStatusChangeClickListener", "Lkotlin/Function1;", "", "dismissClickListener", "infoTextTitle", "", "itemIndexM", "", "getItemIndexM", "()I", "setItemIndexM", "(I)V", "pageIndex", "getPageIndex", "setPageIndex", "subClassItemPosition", "getSubClassItemPosition", "setSubClassItemPosition", "viewModel", "Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/ByteDanceBeautyBottomSheetViewModel;", "beautyPageTitleVisibility", "isVisible", "getInitialItemInfo", "getLayoutResId", "getTheme", "handleRecycleAdapters", "hideSlider", "initObserver", "isSliderVisibility", "hasNavigation", "title", "unique", "onDismiss", "dialog", "Landroid/content/DialogInterface;", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "removeComposerListeners", "setBeautyEffectAdapterScrollToPosition", "setBeautyEnabledClickListener", "listener", "setCameraStatusChangeClickListener", "setCameraStatusClicked", "setDismissClickListener", "setImageArrowClicked", "setImageButtonClicked", "setIvCompareClicked", "showClearFilters", "make", "Companion", "app_debug"})
public final class ByteDanceBeautyBottomSheetFragment extends com.volvoxmobile.bytedanceagoracrashexample.BaseBottomSheetDialogFragment<com.volvoxmobile.bytedanceagoracrashexample.databinding.FragmentByteDanceBeautyBottomSheetBinding> {
    private com.volvoxmobile.bytedanceagoracrashexample.bytedance.ByteDanceBeautyBottomSheetViewModel viewModel;
    @javax.inject.Inject
    public com.volvoxmobile.bytedanceagoracrashexample.bytedance.BeautyPageTitleAdapter beautyPageTitleAdapter;
    @javax.inject.Inject
    public com.volvoxmobile.bytedanceagoracrashexample.bytedance.BeautyEffectAdapter beautyEffectAdapter;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> cameraStatusChangeClickListener;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> dismissClickListener;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> beautyEnabledClickListener;
    @org.jetbrains.annotations.NotNull
    private java.lang.String infoTextTitle = "";
    private int pageIndex = 0;
    private int itemIndexM = 0;
    private int subClassItemPosition = 0;
    private static boolean showing = false;
    @org.jetbrains.annotations.NotNull
    public static final com.volvoxmobile.bytedanceagoracrashexample.bytedance.ByteDanceBeautyBottomSheetFragment.Companion Companion = null;
    
    public ByteDanceBeautyBottomSheetFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.volvoxmobile.bytedanceagoracrashexample.bytedance.BeautyPageTitleAdapter getBeautyPageTitleAdapter() {
        return null;
    }
    
    public final void setBeautyPageTitleAdapter(@org.jetbrains.annotations.NotNull
    com.volvoxmobile.bytedanceagoracrashexample.bytedance.BeautyPageTitleAdapter p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.volvoxmobile.bytedanceagoracrashexample.bytedance.BeautyEffectAdapter getBeautyEffectAdapter() {
        return null;
    }
    
    public final void setBeautyEffectAdapter(@org.jetbrains.annotations.NotNull
    com.volvoxmobile.bytedanceagoracrashexample.bytedance.BeautyEffectAdapter p0) {
    }
    
    public final int getPageIndex() {
        return 0;
    }
    
    public final void setPageIndex(int p0) {
    }
    
    public final int getItemIndexM() {
        return 0;
    }
    
    public final void setItemIndexM(int p0) {
    }
    
    public final int getSubClassItemPosition() {
        return 0;
    }
    
    public final void setSubClassItemPosition(int p0) {
    }
    
    public final void setCameraStatusChangeClickListener(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> listener) {
    }
    
    public final void setDismissClickListener(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> listener) {
    }
    
    public final void setBeautyEnabledClickListener(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> listener) {
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initObserver() {
    }
    
    private final void handleRecycleAdapters() {
    }
    
    private final void setImageButtonClicked() {
    }
    
    private final void removeComposerListeners() {
    }
    
    private final void getInitialItemInfo() {
    }
    
    private final void setBeautyEffectAdapterScrollToPosition() {
    }
    
    private final void beautyPageTitleVisibility(boolean isVisible) {
    }
    
    private final void showClearFilters(kotlin.jvm.functions.Function0<kotlin.Unit> make) {
    }
    
    private final void setIvCompareClicked() {
    }
    
    private final void setCameraStatusClicked() {
    }
    
    private final void setImageArrowClicked() {
    }
    
    private final void isSliderVisibility(boolean hasNavigation, java.lang.String title, java.lang.String unique) {
    }
    
    private final void hideSlider() {
    }
    
    @java.lang.Override
    protected int getLayoutResId() {
        return 0;
    }
    
    @java.lang.Override
    public void onDismiss(@org.jetbrains.annotations.NotNull
    android.content.DialogInterface dialog) {
    }
    
    @java.lang.Override
    public int getTheme() {
        return 0;
    }
    
    @kotlin.jvm.JvmStatic
    @org.jetbrains.annotations.NotNull
    public static final com.volvoxmobile.bytedanceagoracrashexample.bytedance.ByteDanceBeautyBottomSheetFragment newInstance() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004J\b\u0010\u0006\u001a\u00020\u0007H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/ByteDanceBeautyBottomSheetFragment$Companion;", "", "()V", "showing", "", "isShowing", "newInstance", "Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/ByteDanceBeautyBottomSheetFragment;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isShowing() {
            return false;
        }
        
        @kotlin.jvm.JvmStatic
        @org.jetbrains.annotations.NotNull
        public final com.volvoxmobile.bytedanceagoracrashexample.bytedance.ByteDanceBeautyBottomSheetFragment newInstance() {
            return null;
        }
    }
}