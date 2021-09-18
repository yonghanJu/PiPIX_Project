package com.pipi.pipix.src.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivityMainBinding
import com.pipi.pipix.src.main.SoundController.init

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)  {
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestAudioPermission()

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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // 요청한 권한에 대한 결과

        val audioRecordPermissionGranted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                    grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (!audioRecordPermissionGranted) {
            finish() // 거절 할 경우 앱 종료
        }
    }


    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }
    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }



}