package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentOnBoardingFirstBinding
import com.pipi.pipix.databinding.FragmentPurenoticeBinding

class PureNoticeFragment  : BaseFragment<FragmentPurenoticeBinding>(
    FragmentPurenoticeBinding::bind, R.layout.fragment_purenotice) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.purenoticeButtonNext.setOnClickListener {
            findNavController().navigate(R.id.action_pureNoticeFragment_to_prepareFragment)
        }
    }

}