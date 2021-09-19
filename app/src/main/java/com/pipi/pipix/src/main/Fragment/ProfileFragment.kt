package com.pipi.pipix.src.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pipi.pipix.R
import com.pipi.pipix.config.ApplicationClass
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.data.PureResult
import com.pipi.pipix.databinding.ActivityWarningBinding
import com.pipi.pipix.databinding.FragmentProfileBinding
import com.pipi.pipix.src.chart.ChartActivity
import com.pipi.pipix.src.main.fragment.ResultFragment.Companion.recyclerviewAdapter
import com.pipi.pipix.src.warning.WarningActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.withTestContext
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {



    companion object {
        var testType : Int? = null // 1이면 순음 2이면 어음
        var dataList = emptyList<PureResult>()
        lateinit var mUserViewModel : PRViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testType = null//뒤로가기로 인해 다시 화면에 돌아오면 null 처리

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(PRViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            recyclerviewAdapter.setData(user)
        })



        binding.profileTextviewNickname.text = ApplicationClass.prefs.userNickName

        binding.profileTextviewMenu4.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_resultFragment)
        }

        binding.profileTextviewMenu1.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_pureNoticeFragment)
            testType = 1
        }
        binding.profileTextviewMenu2.setOnClickListener {
            //dataList는 전역 변수, 해당 코드는 데이터리스트가 비어있으면 순음 검사를 진행하지 않았다고 판단 -> 수정 필요!

            if(dataList.isEmpty()){
                val intent = Intent(context, WarningActivity::class.java)
                startActivity(intent)}
                else{
                findNavController().navigate(R.id.action_ProfileFragment_to_speechNoticeFragment)
                testType = 2
                }
            }
        }
    }
