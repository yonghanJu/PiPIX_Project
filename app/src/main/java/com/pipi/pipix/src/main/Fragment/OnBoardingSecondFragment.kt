package com.pipi.pipix.src.main.fragment

import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentOnBoardingSecondBinding
import java.util.*


class OnBoardingSecondFragment : BaseFragment<FragmentOnBoardingSecondBinding>(FragmentOnBoardingSecondBinding::bind, R.layout.fragment_on_boarding_second) {
    private var recorder: MediaRecorder? = null // 사용 하지 않을 때는 메모리해제 및  null 처리
    private val recordingFilePath: String by lazy {
        "${requireContext().externalCacheDir?.absolutePath}/recording.3gp"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.onBoardingSecondTextviewText2.text = "잠시만 기다려주세요"


        class RecorderTask(var recorder: MediaRecorder, val sound: TextView) : TimerTask() {
            var test = 0
            override fun run() {

                SystemClock.sleep(3000)

                mHandler?.post(Runnable {
                    val amplitude = recorder.maxAmplitude
                    val amplitudeDb = (20 * Math.log10(Math.abs(amplitude).toDouble()))
                    Log.d("test", (amplitudeDb - 10f).toString())
                    if(test == 0) {

                    }else if(amplitudeDb - 10f <= 30){
                    sound.text = "검사에 적합한 환경입니다. 진행하시겠습니까?"}
                    else{
                        sound.text = "검사에 적합하지 않은 환경입니다. 그래도 진행하시겠습니까?"
                    }
                    test++
                })

            }
        }
        fun startRecoding() {
            // 녹음 시작 시 초기화
            recorder = MediaRecorder()
                .apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 포멧
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 엔코더
                    setOutputFile(recordingFilePath) // 우리는 저장 x 캐시에
                    prepare()
                }
            recorder?.start()
            if (recorder != null) {
                val timer = Timer()
                timer.scheduleAtFixedRate(RecorderTask(recorder!!, binding.onBoardingSecondTextviewText2), 0, 500)


            }

        }

        startRecoding()


        binding.onBoardingSecondButtonNext.setOnClickListener {
            mHandler = null


            if(ProfileFragment.testType == 1){

               // findNavController().navigate(R.id.action_onBoardingSecondFragment_to_PureFragment)
                findNavController().navigate(R.id.action_onBoardingSecondFragment_to_pureFragment2)}
            else{
                findNavController().navigate(R.id.action_onBoardingSecondFragment_to_speechFragment)}
            }
        }


    private var mHandler: Handler? = Handler(Looper.getMainLooper())

    override fun onPause() {


        super.onPause()
        mHandler = null
    }

    }

