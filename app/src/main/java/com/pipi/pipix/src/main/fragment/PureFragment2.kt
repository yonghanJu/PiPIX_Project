package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.databinding.FragmentProfileBinding
import com.pipi.pipix.databinding.FragmentPure2Binding

class PureFragment2  : BaseFragment<FragmentPure2Binding>(FragmentPure2Binding::bind, R.layout.fragment_pure2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pure2Animation.loop(true)
    }
}
