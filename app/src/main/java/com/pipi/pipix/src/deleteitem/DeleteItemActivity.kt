package com.pipi.pipix.src.deleteitem

import android.content.Intent
import android.os.Bundle
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivityDeleteitemBinding
import com.pipi.pipix.src.main.fragment.ProfileFragment
import com.pipi.pipix.src.main.fragment.ProfileFragment.Companion.dataList
import com.pipi.pipix.src.main.fragment.ResultFragment.Companion.mUserViewModel

class DeleteItemActivity : BaseActivity<ActivityDeleteitemBinding>(ActivityDeleteitemBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.deleteitemImageviewBtnBack.setOnClickListener {
            finish()
        }

        var position = intent.getIntExtra("delete",999)

        binding.deleteitemButtonCheck.setOnClickListener {
            mUserViewModel.deletePureResult(dataList[position])
            finish()
        }
    }
}