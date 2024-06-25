package com.volvoxmobile.bytedanceagoracrashexample;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\b\u0010#\u001a\u00020!H\u0002J\b\u0010$\u001a\u00020!H\u0002J\b\u0010%\u001a\u00020!H\u0002J\u0012\u0010&\u001a\u00020!2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0014J\b\u0010)\u001a\u00020!H\u0014J\b\u0010*\u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR#\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0010\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001d\u0010\u0010\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "beautyEnable", "", "binding", "Lcom/volvoxmobile/bytedanceagoracrashexample/databinding/ActivityMainBinding;", "cameraConfig", "Lio/agora/beautyapi/bytedance/CameraConfig;", "chanelName", "", "mByteDanceApi", "Lio/agora/beautyapi/bytedance/ByteDanceBeautyAPI;", "getMByteDanceApi", "()Lio/agora/beautyapi/bytedance/ByteDanceBeautyAPI;", "mByteDanceApi$delegate", "Lkotlin/Lazy;", "mRtcEngine", "Lio/agora/rtc2/RtcEngine;", "kotlin.jvm.PlatformType", "getMRtcEngine", "()Lio/agora/rtc2/RtcEngine;", "mRtcEngine$delegate", "mRtcHandler", "Lio/agora/rtc2/IRtcEngineEventHandler;", "mVideoEncoderConfiguration", "Lio/agora/rtc2/video/VideoEncoderConfiguration;", "getMVideoEncoderConfiguration", "()Lio/agora/rtc2/video/VideoEncoderConfiguration;", "mVideoEncoderConfiguration$delegate", "renderManager", "Lcom/effectsar/labcv/effectsdk/RenderManager;", "checkPermissions", "", "initBeautyApi", "initByteDanceBeautyBottomSheet", "initRtcEngine", "initializeCamera", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setupLocalVideo", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy mByteDanceApi$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final com.effectsar.labcv.effectsdk.RenderManager renderManager = null;
    @org.jetbrains.annotations.NotNull
    private io.agora.beautyapi.bytedance.CameraConfig cameraConfig;
    private boolean beautyEnable = true;
    private com.volvoxmobile.bytedanceagoracrashexample.databinding.ActivityMainBinding binding;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String chanelName = "1000";
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy mRtcEngine$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final io.agora.rtc2.IRtcEngineEventHandler mRtcHandler = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy mVideoEncoderConfiguration$delegate = null;
    private static final int PERMISSION_REQ_ID = 22;
    @org.jetbrains.annotations.NotNull
    public static final com.volvoxmobile.bytedanceagoracrashexample.MainActivity.Companion Companion = null;
    
    public MainActivity() {
        super();
    }
    
    private final io.agora.beautyapi.bytedance.ByteDanceBeautyAPI getMByteDanceApi() {
        return null;
    }
    
    private final io.agora.rtc2.RtcEngine getMRtcEngine() {
        return null;
    }
    
    private final io.agora.rtc2.video.VideoEncoderConfiguration getMVideoEncoderConfiguration() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initRtcEngine() {
    }
    
    private final void initByteDanceBeautyBottomSheet() {
    }
    
    private final void initBeautyApi() {
    }
    
    private final void initializeCamera() {
    }
    
    private final void setupLocalVideo() {
    }
    
    private final void checkPermissions() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/MainActivity$Companion;", "", "()V", "PERMISSION_REQ_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}