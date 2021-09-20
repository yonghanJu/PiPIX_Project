package com.pipi.pipix.src.main.fragment

import android.Manifest
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
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

class MP3Fragment : BaseFragment<FragmentMp3Binding>(FragmentMp3Binding::bind, R.layout.fragment_mp3) {


    var seekBar : SeekBar? = null
    var timer : TextView? = null
    var mediaPlayer : MediaPlayer? = null
    var check : Boolean? = false


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


        timer = binding.mp3TextviewTimer
        seekBar = binding.mp3SeekBar

        binding.mp3ImageviewBtnBack.setOnClickListener {
        //뒤로가기
            findNavController().popBackStack()
        }

        var title = binding.mp3TextviewTitle
        when (trtType){
            1 ->  {title.text = "파도소리"
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.play1)}
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
        seekBar!!.setVisibility(ProgressBar.VISIBLE)
        seekBar!!.setMax(mediaPlayer!!.getDuration())

        seekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }
                val m = progress / 60000
                val s = progress % 60000 / 1000
                val strTime = String.format("%02d:%02d", m, s)
                timer!!.text = strTime
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        var btn = binding.mp3ButtonAction

        btn.setOnClickListener {
            btn.setImageResource(R.drawable.iconpause)
            if(check == false){
                seekBar!!.setVisibility(ProgressBar.VISIBLE)
                seekBar!!.setMax(mediaPlayer!!.getDuration())
                mediaPlayer!!.start()
                Thread()
                check = true}
            else{
                if(mediaPlayer!!.isPlaying()) {
                    btn.setImageResource(R.drawable.iconplay)
                    mediaPlayer!!.pause()
                    check = false

                }

            }
        }




    }

    override fun onPause() {
        super.onPause()
            mediaPlayer!!.stop()
            try {
                mediaPlayer!!.prepare()
            } catch (ie: IOException) {
                ie.printStackTrace()
            }
            mediaPlayer!!.seekTo(0)
    }

    fun Thread() {
        val task = Runnable {
            // 음악이 재생중일때
            while (mediaPlayer!!.isPlaying) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                seekBar!!.progress = mediaPlayer!!.currentPosition
            }
        }
        var thread = Thread(task)
            thread.start()
        }




}