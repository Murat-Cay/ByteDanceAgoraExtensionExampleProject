package com.volvoxmobile.bytedanceagoracrashexample.databinding;
import com.volvoxmobile.bytedanceagoracrashexample.R;
import com.volvoxmobile.bytedanceagoracrashexample.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentByteDanceBeautyBottomSheetBindingImpl extends FragmentByteDanceBeautyBottomSheetBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.layoutSlider, 1);
        sViewsWithIds.put(R.id.slider, 2);
        sViewsWithIds.put(R.id.ivCompare, 3);
        sViewsWithIds.put(R.id.viewContent, 4);
        sViewsWithIds.put(R.id.rvPageTitle, 5);
        sViewsWithIds.put(R.id.textTitle, 6);
        sViewsWithIds.put(R.id.imageBackButton, 7);
        sViewsWithIds.put(R.id.viewLine, 8);
        sViewsWithIds.put(R.id.layoutRv, 9);
        sViewsWithIds.put(R.id.rvBeautyEffect, 10);
        sViewsWithIds.put(R.id.imageArrow, 11);
        sViewsWithIds.put(R.id.imageRotate, 12);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentByteDanceBeautyBottomSheetBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private FragmentByteDanceBeautyBottomSheetBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageButton) bindings[11]
            , (androidx.appcompat.widget.AppCompatImageButton) bindings[7]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[5]
            , (com.google.android.material.slider.Slider) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            , (android.view.View) bindings[4]
            , (android.view.View) bindings[8]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
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
            return variableSet;
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}