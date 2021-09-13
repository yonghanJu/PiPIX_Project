package com.pipi.pipix.src.main.fragment

import android.icu.text.SimpleDateFormat
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.data.PureResult
import com.pipi.pipix.databinding.FragmentPure2Binding
import com.pipi.pipix.src.main.SoundController
import com.pipi.pipix.testpackage.PureTest2
import com.pipi.pipix.testpackage.PureTest2ViewModel
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import java.util.*

class PureFragment2  : BaseFragment<FragmentPure2Binding>(FragmentPure2Binding::bind, R.layout.fragment_pure2) {

    private lateinit var result: MutableList<MutableList<Int>>
    private lateinit var viewModel: PRViewModel
    private lateinit var ptViewModel: PureTest2ViewModel
    private lateinit var pureTest: PureTest2
    private lateinit var hzText: TextView
    private lateinit var direcText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var scope: CoroutineScope
    private  var isPause:Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        SoundController.isStopMusicOfOtherApps()


        viewModel = ViewModelProvider(this).get(PRViewModel::class.java)
        ptViewModel = ViewModelProvider(this).get(PureTest2ViewModel::class.java)
        pureTest = PureTest2(binding.pure2ButtonYes,binding.pure2ButtonNo,requireContext(), ptViewModel)

        // 볼륨 조절
        val st = AudioManager.STREAM_MUSIC
        SoundController.mAudioManager.setStreamVolume(st,15,1)

        hzText = binding.pure2TextviewText2
        direcText = binding.pure2TextviewText3
        progressBar = binding.progress2
        progressText = binding.pure2TextviewProgress

        ptViewModel.hzText.observe(viewLifecycleOwner, Observer {
            hzText.text = it
        })

        ptViewModel.direcText.observe(viewLifecycleOwner, Observer {
            direcText.text = it
        })

        ptViewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it<100){
                progressBar.progress = it
                progressText.text = "${it}%"
            }else {
                progressBar.progress = 100
                progressText.text = "100%"
            }
        })

        ptViewModel.setProgress(0)


        scope = CoroutineScope(CoroutineName("scope"))
        val testJob = scope.launch {
            if(pureTest.doTest(1) && pureTest.doTest(0)) {
                result = pureTest.getResult()
                val now = System.currentTimeMillis()
                val date =  Date(now)
                val sdf =  SimpleDateFormat("yyyy.MM.dd a hh시 mm분")
                val pr = PureResult(0,1,pureTest.getTpa(1),pureTest.getTpa(0),sdf.format(date)
                    ,null,null, result[1][4], result[1][5], result[1][0],result[1][1],result[1][2],result[1][3]
                    , result[0][4], result[0][5], result[0][0],result[0][1],result[0][2],result[0][3])
                viewModel.addPureResult(pr)
                activity?.runOnUiThread {
                    isPause = true
                    findNavController().navigate(R.id.action_pureFragment2_to_ProfileFragment) }

            }
        }

        // 코루틴으로 시작
        testJob.start()

        binding.pure2ButtonPause.setOnClickListener {
            pureTest.pause()
            isPause = true
            findNavController().navigate(R.id.action_pureFragment2_to_ProfileFragment)
            showCustomToast("순음청력검사가 취소 되었습니다.")
        }

        // backButton Pressed handle
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            pureTest.pause()
            isPause = true
            findNavController().navigate(R.id.action_pureFragment2_to_ProfileFragment)
            showCustomToast("순음청력검사가 취소 되었습니다.")
        }

    }

    override fun onPause() {
        pureTest.pause()
        if(!isPause)findNavController().navigate(R.id.action_pureFragment2_to_ProfileFragment)
        showCustomToast("순음청력검사가 취소 되었습니다.")
        super.onPause()
    }
}
