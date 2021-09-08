package com.pipi.pipix.src.main

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioDeviceInfo
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.util.Log

object SoundController {
    private const val TAG: String = "SoundController"

    lateinit var mAudioManager: AudioManager
    private lateinit var mBluetoothAdapter: BluetoothAdapter

    // 초기화
    fun init(context: Context) {
        mAudioManager = context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    // 다른 앱에서 재생되는 음악 일시 중지
    fun isStopMusicOfOtherApps() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mAudioManager.requestAudioFocus(AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build())
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener {}
                .build())
        } else
            mAudioManager!!.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }

    // 블루투스 지원 여부
    fun isSupportBluetooth() = mBluetoothAdapter != null

    // 블루투스 활성화 여부
    fun isActiveBluetooth() = mBluetoothAdapter!!.isEnabled

    // 블루투스 기기 연결 여부
    fun isConnectBluetooth(profile: Int) =
        mBluetoothAdapter!!.getProfileConnectionState(profile) == BluetoothProfile.STATE_CONNECTED

    // 블루투스 전원 제어
    fun bluetoothTrigger() {
        when (mBluetoothAdapter!!.isEnabled) {
            true -> {
                mBluetoothAdapter!!.disable()
                Log.d(TAG, "Bluetooth Off")
            }
            false -> {
                mBluetoothAdapter!!.enable()
                Log.d(TAG, "Bluetooth On")
            }
        }
    }

    // 헤드셋, 이어폰 활성화 여부
    @SuppressLint("WrongConstant")
    fun isActiveHeadset() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val devices = mAudioManager!!.getDevices(AudioManager.GET_DEVICES_ALL)
            val types = listOf(AudioDeviceInfo.TYPE_WIRED_HEADPHONES, AudioDeviceInfo.TYPE_WIRED_HEADSET)
            devices.any {it.type in types}
        } else
            mAudioManager!!.isWiredHeadsetOn
}
