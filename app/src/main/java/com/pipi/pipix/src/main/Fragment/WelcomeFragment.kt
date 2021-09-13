package com.pipi.pipix.src.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.ApplicationClass
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentWelcomeBinding
import com.pipi.pipix.src.consent.ConsentActivity

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::bind, R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(ApplicationClass.prefs.consent == false){
            val intent = Intent(getContext(), ConsentActivity::class.java)
            startActivity(intent)
        }

        val buttonstart = binding.welcomeButtonStart


        buttonstart.setOnClickListener {
            if (ApplicationClass.prefs.consent) {
                if (ApplicationClass.prefs.userNickName == null) {
                    findNavController().navigate(R.id.action_welcomeFragment_to_LoginFragement)
                } else {
                    findNavController().navigate(R.id.action_welcomeFragment_to_ProfileFragment)
                }
            }
        }

    }
}