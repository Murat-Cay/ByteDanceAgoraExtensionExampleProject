package com.volvoxmobile.bytedanceagoracrashexample.bytedance.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0005H\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/volvoxmobile/bytedanceagoracrashexample/bytedance/utils/ZipUtils;", "", "()V", "INVALID_ZIP_ENTRY_NAME", "", "", "[Ljava/lang/String;", "TAG", "unzip", "", "zipFile", "targetDir", "unzipWithException", "", "validEntry", "name", "app_debug"})
public final class ZipUtils {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "ZipUtils";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String[] INVALID_ZIP_ENTRY_NAME = {"../", "~/"};
    @org.jetbrains.annotations.NotNull
    public static final com.volvoxmobile.bytedanceagoracrashexample.bytedance.utils.ZipUtils INSTANCE = null;
    
    private ZipUtils() {
        super();
    }
    
    public final boolean unzip(@org.jetbrains.annotations.NotNull
    java.lang.String zipFile, @org.jetbrains.annotations.NotNull
    java.lang.String targetDir) {
        return false;
    }
    
    @kotlin.jvm.Throws(exceptionClasses = {java.lang.Exception.class})
    private final void unzipWithException(java.lang.String zipFile, java.lang.String targetDir) throws java.lang.Exception {
    }
    
    private final boolean validEntry(java.lang.String name) {
        return false;
    }
}