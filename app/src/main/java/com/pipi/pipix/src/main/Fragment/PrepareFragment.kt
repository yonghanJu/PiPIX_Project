package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentPrepareBinding

class PrepareFragment : BaseFragment<FragmentPrepareBinding>(
    FragmentPrepareBinding::bind, R.layout.fragment_prepare) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonnext = binding.prepareButtonNext
        buttonnext.setOnClickListener {
            findNavController().navigate(R.id.action_prepareFragment_to_onBoardingFirstFragment)
        }


    }
}