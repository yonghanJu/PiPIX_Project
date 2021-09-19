package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.databinding.FragmentResultBinding
import com.pipi.pipix.databinding.FragmentWelcomeBinding

class ResultFragment  : BaseFragment<FragmentResultBinding>(
    FragmentResultBinding::bind, R.layout.fragment_result) {

    companion object{
        lateinit var mUserViewModel : PRViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        val recyclerView = binding.resultRecyclerviewResult
        recyclerView.setLayoutManager(object : LinearLayoutManager(context){
            override fun canScrollVertically(): Boolean {
                return true
            }
        })

        // 리사이클러뷰에 Adapter 객체 지정.
        var RecyclerviewAdapter = RecyclerviewAdapter(this)
        recyclerView.adapter = RecyclerviewAdapter

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(PRViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            RecyclerviewAdapter.setData(user)
        })
    }
}