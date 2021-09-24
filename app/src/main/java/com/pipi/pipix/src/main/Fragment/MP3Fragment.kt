package com.pipi.pipix.src.main.fragment

import android.Manifest
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentMp3Binding
import com.pipi.pipix.databinding.FragmentTrtBinding
import com.pipi.pipix.src.main.fragment.TRTFragment.Companion.trtType
import java.io.IOException
import kotlin.concurrent.timer

class MP3Fragment : BaseFragment<FragmentMp3Binding>(FragmentMp3Binding::bind, R.layout.fragment_mp3) {


    var mediaPlayer : MediaPlayer? = null
    var check : Boolean? = false

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            val seekBar = binding.mp3SeekBar
            val start = binding.mp3ButtonStart
            val stop = binding.mp3ButtonStop
            val time = binding.mp3TextviewTimer

            var timeTick = 0
            var minute = 0
            var second = 0



        binding.mp3ImageviewBtnBack.setOnClickListener {
        //뒤로가기
            findNavController().popBackStack()
        }

        var title = binding.mp3TextviewTitle
        when (trtType){
            1 ->  {title.text = "파도소리"
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.play1) }
            2 ->  {title.text = "숲소리"
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.play2)}
            3 ->  {title.text = "빗소리"
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.play3)}
            4 ->  {title.text = "불소리"
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.play4)}
            5 ->  {title.text = "기차소리"
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.play5)}
            6 ->  {title.text = "백색소음"
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.play6)}
            else -> showCustomToast("오류 발생")
        }

            mediaPlayer?.isLooping = true

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    timeTick = seekBar!!.progress
                    if(fromUser){
                        time.text = String.format("%02d : %02d",timeTick/60, timeTick%60)
                    }


                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    timeTick = seekBar!!.progress
                    time.text = String.format("%02d : %02d",timeTick/60, timeTick%60)
                    check = true
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    timeTick = seekBar!!.progress
                    time.text = String.format("%02d : %02d",timeTick/60, timeTick%60)
                    check = true
                }
            })

            start.setOnClickListener {
                if (check == true) {
                    mediaPlayer?.start()
                    seekBar.isEnabled = false
                    start.visibility = GONE
                    stop.visibility = VISIBLE
                    Log.d("test",timeTick.toString())
                    minute = timeTick / 60
                    second = timeTick % 60
                    timer(period = 1000, initialDelay = 1000) {

                        mHandler?.post(Runnable {
                            time.text = String.format("%02d : %02d", minute, second)
                            Log.d("test",String.format("%02d : %02d", minute, second))
                            seekBar.progress = minute * 60
                        })

                        if (second == 0 && minute == 0) {
                            println("\n타이머 종료")
                            mediaPlayer?.pause()
                            check = false
                            cancel()

                        }
                        if (second == 0) {
                            minute--
                            second = 60
                        }
                        second--
                        stop.setOnClickListener {
                            mediaPlayer?.pause()
                            stop.visibility = GONE
                            start.visibility = VISIBLE
                            seekBar.isEnabled = true
                            cancel()
                        }
                    }
                }
            }


        }

    private var mHandler: Handler? = Handler(Looper.getMainLooper())

    override fun onPause() {
        super.onPause()
            mediaPlayer?.stop()
            try {
                mediaPlayer?.prepare()
            } catch (ie: IOException) {
                ie.printStackTrace()
            }
        mHandler = null
    }






}