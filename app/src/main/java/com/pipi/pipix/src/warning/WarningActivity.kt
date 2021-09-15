package com.pipi.pipix.src.warning

import android.os.Bundle
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivityConsentBinding
import com.pipi.pipix.databinding.ActivityWarningBinding

class WarningActivity  : BaseActivity<ActivityWarningBinding>(ActivityWarningBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.consentButtonCheck.setOnClickListener {
            finish()
        }
    }
}