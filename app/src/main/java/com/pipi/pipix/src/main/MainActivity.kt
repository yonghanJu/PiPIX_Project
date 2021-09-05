package com.pipi.pipix.src.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.Theme_AppCompat_Light_DarkActionBar) 스플래시를 테마로 설정했을 경우 테마를 원래 상태로 바꿀때 사용
        super.onCreate(savedInstanceState)



        //JetPack navigation
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,navController)

        //ScreenSet
        setScreen()
    }

    private fun setScreen(){
        //Set FullScreen, No actionbar
        supportActionBar?.hide()
    }
}