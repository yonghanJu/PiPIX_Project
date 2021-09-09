package com.pipi.pipix.src.main.Fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentOnBoardingSecondBinding


class OnBoardingSecondFragment : BaseFragment<FragmentOnBoardingSecondBinding>(FragmentOnBoardingSecondBinding::bind, R.layout.fragment_on_boarding_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onBoardingSecondButtonNext.setOnClickListener {

            if(ProfileFragment.testType == 1){
                findNavController().navigate(R.id.action_onBoardingSecondFragment_to_PureFragment)}
            else{
                findNavController().navigate(R.id.action_onBoardingSecondFragment_to_speechFragment)}
            }
        }

    }

