package com.pipi.pipix.src.main.Fragment

import android.annotation.SuppressLint
import android.media.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PureViewModel
import com.pipi.pipix.databinding.FragmentPureBinding
import com.pipi.pipix.src.main.PureTest
import com.pipi.pipix.src.main.SoundController.isStopMusicOfOtherApps
import com.pipi.pipix.src.main.SoundController.mAudioManager
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class PureFragment : BaseFragment<FragmentPureBinding>(FragmentPureBinding::bind, R.layout.fragment_pure) {

    private lateinit var viewModel: PureViewModel
    private lateinit var buttonRight:Button
    private lateinit var buttonCheck:Button
    private lateinit var buttonLeft:Button
    private lateinit var textCount:TextView
    private lateinit var imageSound: ImageView

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PureViewModel::class.java)

        // 뷰 바인딩
        textCount = binding.pureCountText
        imageSound = binding.pureImageviewImage
        buttonRight = binding.pureButtonRight
        buttonLeft = binding.pureButtonLeft
        buttonCheck = binding.pureButtonCheck


        // 카운트와 이미지의 Visibility 관찰
        viewModel.currentCountVisible.observe(viewLifecycleOwner, Observer {
            textCount.visibility = it
        })

        viewModel.currentImageVisible.observe(viewLifecycleOwner, Observer {
            imageSound.visibility = it
        })

        // 다른 앱의 음악 끄기 SoundController 이용
        isStopMusicOfOtherApps()

        // 볼륨 조절
        val st = AudioManager.STREAM_MUSIC
        mAudioManager.setStreamVolume(st,15,1)

        val test = context?.let { PureTest(buttonRight,buttonLeft,buttonCheck,textCount,viewModel, it) }
        thread { test?.doTest() }
    }
}