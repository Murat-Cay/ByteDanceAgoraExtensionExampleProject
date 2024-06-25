package com.volvoxmobile.bytedanceagoracrashexample.databinding;
import com.volvoxmobile.bytedanceagoracrashexample.R;
import com.volvoxmobile.bytedanceagoracrashexample.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemBeautyEffectBindingImpl extends ItemBeautyEffectBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivIcon, 3);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemBeautyEffectBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private ItemBeautyEffectBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (android.widget.ImageView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[1]
            );
        this.imgSelected.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvName.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
        if (BR.icon == variableId) {
            setIcon((java.lang.Integer) variable);
        }
        else if (BR.itemText == variableId) {
            setItemText((java.lang.String) variable);
        }
        else if (BR.textColor == variableId) {
            setTextColor((java.lang.Integer) variable);
        }
        else if (BR.imageSelectedValue == variableId) {
            setImageSelectedValue((java.lang.Integer) variable);
        }
        else if (BR.cardElevationValue == variableId) {
            setCardElevationValue((java.lang.Integer) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setIcon(@Nullable java.lang.Integer Icon) {
        this.mIcon = Icon;
    }
    public void setItemText(@Nullable java.lang.String ItemText) {
        this.mItemText = ItemText;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.itemText);
        super.requestRebind();
    }
    public void setTextColor(@Nullable java.lang.Integer TextColor) {
        this.mTextColor = TextColor;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.textColor);
        super.requestRebind();
    }
    public void setImageSelectedValue(@Nullable java.lang.Integer ImageSelectedValue) {
        this.mImageSelectedValue = ImageSelectedValue;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.imageSelectedValue);
        super.requestRebind();
    }
    public void setCardElevationValue(@Nullable java.lang.Integer CardElevationValue) {
        this.mCardElevationValue = CardElevationValue;
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
        int androidxDatabindingViewDataBindingSafeUnboxTextColor = 0;
        int androidxDatabindingViewDataBindingSafeUnboxImageSelectedValue = 0;
        java.lang.String itemText = mItemText;
        java.lang.Integer textColor = mTextColor;
        java.lang.Integer imageSelectedValue = mImageSelectedValue;

        if ((dirtyFlags & 0x22L) != 0) {
        }
        if ((dirtyFlags & 0x24L) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(textColor)
                androidxDatabindingViewDataBindingSafeUnboxTextColor = androidx.databinding.ViewDataBinding.safeUnbox(textColor);
        }
        if ((dirtyFlags & 0x28L) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(imageSelectedValue)
                androidxDatabindingViewDataBindingSafeUnboxImageSelectedValue = androidx.databinding.ViewDataBinding.safeUnbox(imageSelectedValue);
        }
        // batch finished
        if ((dirtyFlags & 0x28L) != 0) {
            // api target 1

            this.imgSelected.setVisibility(androidxDatabindingViewDataBindingSafeUnboxImageSelectedValue);
        }
        if ((dirtyFlags & 0x22L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, itemText);
        }
        if ((dirtyFlags & 0x24L) != 0) {
            // api target 1

            this.tvName.setTextColor(androidxDatabindingViewDataBindingSafeUnboxTextColor);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): icon
        flag 1 (0x2L): itemText
        flag 2 (0x3L): textColor
        flag 3 (0x4L): imageSelectedValue
        flag 4 (0x5L): cardElevationValue
        flag 5 (0x6L): null
    flag mapping end*/
    //end
}