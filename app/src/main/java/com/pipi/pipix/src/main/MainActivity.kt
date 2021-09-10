package com.pipi.pipix.src.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivityMainBinding
import com.pipi.pipix.src.main.SoundController.init

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SoundController init
        init(applicationContext)

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