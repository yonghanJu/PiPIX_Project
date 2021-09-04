package com.pipi.pipix.src.Fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.ApplicationClass
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentProfileBinding
import com.pipi.pipix.databinding.FragmentResultBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileTextviewNickname.text = ApplicationClass.prefs.userNickName

        binding.profileButtonTest1.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_prepareFragment)
        }
    }
}