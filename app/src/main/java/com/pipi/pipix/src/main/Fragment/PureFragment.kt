package com.pipi.pipix.src.main.fragment

import android.annotation.SuppressLint
import android.media.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.data.PureViewModel
import com.pipi.pipix.databinding.FragmentPureBinding
import com.pipi.pipix.src.main.PureTest
import com.pipi.pipix.src.main.SoundController.isStopMusicOfOtherApps
import com.pipi.pipix.src.main.SoundController.mAudioManager
import kotlinx.coroutines.*

class PureFragment : BaseFragment<FragmentPureBinding>(FragmentPureBinding::bind, R.layout.fragment_pure) {

    private lateinit var viewModel: PureViewModel
    private lateinit var resultViewModel:PRViewModel
    private lateinit var buttonRight:Button
    private lateinit var buttonCheck:Button
    private lateinit var buttonLeft:Button
    private lateinit var textCount:TextView
    private lateinit var imageSound: ImageView

    @InternalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰 바인딩
        textCount = binding.pureCountText
        imageSound = binding.pureImageviewImage
        buttonRight = binding.pureButtonRight
        buttonLeft = binding.pureButtonLeft
        buttonCheck = binding.pureButtonCheck


        // 카운트와 이미지의 Visibility 관찰
        viewModel = ViewModelProvider(this).get(PureViewModel::class.java)

        viewModel.currentCountVisible.observe(viewLifecycleOwner, Observer {
            textCount.visibility = it
        })

        viewModel.currentImageVisible.observe(viewLifecycleOwner, Observer {
            imageSound.visibility = it
        })

        // 검사결과 데이터 뷰모델
        resultViewModel = ViewModelProvider(this).get(PRViewModel::class.java)

        // 다른 앱의 음악 끄기 SoundController 이용
        isStopMusicOfOtherApps()

        // 볼륨 조절
        val st = AudioManager.STREAM_MUSIC
        mAudioManager.setStreamVolume(st,15,1)

        // PureTest 객체 생성
        val pureTest = context?.let { PureTest(buttonRight,buttonLeft,buttonCheck,textCount,viewModel,resultViewModel, it,false) }

        //// 코루틴스코프안에서 테스트 진행
        //val scope = CoroutineScope(CoroutineName("PureTest"))
        val scope = CoroutineScope(CoroutineName("PureTest"))
        val testInCoroutine = scope.launch {
            if(pureTest!!.doTest()) activity?.runOnUiThread { findNavController().navigate(R.id.action_PureFragment_to_ProfileFragment) }
        }
        testInCoroutine.start()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            pureTest?.cancel()
            testInCoroutine.cancel()
            findNavController().popBackStack()

            Toast.makeText(context,"순음청력검사가 취소 되었습니다.",Toast.LENGTH_LONG).show()
        }
    }
}