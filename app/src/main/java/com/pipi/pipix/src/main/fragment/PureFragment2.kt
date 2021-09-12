package com.pipi.pipix.src.main.fragment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseFragment
import com.pipi.pipix.data.PRViewModel
import com.pipi.pipix.data.PureResult
import com.pipi.pipix.databinding.FragmentPure2Binding
import com.pipi.pipix.testpackage.PureTest2
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class PureFragment2  : BaseFragment<FragmentPure2Binding>(FragmentPure2Binding::bind, R.layout.fragment_pure2) {

    private lateinit var result: MutableList<MutableList<Int>>
    private lateinit var viewModel: PRViewModel
    private lateinit var pureTest: PureTest2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.pure2Animation.loop(true)

        pureTest = PureTest2(binding.pure2ButtonYes,binding.pure2ButtonNo,requireContext())
        viewModel = ViewModelProvider(this).get(PRViewModel::class.java)

        val scope = CoroutineScope(CoroutineName("scope"))
        val testJob = scope.launch {
            if(pureTest.doTest(1) && pureTest.doTest(0)) {
                result = pureTest.getResult()
                val now = System.currentTimeMillis()
                val date =  Date(now)
                val sdf =  SimpleDateFormat("yyyy.MM.dd a hh시 mm분")
                val pr = PureResult(0,1,pureTest.getTpa(1),pureTest.getTpa(0),sdf.format(date)
                    ,null,null, result[1][4], result[1][5], result[1][0],result[1][1],result[1][2],result[1][3]
                    , result[0][4], result[0][5], result[0][0],result[0][1],result[0][2],result[0][3])
                viewModel.addPureResult(pr)
                activity?.runOnUiThread { findNavController().navigate(R.id.action_pureFragment2_to_ProfileFragment) }
            }
        }

        // 코루틴으로 시작
        testJob.start()

        binding.pure2ButtonPause.setOnClickListener {
            pureTest.pause()
            findNavController().navigate(R.id.action_pureFragment2_to_ProfileFragment)
            Toast.makeText(context,"순음청력검사가 취소 되었습니다.",Toast.LENGTH_LONG).show()
        }

        // backButton Pressed handle
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            pureTest.pause()
            findNavController().navigate(R.id.action_pureFragment2_to_ProfileFragment)
            Toast.makeText(context,"순음청력검사가 취소 되었습니다.",Toast.LENGTH_LONG).show()
        }

    }

    override fun onPause() {
        pureTest.pause()
        super.onPause()
    }
}
