package com.pipi.pipix.data

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PureViewModel:ViewModel() {
    
    var imageVisible = View.VISIBLE
    var countVisible =  View.GONE

    val currentCountVisible: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }

    val currentImageVisible: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }
}