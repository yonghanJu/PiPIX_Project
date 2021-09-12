package com.pipi.pipix.src.speechresult

import android.os.Bundle
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivitySpeechresultBinding

class SpeechResultActivity  : BaseActivity<ActivitySpeechresultBinding>(ActivitySpeechresultBinding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.speechresultButtonBack.setOnClickListener {
            finish()
        }
    }
}