package com.pipi.pipix.src.main.Fragment

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
import com.pipi.pipix.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {

    private  lateinit var mUserViewModel : PRViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultList = arrayListOf("2021.09.05 오전 12시 39분","2021.09.05 오전 12시 39분","2021.09.05 오전 12시 39분",
            "2021.09.05 오전 12시 39분","2021.09.05 오전 12시 39분","2021.09.05 오전 12시 39분")



        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        val recyclerView = binding.profileRecyclerviewResult
        recyclerView.setLayoutManager(object : LinearLayoutManager(context){
            override fun canScrollVertically(): Boolean {
                return true
            }
        })

        // 리사이클러뷰에 Adapter 객체 지정.
        //var profileRecyclerviewAdapter = ProfileRecyclerviewAdapter(this, resultList)
        var profileRecyclerviewAdapter2 = ProfileRecyclerviewAdapter2(this)
        recyclerView.adapter = profileRecyclerviewAdapter2

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(PRViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            profileRecyclerviewAdapter2.setData(user)
        })

        val testPureData = PureResult(0,2021,9,10,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0)


        mUserViewModel.addPureResult(testPureData)



        binding.profileTextviewNickname.text = ApplicationClass.prefs.userNickName

        binding.profileButtonTest1.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_prepareFragment)
        }
    }
}