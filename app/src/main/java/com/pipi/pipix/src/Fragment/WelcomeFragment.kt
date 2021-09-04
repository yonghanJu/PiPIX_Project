package com.pipi.pipix.src.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.ApplicationClass
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::bind, R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val buttonstart = binding.welcomeButtonStart

        buttonstart.setOnClickListener {
            if(ApplicationClass.prefs.userNickName == null){
                findNavController().navigate(R.id.action_welcomeFragment_to_LoginFragement)}
            else{
                findNavController().navigate(R.id.action_welcomeFragment_to_ProfileFragment)}
        }

    }
}