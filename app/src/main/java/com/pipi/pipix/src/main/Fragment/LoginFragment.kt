package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.ApplicationClass
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.loginButtonCheck.setOnClickListener {
            var nickName: String  = binding.loginEdittextNickname.text.toString()

            if(nickName.length > 0){
                ApplicationClass.prefs.userNickName = nickName
                findNavController().navigate(R.id.action_LoginFragement_to_ProfileFragment)
            }
            else{
                showCustomToast("닉네임을 입력해주세요")
            }
        }
    }
}