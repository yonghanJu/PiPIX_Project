package com.pipi.pipix.src.main.Fragment

import android.annotation.SuppressLint
import android.media.*
import android.os.Build
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PureViewModel
import com.pipi.pipix.databinding.FragmentPureBinding
import com.pipi.pipix.src.main.SoundController.init
import com.pipi.pipix.src.main.SoundController.isStopMusicOfOtherApps
import com.pipi.pipix.src.main.SoundController.mAudioManager
import kotlin.concurrent.thread

class PureFragment : BaseFragment<FragmentPureBinding>(FragmentPureBinding::bind, R.layout.fragment_pure) {

    lateinit var viewModel: PureViewModel
//    private lateinit var  audioManager: AudioManager

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PureViewModel::class.java)
        var mediaPlayer = MediaPlayer.create(context, R.raw.beep)

        // 뷰 바인딩
        val count = binding.pureCountText
        val image = binding.pureImageviewImage
//        val buttonright = binding.pureButtonRight
//        val buttonleft = binding.pureButtonLeft
//        val buttoncheck = binding.pureButtonCheck

        // 카운트와 이미지의 Visibility 관찰
        viewModel.currentCountVisible.observe(viewLifecycleOwner, Observer {
            count.visibility = it
        })

        viewModel.currentImageVisible.observe(viewLifecycleOwner, Observer {
            image.visibility = it
        })

        // 다른 앱의 음악 끄기 SoundController 이용
        context?.let { init(it) }
        isStopMusicOfOtherApps()
        val device = mAudioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)[1].type

        val st = AudioManager.STREAM_MUSIC

        mAudioManager.setStreamVolume(st,5,1)
        Log.d("tag", mAudioManager.getStreamVolume(st).toString())
        Log.d("tag", device.toString())
        mediaPlayer.setVolume(0f,0.1f)

        val num = mAudioManager.getStreamVolumeDb(st, mAudioManager.getStreamVolume(st),4)
        Log.d("db", num.toString())
        thread (start = true){
            setImageGone()
            setCountVisibility()

            count.text = "3"
            sleep(1000)
            count.text = "2"
            sleep(1000)
            count.text = "1"
            sleep(1000)

            setCountGone()
            setImageVisible()
            mediaPlayer.start()
        }
    }

    // image setting function
    private fun setImageVisible(){
        viewModel.currentImageVisible.postValue(View.VISIBLE)
    }

    private fun setImageGone(){
        viewModel.currentImageVisible.postValue(View.GONE)
    }

    private fun setCountVisibility(){
        viewModel.currentCountVisible.postValue(View.VISIBLE)
    }

    private fun setCountGone(){
        viewModel.currentCountVisible.postValue(View.GONE)
    }
}