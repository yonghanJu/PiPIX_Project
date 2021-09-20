package com.pipi.pipix.src.main.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.databinding.FragmentResultBinding
import com.pipi.pipix.databinding.FragmentWelcomeBinding

class ResultFragment  : BaseFragment<FragmentResultBinding>(
    FragmentResultBinding::bind, R.layout.fragment_result) {
companion object{
    var recyclerviewAdapter = RecyclerviewAdapter()
}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resultImageviewBtnBack.setOnClickListener {
            //뒤로가기
            findNavController().popBackStack()
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        val recyclerView = binding.resultRecyclerviewResult
        recyclerView.setLayoutManager(object : LinearLayoutManager(context){
            override fun canScrollVertically(): Boolean {
                return true
            }
        })

        // UserViewModel
        ProfileFragment.mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            recyclerviewAdapter.setData(user)
        })
        recyclerView.adapter = recyclerviewAdapter




    }
}