package com.pipi.pipix.testpackage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PureTest2ViewModel:ViewModel() {
    val hzText: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    val direcText: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    val progress: MutableLiveData<Int>by lazy{
        MutableLiveData<Int>()
    }

    fun setHz(hz: Int){
        hzText.postValue("${hz}hz")
    }

    fun setDirec(direc: Int){
        if(direc==1) direcText.postValue("오른쪽") else  direcText.postValue("왼쪽")
    }

    fun setProgress(prog: Int){
        progress.postValue(prog)
    }
}