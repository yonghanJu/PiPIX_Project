package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentPurenoticeBinding
import com.pipi.pipix.databinding.FragmentSpeechnoticeBinding

class SpeechNoticeFragment : BaseFragment<FragmentSpeechnoticeBinding>(
    FragmentSpeechnoticeBinding::bind, R.layout.fragment_speechnotice) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.speechnoticeButtonNext.setOnClickListener {
            findNavController().navigate(R.id.action_speechNoticeFragment_to_prepareFragment)
        }
    }

}