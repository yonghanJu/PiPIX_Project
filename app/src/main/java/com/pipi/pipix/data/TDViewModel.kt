package com.pipi.pipix.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TDViewModel(applications: Application): AndroidViewModel(applications) {

    val readAllData: LiveData<List<TestData>>
    private val repository: TDRepository

    init{
        val testDataDao = TDDatabase.getDatabase(applications).testDataDao()
        repository = TDRepository(testDataDao)
        readAllData = repository.readAllData
    }

    fun addTestData(td: TestData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTestData(td)
        }
    }
}