package com.volvoxmobile.bytedanceagoracrashexample.databinding;
import com.volvoxmobile.bytedanceagoracrashexample.R;
import com.volvoxmobile.bytedanceagoracrashexample.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemBeautyTabPageBindingImpl extends ItemBeautyTabPageBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemBeautyTabPageBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private ItemBeautyTabPageBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[1]
            );
        this.imgSelected.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.textPageTitle.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.pageTitle == variableId) {
            setPageTitle((java.lang.String) variable);
        }
        else if (BR.isSelectedBeauty == variableId) {
            setIsSelectedBeauty((java.lang.Boolean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPageTitle(@Nullable java.lang.String PageTitle) {
        this.mPageTitle = PageTitle;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.pageTitle);
        super.requestRebind();
    }
    public void setIsSelectedBeauty(@Nullable java.lang.Boolean IsSelectedBeauty) {
        this.mIsSelectedBeauty = IsSelectedBeauty;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.isSelectedBeauty);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String pageTitle = mPageTitle;
        boolean androidxDatabindingViewDataBindingSafeUnboxIsSelectedBeauty = false;
        int isSelectedBeautyTextPageTitleAndroidColorWheelTextColorTextPageTitleAndroidColorCoolGrey = 0;
        int isSelectedBeautyImgSelectedAndroidColorPurpleBackgroundColorImgSelectedAndroidColorTransparent = 0;
        java.lang.Boolean isSelectedBeauty = mIsSelectedBeauty;

        if ((dirtyFlags & 0x5L) != 0) {
        }
        if ((dirtyFlags & 0x6L) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty)
                androidxDatabindingViewDataBindingSafeUnboxIsSelectedBeauty = androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty);
            if((dirtyFlags & 0x6L) != 0) {
                if(androidxDatabindingViewDataBindingSafeUnboxIsSelectedBeauty) {
                        dirtyFlags |= 0x10L;
                        dirtyFlags |= 0x40L;
                }
                else {
                        dirtyFlags |= 0x8L;
                        dirtyFlags |= 0x20L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty) ? @android:color/wheel_text_color : @android:color/cool_grey
                isSelectedBeautyTextPageTitleAndroidColorWheelTextColorTextPageTitleAndroidColorCoolGrey = ((androidxDatabindingViewDataBindingSafeUnboxIsSelectedBeauty) ? (getColorFromResource(textPageTitle, R.color.wheel_text_color)) : (getColorFromResource(textPageTitle, R.color.cool_grey)));
                // read androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty) ? @android:color/purple_background_color : @android:color/transparent
                isSelectedBeautyImgSelectedAndroidColorPurpleBackgroundColorImgSelectedAndroidColorTransparent = ((androidxDatabindingViewDataBindingSafeUnboxIsSelectedBeauty) ? (getColorFromResource(imgSelected, R.color.purple_background_color)) : (getColorFromResource(imgSelected, android.R.color.transparent)));
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.imgSelected, androidx.databinding.adapters.Converters.convertColorToDrawable(isSelectedBeautyImgSelectedAndroidColorPurpleBackgroundColorImgSelectedAndroidColorTransparent));
            this.textPageTitle.setTextColor(isSelectedBeautyTextPageTitleAndroidColorWheelTextColorTextPageTitleAndroidColorCoolGrey);
        }
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textPageTitle, pageTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): pageTitle
        flag 1 (0x2L): isSelectedBeauty
        flag 2 (0x3L): null
        flag 3 (0x4L): androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty) ? @android:color/wheel_text_color : @android:color/cool_grey
        flag 4 (0x5L): androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty) ? @android:color/wheel_text_color : @android:color/cool_grey
        flag 5 (0x6L): androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty) ? @android:color/purple_background_color : @android:color/transparent
        flag 6 (0x7L): androidx.databinding.ViewDataBinding.safeUnbox(isSelectedBeauty) ? @android:color/purple_background_color : @android:color/transparent
    flag mapping end*/
    //end
}