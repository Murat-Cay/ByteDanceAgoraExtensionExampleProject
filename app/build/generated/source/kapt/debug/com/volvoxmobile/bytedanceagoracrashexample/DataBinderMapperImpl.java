package com.volvoxmobile.bytedanceagoracrashexample;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.volvoxmobile.bytedanceagoracrashexample.databinding.ActivityMainBindingImpl;
import com.volvoxmobile.bytedanceagoracrashexample.databinding.FragmentByteDanceBeautyBottomSheetBindingImpl;
import com.volvoxmobile.bytedanceagoracrashexample.databinding.ItemBeautyEffectBindingImpl;
import com.volvoxmobile.bytedanceagoracrashexample.databinding.ItemBeautyTabPageBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMAIN = 1;

  private static final int LAYOUT_FRAGMENTBYTEDANCEBEAUTYBOTTOMSHEET = 2;

  private static final int LAYOUT_ITEMBEAUTYEFFECT = 3;

  private static final int LAYOUT_ITEMBEAUTYTABPAGE = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.volvoxmobile.bytedanceagoracrashexample.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.volvoxmobile.bytedanceagoracrashexample.R.layout.fragment_byte_dance_beauty_bottom_sheet, LAYOUT_FRAGMENTBYTEDANCEBEAUTYBOTTOMSHEET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.volvoxmobile.bytedanceagoracrashexample.R.layout.item_beauty_effect, LAYOUT_ITEMBEAUTYEFFECT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.volvoxmobile.bytedanceagoracrashexample.R.layout.item_beauty_tab_page, LAYOUT_ITEMBEAUTYTABPAGE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTBYTEDANCEBEAUTYBOTTOMSHEET: {
          if ("layout/fragment_byte_dance_beauty_bottom_sheet_0".equals(tag)) {
            return new FragmentByteDanceBeautyBottomSheetBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_byte_dance_beauty_bottom_sheet is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMBEAUTYEFFECT: {
          if ("layout/item_beauty_effect_0".equals(tag)) {
            return new ItemBeautyEffectBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_beauty_effect is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMBEAUTYTABPAGE: {
          if ("layout/item_beauty_tab_page_0".equals(tag)) {
            return new ItemBeautyTabPageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_beauty_tab_page is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(8);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "cardElevationValue");
      sKeys.put(2, "icon");
      sKeys.put(3, "imageSelectedValue");
      sKeys.put(4, "isSelectedBeauty");
      sKeys.put(5, "itemText");
      sKeys.put(6, "pageTitle");
      sKeys.put(7, "textColor");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_main_0", com.volvoxmobile.bytedanceagoracrashexample.R.layout.activity_main);
      sKeys.put("layout/fragment_byte_dance_beauty_bottom_sheet_0", com.volvoxmobile.bytedanceagoracrashexample.R.layout.fragment_byte_dance_beauty_bottom_sheet);
      sKeys.put("layout/item_beauty_effect_0", com.volvoxmobile.bytedanceagoracrashexample.R.layout.item_beauty_effect);
      sKeys.put("layout/item_beauty_tab_page_0", com.volvoxmobile.bytedanceagoracrashexample.R.layout.item_beauty_tab_page);
    }
  }
}
