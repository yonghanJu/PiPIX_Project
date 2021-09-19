package com.pipi.pipix.src.speechresult

import android.os.Bundle
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.data.PureResult
import com.pipi.pipix.databinding.ActivitySpeechresultBinding

class SpeechResultActivity  : BaseActivity<ActivitySpeechresultBinding>(ActivitySpeechresultBinding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.speechresultButtonBack.setOnClickListener {
            finish()
        }

        val data = intent.getSerializableExtra("test") as PureResult

        binding.speechresultTextviewLeftsrt.text = data.tpaLeft.toString() + "dB"
        binding.speechresultTextviewRightsrt.text = data.tpaRight.toString()  + "dB"
        binding.speechresultTextviewLeftwrs.text = data.scoreLeft.toString() + "/10"
        binding.speechresultTextviewRightwrs.text = data.scoreRight.toString() + "/10"

        setScreen()
    }

    private fun setScreen(){
        //Set FullScreen, No actionbar
        supportActionBar?.hide()
    }
}