package com.pipi.pipix.src.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentLoginBinding
import com.pipi.pipix.databinding.FragmentTrtBinding
import com.pipi.pipix.src.warning.WarningActivity

class TRTFragment : BaseFragment<FragmentTrtBinding>(FragmentTrtBinding::bind, R.layout.fragment_trt) {
companion object{
    var trtType : Int? = null
}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.trtImageviewBtnBack.setOnClickListener {
            //뒤로가기
        }

        binding.trtTextviewMenu1.setOnClickListener {
            trtType = 1
            findNavController().navigate(R.id.action_TRTFragment_to_MP3Fragment)}
        binding.trtTextviewMenu2.setOnClickListener {
            trtType = 2
            findNavController().navigate(R.id.action_TRTFragment_to_MP3Fragment)}
        binding.trtTextviewMenu3.setOnClickListener {
            trtType = 3
            findNavController().navigate(R.id.action_TRTFragment_to_MP3Fragment)}
        binding.trtTextviewMenu4.setOnClickListener {
            trtType = 4
            findNavController().navigate(R.id.action_TRTFragment_to_MP3Fragment)}
        binding.trtTextviewMenu5.setOnClickListener {
            trtType = 5
            findNavController().navigate(R.id.action_TRTFragment_to_MP3Fragment)}
        binding.trtTextviewMenu6.setOnClickListener {
            trtType = 6
            findNavController().navigate(R.id.action_TRTFragment_to_MP3Fragment)}





    }
}


