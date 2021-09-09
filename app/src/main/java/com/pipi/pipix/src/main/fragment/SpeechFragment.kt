package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentSpeechBinding
import com.pipi.pipix.databinding.FragmentSpeechnoticeBinding

class SpeechFragment : BaseFragment<FragmentSpeechBinding>(
    FragmentSpeechBinding::bind, R.layout.fragment_speech) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}