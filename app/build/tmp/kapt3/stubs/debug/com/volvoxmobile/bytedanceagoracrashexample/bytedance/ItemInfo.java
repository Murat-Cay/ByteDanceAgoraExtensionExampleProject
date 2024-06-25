package com.volvoxmobile.bytedanceagoracrashexample.bytedance;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B~\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012#\b\u0002\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\n\u0012\b\b\u0002\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u000b0\t\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\r\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010)\u001a\u00020\u00052\u0006\u0010*\u001a\u00020+J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0007H\u00c6\u0003J$\u0010/\u001a\u001d\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\n\u0012\b\b\u0002\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u000b0\tH\u00c6\u0003J\t\u00100\u001a\u00020\rH\u00c6\u0003J\t\u00101\u001a\u00020\rH\u00c6\u0003J\t\u00102\u001a\u00020\rH\u00c6\u0003J\u000f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\u0084\u0001\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072#\b\u0002\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\n\u0012\b\b\u0002\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u000b0\t2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\r2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u0003H\u00c6\u0001J\u0006\u00106\u001a\u00020\u000bJ\u0013\u00107\u001a\u00020\r2\b\u00108\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00109\u001a\u00020\u0005H\u00d6\u0001J\b\u0010:\u001a\u00020\rH\u0002J\u000e\u0010;\u001a\u00020\u00052\u0006\u0010*\u001a\u00020+J\u000e\u0010<\u001a\u00020\u00052\u0006\u0010*\u001a\u00020+J\u0010\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010*\u001a\u00020+J\t\u0010?\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u000f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0016\"\u0004\b\u001b\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR,\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\n\u0012\b\b\u0002\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u000b0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010\u000e\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u0016\"\u0004\b(\u0010\u0018\u00a8\u0006@"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/ItemInfo;", "", "name", "", "icon", "", "value", "", "onValueChanged", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "", "isSelected", "", "withPadding", "hasNavigation", "subItemInfo", "", "Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/SubItemInfo;", "unique", "(Ljava/lang/String;IFLkotlin/jvm/functions/Function1;ZZZLjava/util/List;Ljava/lang/String;)V", "getHasNavigation", "()Z", "setHasNavigation", "(Z)V", "getIcon", "()I", "setSelected", "getName", "()Ljava/lang/String;", "getOnValueChanged", "()Lkotlin/jvm/functions/Function1;", "getSubItemInfo", "()Ljava/util/List;", "getUnique", "getValue", "()F", "setValue", "(F)V", "getWithPadding", "setWithPadding", "cardElevation", "context", "Landroid/content/Context;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "deselectAllSubItems", "equals", "other", "hashCode", "isSubItemSelectedVisibility", "selectedImg", "selectedTextColor", "setImage", "Landroid/graphics/drawable/Drawable;", "toString", "app_debug"})
public final class ItemInfo {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String name = null;
    private final int icon = 0;
    private float value;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<java.lang.Float, kotlin.Unit> onValueChanged = null;
    private boolean isSelected;
    private boolean withPadding;
    private boolean hasNavigation;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.volvoxmobile.bytedanceagoracrashexample.bytedance.SubItemInfo> subItemInfo = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String unique = null;
    
    public ItemInfo(@org.jetbrains.annotations.NotNull
    java.lang.String name, @androidx.annotation.DrawableRes
    int icon, float value, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onValueChanged, boolean isSelected, boolean withPadding, boolean hasNavigation, @org.jetbrains.annotations.NotNull
    java.util.List<com.volvoxmobile.bytedanceagoracrashexample.bytedance.SubItemInfo> subItemInfo, @org.jetbrains.annotations.NotNull
    java.lang.String unique) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getName() {
        return null;
    }
    
    public final int getIcon() {
        return 0;
    }
    
    public final float getValue() {
        return 0.0F;
    }
    
    public final void setValue(float p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlin.jvm.functions.Function1<java.lang.Float, kotlin.Unit> getOnValueChanged() {
        return null;
    }
    
    public final boolean isSelected() {
        return false;
    }
    
    public final void setSelected(boolean p0) {
    }
    
    public final boolean getWithPadding() {
        return false;
    }
    
    public final void setWithPadding(boolean p0) {
    }
    
    public final boolean getHasNavigation() {
        return false;
    }
    
    public final void setHasNavigation(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.volvoxmobile.bytedanceagoracrashexample.bytedance.SubItemInfo> getSubItemInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUnique() {
        return null;
    }
    
    public final void deselectAllSubItems() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.graphics.drawable.Drawable setImage(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    private final boolean isSubItemSelectedVisibility() {
        return false;
    }
    
    public final int selectedTextColor(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0;
    }
    
    public final int selectedImg(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0;
    }
    
    public final int cardElevation(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final float component3() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlin.jvm.functions.Function1<java.lang.Float, kotlin.Unit> component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.volvoxmobile.bytedanceagoracrashexample.bytedance.SubItemInfo> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.volvoxmobile.bytedanceagoracrashexample.bytedance.ItemInfo copy(@org.jetbrains.annotations.NotNull
    java.lang.String name, @androidx.annotation.DrawableRes
    int icon, float value, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onValueChanged, boolean isSelected, boolean withPadding, boolean hasNavigation, @org.jetbrains.annotations.NotNull
    java.util.List<com.volvoxmobile.bytedanceagoracrashexample.bytedance.SubItemInfo> subItemInfo, @org.jetbrains.annotations.NotNull
    java.lang.String unique) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}