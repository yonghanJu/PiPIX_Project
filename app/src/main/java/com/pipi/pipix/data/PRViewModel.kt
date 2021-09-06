package com.pipi.pipix.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PRViewModel(applications: Application): AndroidViewModel(applications) {

    val readAllData: LiveData<List<PureResult>>
    private val repository: PRRepository

    init{
        val pureResultDao = PRDatabase.getDatabase(applications).pureResultDao()
        repository = PRRepository(pureResultDao)
        readAllData = repository.readAllData
    }

    fun deletePureResult(pr: PureResult){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePureResult(pr)
        }
    }

    fun addPureResult(pr: PureResult){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPureResult(pr)
        }
    }
}