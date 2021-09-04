package com.pipi.pipix.src.Fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentOnBoardingFirstBinding

class OnBoardingFirstFragment : BaseFragment<FragmentOnBoardingFirstBinding>(FragmentOnBoardingFirstBinding::bind, R.layout.fragment_on_boarding_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonnext = binding.onBoardingFirstButtonNext
        buttonnext.setOnClickListener{
            findNavController().navigate(R.id.action_onBoardingFirstFragment_to_onBoardingSecondFragment)
        }


    }

}