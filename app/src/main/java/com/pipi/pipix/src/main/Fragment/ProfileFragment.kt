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
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {

    private  lateinit var mUserViewModel : PRViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        val recyclerView = binding.profileRecyclerviewResult
        recyclerView.setLayoutManager(object : LinearLayoutManager(context){
            override fun canScrollVertically(): Boolean {
                return true
            }
        })

        // 리사이클러뷰에 Adapter 객체 지정.
        var profileRecyclerviewAdapter = ProfileRecyclerviewAdapter(this)
        recyclerView.adapter = profileRecyclerviewAdapter

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(PRViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            profileRecyclerviewAdapter.setData(user)
        })

        //test data
        val now = System.currentTimeMillis()
        val date =  Date(now)
        val sdf =  SimpleDateFormat("yyyy.MM.dd HH시 mm분")
        val getTime = sdf.format(date)

        val testPureData = PureResult(0,0,getTime,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0)


        mUserViewModel.addPureResult(testPureData)



        binding.profileTextviewNickname.text = ApplicationClass.prefs.userNickName

        binding.profileButtonTest1.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_prepareFragment)
        }
    }
}