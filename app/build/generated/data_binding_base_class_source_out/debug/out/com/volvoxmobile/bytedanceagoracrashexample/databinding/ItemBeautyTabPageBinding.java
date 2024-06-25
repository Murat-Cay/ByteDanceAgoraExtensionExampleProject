// Generated by data binding compiler. Do not edit!
package com.volvoxmobile.bytedanceagoracrashexample.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.volvoxmobile.bytedanceagoracrashexample.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemBeautyTabPageBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatImageView imgSelected;

  @NonNull
  public final AppCompatTextView textPageTitle;

  @Bindable
  protected String mPageTitle;

  @Bindable
  protected Boolean mIsSelectedBeauty;

  protected ItemBeautyTabPageBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatImageView imgSelected, AppCompatTextView textPageTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgSelected = imgSelected;
    this.textPageTitle = textPageTitle;
  }

  public abstract void setPageTitle(@Nullable String pageTitle);

  @Nullable
  public String getPageTitle() {
    return mPageTitle;
  }

  public abstract void setIsSelectedBeauty(@Nullable Boolean isSelectedBeauty);

  @Nullable
  public Boolean getIsSelectedBeauty() {
    return mIsSelectedBeauty;
  }

  @NonNull
  public static ItemBeautyTabPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_beauty_tab_page, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemBeautyTabPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemBeautyTabPageBinding>inflateInternal(inflater, R.layout.item_beauty_tab_page, root, attachToRoot, component);
  }

  @NonNull
  public static ItemBeautyTabPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_beauty_tab_page, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemBeautyTabPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemBeautyTabPageBinding>inflateInternal(inflater, R.layout.item_beauty_tab_page, null, false, component);
  }

  public static ItemBeautyTabPageBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ItemBeautyTabPageBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemBeautyTabPageBinding)bind(component, view, R.layout.item_beauty_tab_page);
  }
}
