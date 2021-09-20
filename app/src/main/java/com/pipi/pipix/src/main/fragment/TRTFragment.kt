package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentLoginBinding
import com.pipi.pipix.databinding.FragmentTrtBinding

class TRTFragment : BaseFragment<FragmentTrtBinding>(FragmentTrtBinding::bind, R.layout.fragment_trt) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.trtImageviewBtnBack.setOnClickListener {
            //뒤로가기
        }

    }

}