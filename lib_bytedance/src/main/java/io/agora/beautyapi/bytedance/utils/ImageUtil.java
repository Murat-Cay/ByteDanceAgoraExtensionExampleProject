/*
 * MIT License
 *
 * Copyright (c) 2023 Agora Community
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.agora.beautyapi.bytedance.utils;

import static android.opengl.GLES20.GL_RGBA;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import android.widget.ImageView;

import com.effectsar.labcv.effectsdk.EffectsSDKEffectConstants;

import java.nio.ByteBuffer;
import java.util.Objects;

import io.agora.beautyapi.bytedance.utils.opengl.GlUtil;
import io.agora.beautyapi.bytedance.utils.opengl.ProgramManager;
import io.agora.beautyapi.bytedance.utils.opengl.ProgramTextureYUV;

/**
 * Created on 5/8/21 11:58 AM
 */
public class ImageUtil {
    private static final String TAG = "ImageUtil";

    protected int[] mFrameBuffers;
    protected int[] mFrameBufferTextures;
    protected int FRAME_BUFFER_NUM = 1;
    protected Point mFrameBufferShape;

    private ProgramManager mProgramManager;

/*    public ImageUtil() {
    }*/


    public int prepareTexture(int width, int height) {
        try {
            initFrameBufferIfNeed(width, height);
        } catch (Exception e) {
            return -1;
        }
        return mFrameBufferTextures[0];
    }

    public int getOutputTexture() {
        if (mFrameBufferTextures == null) return GlUtil.NO_TEXTURE;
        return mFrameBufferTextures[0];
    }

    private void initFrameBufferIfNeed(int width, int height) {
        boolean need = false;
        if (mFrameBufferShape == null || mFrameBufferShape.x != width || mFrameBufferShape.y != height) {
            need = true;
        }
        if (mFrameBuffers == null || mFrameBufferTextures == null) {
            need = true;
        }
        if (need) {
            destroyFrameBuffers();
            try {
                mFrameBuffers = new int[FRAME_BUFFER_NUM];
                mFrameBufferTextures = new int[FRAME_BUFFER_NUM];
                GLES20.glGenFramebuffers(FRAME_BUFFER_NUM, mFrameBuffers, 0);
                GLES20.glGenTextures(FRAME_BUFFER_NUM, mFrameBufferTextures, 0);
                for (int i = 0; i < FRAME_BUFFER_NUM; i++) {
                    bindFrameBuffer(mFrameBufferTextures[i], mFrameBuffers[i], width, height);
                }
                mFrameBufferShape = new Point(width, height);
            } catch (Exception e) {
                destroyFrameBuffers();
            }
        }
    }

    private void destroyFrameBuffers() {
        if (mFrameBufferTextures != null) {
            GLES20.glDeleteTextures(FRAME_BUFFER_NUM, mFrameBufferTextures, 0);
            mFrameBufferTextures = null;
        }
        if (mFrameBuffers != null) {
            GLES20.glDeleteFramebuffers(FRAME_BUFFER_NUM, mFrameBuffers, 0);
            mFrameBuffers = null;
        }
    }

    private void bindFrameBuffer(int textureId, int frameBuffer, int width, int height) {
        try {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                    GLES20.GL_TEXTURE_2D, textureId, 0);

            int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
            if (status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
                throw new RuntimeException("Framebuffer not complete: " + status);
            }

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        } catch (Exception e) {
            Log.e(TAG, "Error binding frame buffer", e);
        }
    }

    public void release() {
        destroyFrameBuffers();
        if (mProgramManager != null) {
            mProgramManager.release();
            mProgramManager = null;
        }
    }


    private void checkGLError(String operation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(operation + ": glError " + error);
        }
    }

    public ByteBuffer captureRenderResult(int imageWidth, int imageHeight) {
        if (mFrameBufferTextures == null) return null;
        int textureId = mFrameBufferTextures[0];
        if (null == mFrameBufferTextures || textureId == GlUtil.NO_TEXTURE) {
            return null;
        }
        if (imageWidth * imageHeight == 0) {
            return null;
        }
        ByteBuffer mCaptureBuffer = ByteBuffer.allocateDirect(imageWidth * imageHeight * 4);

        mCaptureBuffer.position(0);
        int[] frameBuffer = new int[1];
        GLES20.glGenFramebuffers(1, frameBuffer, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, textureId, 0);
        GLES20.glReadPixels(0, 0, imageWidth, imageHeight,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, mCaptureBuffer);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        if (null != frameBuffer) {
            GLES20.glDeleteFramebuffers(1, frameBuffer, 0);
        }
        return mCaptureBuffer;
    }

    public ByteBuffer captureRenderResult(int textureId, int imageWidth, int imageHeight) {
        if (textureId == GlUtil.NO_TEXTURE) {
            return null;
        }
        if (imageWidth * imageHeight == 0) {
            return null;
        }
        ByteBuffer mCaptureBuffer = ByteBuffer.allocateDirect(imageWidth * imageHeight * 4);

        mCaptureBuffer.position(0);
        int[] frameBuffer = new int[1];
        GLES20.glGenFramebuffers(1, frameBuffer, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, textureId, 0);
        GLES20.glReadPixels(0, 0, imageWidth, imageHeight,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, mCaptureBuffer);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        if (null != frameBuffer) {
            GLES20.glDeleteFramebuffers(1, frameBuffer, 0);
        }
        return mCaptureBuffer;
    }

    public boolean copyTexture(int srcTexture, int dstTexture, int width, int height) {
        if (srcTexture == GlUtil.NO_TEXTURE || dstTexture == GlUtil.NO_TEXTURE) {
            return false;
        }
        if (width * height == 0) {
            return false;
        }
        int[] frameBuffer = new int[1];
        GLES20.glGenFramebuffers(1, frameBuffer, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, srcTexture, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, dstTexture);
        GLES20.glCopyTexImage2D(GLES20.GL_TEXTURE_2D, 0, GL_RGBA, 0, 0, width, height, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        if (null != frameBuffer) {
            GLES20.glDeleteFramebuffers(1, frameBuffer, 0);
        }
        int error = GLES20.glGetError();
        if (error != GLES20.GL_NO_ERROR) {
            String msg = "copyTexture glError 0x" + Integer.toHexString(error);
            return false;
        }
        return true;
    }

    public  int transferTextureToTexture(int inputTexture, EffectsSDKEffectConstants.TextureFormat inputTextureFormat,
                                         EffectsSDKEffectConstants.TextureFormat outputTextureFormat,
                                 int width, int height, Transition transition) {
            if (outputTextureFormat != EffectsSDKEffectConstants.TextureFormat.Texure2D){
                LogUtils.e(TAG, "the inputTexture is not supported,please use Texure2D as output texture format");
                return  GlUtil.NO_TEXTURE;
            }
        if (null == mProgramManager) {
            mProgramManager = new ProgramManager();
        }

        boolean targetRoated = (transition.getAngle()%180 ==90);
        return mProgramManager.getProgram(inputTextureFormat).drawFrameOffScreen(inputTexture, targetRoated?height:width, targetRoated?width:height, transition.getMatrix());

    }

    private ProgramTextureYUV mYUVProgram;
    public int transferYUVToTexture(ByteBuffer yBuffer, ByteBuffer vuBuffer, int width, int height, Transition transition) {
        try {
            if (mYUVProgram == null) {
                mYUVProgram = new ProgramTextureYUV();
            }

            int yTexture = GlUtil.createImageTexture(yBuffer, width, height, GLES20.GL_ALPHA);
            checkGLError("createImageTexture yTexture");
            int vuTexture = GlUtil.createImageTexture(vuBuffer, width / 2, height / 2, GLES20.GL_LUMINANCE_ALPHA);
            checkGLError("createImageTexture vuTexture");

            int rgbaTexture = mYUVProgram.drawFrameOffScreen(yTexture, vuTexture, width, height, transition.getMatrix());
            checkGLError("drawFrameOffScreen");

            GlUtil.deleteTextureId(new int[]{yTexture, vuTexture});
            checkGLError("deleteTextureId");

            return rgbaTexture;
        } catch (Exception e) {
            Log.e(TAG, "Error in transferYUVToTexture", e);
            return -1;
        }
    }

    public ByteBuffer transferTextureToBuffer(int texture, EffectsSDKEffectConstants.TextureFormat inputTextureFormat,
                                              EffectsSDKEffectConstants.PixlFormat outputFormat, int width, int height, float ratio) {
        if (outputFormat != EffectsSDKEffectConstants.PixlFormat.RGBA8888) {
            Log.e(TAG, "Unsupported output format");
            return null;
        }

        if (inputTextureFormat == null) {
            Log.e(TAG, "Input texture format is null");
            return null;
        }

        try {
            if (mProgramManager == null) {
                mProgramManager = new ProgramManager();
            }
            int resizedWidth = (int) (width * ratio);
            int resizedHeight = (int) (height * ratio);

            return mProgramManager.getProgram(inputTextureFormat).readBuffer(texture, resizedWidth, resizedHeight);
        } catch (Exception e) {
            Log.e(TAG, "Error in transferTextureToBuffer", e);
            return null;
        }
    }

    public Bitmap transferTextureToBitmap(int texture, EffectsSDKEffectConstants.TextureFormat inputTextureFormat,
                                          int width, int height) {
        ByteBuffer buffer = transferTextureToBuffer(texture, inputTextureFormat, EffectsSDKEffectConstants.PixlFormat.RGBA8888,
                width, height, 1);
        if (buffer == null) {
            return null;
        }
        return transferBufferToBitmap(buffer, EffectsSDKEffectConstants.PixlFormat.RGBA8888, width, height);
    }

    public int transferBufferToTexture(ByteBuffer buffer, EffectsSDKEffectConstants.PixlFormat inputFormat,
                                EffectsSDKEffectConstants.TextureFormat outputFormat, int width, int height){

        if (inputFormat != EffectsSDKEffectConstants.PixlFormat.RGBA8888){
            LogUtils.e(TAG, "inputFormat support RGBA8888 only");
            return GlUtil.NO_TEXTURE;
        }

        if (outputFormat != EffectsSDKEffectConstants.TextureFormat.Texure2D){
            LogUtils.e(TAG, "outputFormat support Texure2D only");
            return GlUtil.NO_TEXTURE;
        }

        return create2DTexture(buffer, width,height, GL_RGBA);
    }

    private void checkGlError(String operation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, operation + ": glError " + error);
            throw new RuntimeException(operation + ": glError " + error);
        }
    }

    private int create2DTexture(ByteBuffer data, int width, int height, int format) {
        int[] textureHandles = new int[1];
        GLES20.glGenTextures(1, textureHandles, 0);
        checkGlError("glGenTextures");

        int textureHandle = textureHandles[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        checkGlError("setTexParameters");

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, format, width, height, 0, format, GLES20.GL_UNSIGNED_BYTE, data);
        checkGlError("glTexImage2D");

        return textureHandle;
    }

    public ByteBuffer transferBufferToBuffer(ByteBuffer buffer, EffectsSDKEffectConstants.PixlFormat inputFormat,
                                      EffectsSDKEffectConstants.PixlFormat outputFormat, int width, int height){
        return null;
    }

    public Bitmap transferBufferToBitmap(ByteBuffer buffer, EffectsSDKEffectConstants.PixlFormat format, int width, int height) {
        Bitmap.Config bitmapConfig;
        if (Objects.requireNonNull(format) == EffectsSDKEffectConstants.PixlFormat.RGBA8888) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        } else {
            Log.e(TAG, "Unsupported pixel format");
            return null;
        }

        Bitmap cameraBitmap = Bitmap.createBitmap(width, height, bitmapConfig);
        buffer.position(0);
        cameraBitmap.copyPixelsFromBuffer(buffer);
        buffer.position(0);

        return cameraBitmap;
    }

    public void drawFrameOnScreen(int textureId,EffectsSDKEffectConstants.TextureFormat srcTetxureFormat,int surfaceWidth, int surfaceHeight, float[]mMVPMatrix) {
        if (null == mProgramManager) {
            mProgramManager = new ProgramManager();
        }
        mProgramManager.getProgram(srcTetxureFormat).drawFrameOnScreen(textureId, surfaceWidth, surfaceHeight, mMVPMatrix);
    }

    public static class Transition {
        private static final String TAG = "Transition";
        private final float[] mMVPMatrix = new float[16];
        private int mAngle = 0;

        public Transition() {
            Matrix.setIdentityM(mMVPMatrix, 0);
        }

        public Transition(float[] transformMatrixArray) {
            System.arraycopy(transformMatrixArray, 0, mMVPMatrix, 0, Math.min(transformMatrixArray.length, mMVPMatrix.length));
        }

        public Transition flip(boolean x, boolean y) {
            GlUtil.flip(mMVPMatrix, x, y);
            return this;
        }

        public int getAngle() {
            return mAngle % 360;
        }

        public Transition rotate(float angle) {
            mAngle += angle;
            GlUtil.rotate(mMVPMatrix, angle);
            return this;
        }

        public Transition scale(float sx, float sy) {
            GlUtil.scale(mMVPMatrix, sx, sy);
            return this;
        }

        public Transition crop(ImageView.ScaleType scaleType, int rotation, int textureWidth, int textureHeight, int surfaceWidth, int surfaceHeight) {
            int targetWidth = (rotation % 180 == 90) ? textureHeight : textureWidth;
            int targetHeight = (rotation % 180 == 90) ? textureWidth : textureHeight;
            GlUtil.getShowMatrix(mMVPMatrix, scaleType, targetWidth, targetHeight, surfaceWidth, surfaceHeight);
            return this;
        }

        public Transition reverse() {
            float[] invertedMatrix = new float[16];
            if (Matrix.invertM(invertedMatrix, 0, mMVPMatrix, 0)) {
                System.arraycopy(invertedMatrix, 0, mMVPMatrix, 0, invertedMatrix.length);
            } else {
                Log.e(TAG, "Matrix inversion failed");
            }
            return this;
        }

        public float[] getMatrix() {
            return mMVPMatrix;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (float value : mMVPMatrix) {
                sb.append(value).append("  ");
            }
            return sb.toString();
        }
    }
}
