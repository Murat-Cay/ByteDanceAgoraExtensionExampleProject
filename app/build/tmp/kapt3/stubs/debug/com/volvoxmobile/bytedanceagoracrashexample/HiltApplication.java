package com.volvoxmobile.bytedanceagoracrashexample;

@dagger.hilt.android.HiltAndroidApp
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0006"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/HiltApplication;", "Landroid/app/Application;", "()V", "onCreate", "", "Companion", "app_debug"})
public final class HiltApplication extends android.app.Application {
    public static android.content.Context appContext;
    @org.jetbrains.annotations.NotNull
    private static java.util.List<java.lang.String> fragments;
    @org.jetbrains.annotations.NotNull
    public static final com.volvoxmobile.bytedanceagoracrashexample.HiltApplication.Companion Companion = null;
    
    public HiltApplication() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bJ\u0006\u0010\u000f\u001a\u00020\u000bJ\u0006\u0010\u0010\u001a\u00020\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/HiltApplication$Companion;", "", "()V", "appContext", "Landroid/content/Context;", "getAppContext", "()Landroid/content/Context;", "setAppContext", "(Landroid/content/Context;)V", "fragments", "", "", "addFragment", "", "name", "getCurrentFragment", "getPreviousFragment", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.content.Context getAppContext() {
            return null;
        }
        
        public final void setAppContext(@org.jetbrains.annotations.NotNull
        android.content.Context p0) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPreviousFragment() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getCurrentFragment() {
            return null;
        }
        
        public final void addFragment(@org.jetbrains.annotations.NotNull
        java.lang.String name) {
        }
    }
}