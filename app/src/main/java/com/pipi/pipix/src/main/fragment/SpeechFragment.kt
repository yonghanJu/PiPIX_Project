package com.pipi.pipix.src.main.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.media.AudioManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.data.PureResult
import com.pipi.pipix.databinding.FragmentSpeechBinding
import com.pipi.pipix.src.main.SoundController
import com.pipi.pipix.testpackage.SpeechTest
import com.pipi.pipix.testpackage.SpeechViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SpeechFragment : BaseFragment<FragmentSpeechBinding>(
    FragmentSpeechBinding::bind, R.layout.fragment_speech) {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener
    private lateinit var matches: ArrayList<String>
    private lateinit var speechViewModel: SpeechViewModel
    private lateinit var speechTest: SpeechTest
    private lateinit var prViewModel: PRViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var isPause = false
    private var tpaRight = 0
    private var tpaLeft = 0


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 다른 앱의 음악 끄기 SoundController 이용
        SoundController.isStopMusicOfOtherApps()

        // 볼륨 조절
        val st = AudioManager.STREAM_MUSIC
        SoundController.mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,1,1)
        SoundController.mAudioManager.setStreamVolume(st,15,1)


        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,activity?.packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR")

        setListener()
        
        prViewModel = ViewModelProvider(this).get(PRViewModel::class.java)
        prViewModel.readAllData.observe(viewLifecycleOwner, Observer { list ->
            tpaLeft = list[list.size-1].tpaLeft
            tpaRight = list[list.size -1].tpaRight
        })

        // 테스트 객체 생성
        speechViewModel = ViewModelProvider(this).get(SpeechViewModel::class.java)
        speechTest= SpeechTest(tpaRight, tpaLeft, binding.speechCountText, speechViewModel, prViewModel, requireContext())


        speechViewModel.currentCountVisible.observe(viewLifecycleOwner, Observer {
            binding.speechCountText.visibility = it
        })

        speechViewModel.currentImageVisible.observe(viewLifecycleOwner, Observer {
            binding.speechImageviewImage.visibility = it
        })

        speechViewModel.currentClickable.observe(viewLifecycleOwner,{
            binding.speechButtonRight.isEnabled = it
        })


        speechViewModel.currentProgress.postValue(0)
        speechViewModel.currentProgress.observe(viewLifecycleOwner, {
            if(it<100){
                progressBar.progress = it
                progressText.text = "${it}%"
            }else {
                progressBar.progress = 100
                progressText.text = "100%"
            }
        })

        binding.speechButtonRight.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
                    speechRecognizer.setRecognitionListener(recognitionListener)
                    speechRecognizer.startListening(intent)
                    speechTest.recordStart()
                }
            }
            true
        }

        binding.speechImageviewBtnBack.setOnClickListener {
            speechTest.pause()
            isPause = true
            findNavController().navigate(R.id.action_speechFragment_to_ProfileFragment)
        }


        val scope = CoroutineScope(CoroutineName("SpeechTest"))
        val speechTestJob = scope.launch {
            if(speechTest.doTest1(1) && speechTest.doTest1(0) && speechTest.doTest2(1) && speechTest.doTest2(0)){
                // 성공적으로 테스트 완료시
                val result = speechTest.getResult()
                val now = System.currentTimeMillis()
                val date =  Date(now)
                val sdf =  SimpleDateFormat("yyyy.MM.dd a hh시 mm분")
                val pr = PureResult(0,2,result[0],result[1],sdf.format(date),result[2],result[3])
                prViewModel.addPureResult(pr)
                isPause = true
                activity?.runOnUiThread { findNavController().navigate(R.id.action_speechFragment_to_resultFragment) }
            }
        }

    }

    private fun setListener() {
        recognitionListener = object: RecognitionListener{
            override fun onReadyForSpeech(params: Bundle?) {}

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                var message = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                    SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                    SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트워크 타임아웃"
                    SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER가 바쁨"
                    SpeechRecognizer.ERROR_SERVER -> "서버 오류"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                    else -> "알 수 없는 오류"
                }
                if(error != SpeechRecognizer.ERROR_NO_MATCH) Toast.makeText(context, "에러 발생 $message", Toast.LENGTH_SHORT).show()
                speechTest.recordText("")
            }

            override fun onResults(results: Bundle?) {
                matches= results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>
                for (i in 0 until matches.size) {
                    binding.tvResult.text = matches[i]
                    speechTest.recordText(matches[i])
                    Log.d("tag",matches.size.toString())
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}

        }

        // backButton Pressed handle
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            speechTest.pause()
            isPause = true
            findNavController().navigate(R.id.action_speechFragment_to_ProfileFragment)
            showCustomToast("어음청력검사가 취소 되었습니다.")
        }
    }
    override fun onPause() {
        speechTest.pause()
        SoundController.mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,7,1)
        if(!isPause)findNavController().navigate(R.id.action_speechFragment_to_ProfileFragment)
        super.onPause()
    }
}