package com.changs.today_house_test_android.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.pipi.pipix.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) //배경이 투명해지지 않도록
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }
}