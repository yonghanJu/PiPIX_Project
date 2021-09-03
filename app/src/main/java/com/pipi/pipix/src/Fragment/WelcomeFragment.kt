package com.pipi.pipix.src.Fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::bind, R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val buttonstart = binding.buttonStart
        buttonstart.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_prepareFragment)
        }

    }
}