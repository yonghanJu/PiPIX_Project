package com.pipi.pipix.config

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm="mPref"
    private val prefs=context.getSharedPreferences(prefNm,MODE_PRIVATE)

    var userNickName : String?
        get() = prefs.getString("userNickName",null)
        set(value){
            prefs.edit().putString("userNickName",value).apply()
        }

}