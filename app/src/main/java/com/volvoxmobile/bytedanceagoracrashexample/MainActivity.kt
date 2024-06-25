package com.volvoxmobile.bytedanceagoracrashexample

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.volvoxmobile.bytedanceagoracrashexample.bytedance.AppConstants
import com.volvoxmobile.bytedanceagoracrashexample.bytedance.ByteDanceBeautySDK
import com.volvoxmobile.bytedanceagoracrashexample.bytedance.ByteDanceBeautyBottomSheetFragment
import com.volvoxmobile.bytedanceagoracrashexample.bytedance.utils.ReflectUtils
import com.volvoxmobile.bytedanceagoracrashexample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.agora.beautyapi.bytedance.CameraConfig
import io.agora.beautyapi.bytedance.CaptureMode
import io.agora.beautyapi.bytedance.Config
import io.agora.beautyapi.bytedance.EventCallback
import io.agora.beautyapi.bytedance.createByteDanceBeautyAPI
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
import io.agora.rtc2.Constants.CLIENT_ROLE_BROADCASTER
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration
import io.agora.rtc2.video.VideoEncoderConfiguration.FRAME_RATE

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mByteDanceApi by lazy {
        createByteDanceBeautyAPI()
    }
    private val renderManager = ByteDanceBeautySDK.renderManager

    private var cameraConfig = CameraConfig()

    private var beautyEnable = true


    private lateinit var binding: ActivityMainBinding

    private val chanelName = "1000"

    private val mRtcEngine by lazy {
        RtcEngine.create(RtcEngineConfig().apply {
            mContext = applicationContext
            mAppId = AppConstants.AGORA_APP_ID
            mEventHandler = object : IRtcEngineEventHandler() {
                override fun onError(err: Int) {
                    super.onError(err)
                }

                override fun onUserJoined(uid: Int, elapsed: Int) {
                    super.onUserJoined(uid, elapsed)
                    runOnUiThread {
                        if (binding.remoteVideoView.tag == null) {
                            binding.remoteVideoView.tag = uid
                            val renderView = SurfaceView(applicationContext)
                            renderView.setZOrderMediaOverlay(true)
                            renderView.setZOrderOnTop(true)
                            binding.remoteVideoView.addView(renderView)
                        }
                    }
                }
            }
        }).apply {
            enableExtension("agora_video_filters_clear_vision", "clear_vision", true)
        }
    }


    private val mRtcHandler = object : IRtcEngineEventHandler() {
        override fun onError(err: Int) {
            super.onError(err)
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            runOnUiThread {
                if (binding.remoteVideoView.tag == null) {
                    binding.remoteVideoView.tag = uid
                    val renderView = SurfaceView(applicationContext)
                    renderView.setZOrderMediaOverlay(true)
                    renderView.setZOrderOnTop(true)
                    binding.remoteVideoView.addView(renderView)
                    mRtcEngine.setupRemoteVideo(
                        VideoCanvas(
                            renderView, Constants.RENDER_MODE_HIDDEN, uid
                        )
                    )
                }
            }
        }
    }

    private val mVideoEncoderConfiguration by lazy {
        VideoEncoderConfiguration(
            ReflectUtils.getStaticFiledValue(
                VideoEncoderConfiguration::class.java, "VD_640x480"
            ), ReflectUtils.getStaticFiledValue(
                FRAME_RATE::class.java, FRAME_RATE.FRAME_RATE_FPS_15.name
            ), 0, VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        checkPermissions()
        initRtcEngine()
        initializeCamera()
        initBeautyApi()

        binding.btnEffect.setOnClickListener {
            initByteDanceBeautyBottomSheet()
        }
    }

    private fun initRtcEngine() {
        // Config RtcEngine
        mRtcEngine.addHandler(mRtcHandler)
        mRtcEngine.setVideoEncoderConfiguration(mVideoEncoderConfiguration)
        mRtcEngine.enableVideo()

        // join channel
        mRtcEngine.joinChannel(null, chanelName, 0, ChannelMediaOptions().apply {
            channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            publishCameraTrack = true
            publishMicrophoneTrack = false
            autoSubscribeAudio = false
            autoSubscribeVideo = true
        })
    }

    private fun initByteDanceBeautyBottomSheet() {
        if (ByteDanceBeautyBottomSheetFragment.isShowing().not()) {
            val byteDanceBeautyBottomSheetFragment = ByteDanceBeautyBottomSheetFragment.newInstance()
            byteDanceBeautyBottomSheetFragment.show(supportFragmentManager, ByteDanceBeautyBottomSheetFragment::class.java.simpleName)
            byteDanceBeautyBottomSheetFragment.setCameraStatusChangeClickListener {
                mRtcEngine.switchCamera()
            }
            byteDanceBeautyBottomSheetFragment.setDismissClickListener {
                byteDanceBeautyBottomSheetFragment.dismiss()
            }
            byteDanceBeautyBottomSheetFragment.setBeautyEnabledClickListener {
                beautyEnable = !beautyEnable
                mByteDanceApi.enable(beautyEnable)
            }
        }
    }

    private fun initBeautyApi() {
        mByteDanceApi.initialize(
            Config(
                this,
                mRtcEngine,
                renderManager,
                captureMode = CaptureMode.Agora,
                statsEnable = true,
                cameraConfig = cameraConfig,
                eventCallback = EventCallback(
                    onBeautyStats = { stats ->
                        Log.d("initBeautyApi", "BeautyStats stats = $stats")
                    },
                    onEffectInitialized = {
                        ByteDanceBeautySDK.initEffect(this)
                    },
                    onEffectDestroyed = {
                        ByteDanceBeautySDK.unInitEffect()
                    }
                )
            )
        )

        mByteDanceApi.enable(beautyEnable)

        // render local video
        mByteDanceApi.setupLocalVideo(binding.frameLayoutVideoContainer, io.agora.rtc2.Constants.RENDER_MODE_HIDDEN)
    }

    private fun initializeCamera() {
        val videoConfig = VideoEncoderConfiguration()
        videoConfig.frameRate = VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15.value
        videoConfig.bitrate = VideoEncoderConfiguration.COMPATIBLE_BITRATE
        videoConfig.dimensions = VideoEncoderConfiguration.VD_640x480
        videoConfig.orientationMode = VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE
        videoConfig.degradationPrefer = VideoEncoderConfiguration.DEGRADATION_PREFERENCE.MAINTAIN_FRAMERATE
        videoConfig.mirrorMode = VideoEncoderConfiguration.MIRROR_MODE_TYPE.MIRROR_MODE_ENABLED
        mRtcEngine.setVideoEncoderConfiguration(videoConfig)
        mRtcEngine.enableDualStreamMode(true)
        mRtcEngine.setChannelProfile(CHANNEL_PROFILE_LIVE_BROADCASTING)
        mRtcEngine.setClientRole(CLIENT_ROLE_BROADCASTER)
        setupLocalVideo()
    }

    private fun setupLocalVideo() {
        if (binding.frameLayoutVideoContainer.childCount > 0) {
            binding.frameLayoutVideoContainer.removeAllViews()
        }
        val surfaceView = RtcEngine.CreateRendererView(this)
        surfaceView.setZOrderMediaOverlay(true)
        binding.frameLayoutVideoContainer.addView(surfaceView)
        mRtcEngine.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0))
        mRtcEngine.startPreview()
    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO), PERMISSION_REQ_ID)
        }
    }

    companion object {
        private const val PERMISSION_REQ_ID = 22
    }



    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine.leaveChannel()
        mRtcEngine.stopPreview()
        mByteDanceApi.release()
        RtcEngine.destroy()
    }
}