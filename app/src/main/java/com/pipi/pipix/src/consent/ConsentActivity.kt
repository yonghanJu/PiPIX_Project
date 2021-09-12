package com.pipi.pipix.src.consent

import android.content.Intent
import android.os.Bundle
import com.pipi.pipix.config.ApplicationClass
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivityConsentBinding
import com.pipi.pipix.src.consent.contents.ContentsActivity

class ConsentActivity : BaseActivity<ActivityConsentBinding>(ActivityConsentBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.consentTextviewContents.setOnClickListener {
            val intent = Intent(this, ContentsActivity::class.java)
            startActivity(intent)
        }

        binding.consentButtonCheck.setOnClickListener {
            if (binding.consentCheckboxCheck.isChecked == true){
                ApplicationClass.prefs.consent = true
                finish()
            }
            else{
                showCustomToast("필수 사항을 확인해주세요.")
            }
        }

        setScreen()

    }

    private fun setScreen(){
        //Set FullScreen, No actionbar
        supportActionBar?.hide()
    }
}
