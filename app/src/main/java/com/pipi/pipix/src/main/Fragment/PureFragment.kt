package com.pipi.pipix.src.main.Fragment

import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PureViewModel
import com.pipi.pipix.databinding.FragmentPureBinding
import kotlin.concurrent.thread

class PureFragment : BaseFragment<FragmentPureBinding>(FragmentPureBinding::bind, R.layout.fragment_pure) {

    lateinit var viewModel: PureViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PureViewModel::class.java)
        var mediaPlayer = MediaPlayer.create(context, R.raw.beep)

        // 숫자 카운트와 이미지 할당
        val count = binding.pureCountText
        val image = binding.pureImageviewImage

        val btnright= binding.pureButtonRight

        btnright.setOnClickListener{
            Log.d("tag","Clicked")
        }

        // 숫자 카운트와 이미지의 Visibility 관찰
        viewModel.currentCountVisible.observe(viewLifecycleOwner, Observer {
            count.visibility = it
        })

        viewModel.currentImageVisible.observe(viewLifecycleOwner, Observer {
            image.visibility = it
        })



        thread (start = true){
            sleep(1000)
            count.text = "2"
            sleep(1000)
            count.text = "1"
            sleep(1000)
            mediaPlayer.start()
            setCountGone()
            setImageVisible()
        }


    }

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