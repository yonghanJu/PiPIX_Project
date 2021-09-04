package com.pipi.pipix.data

import androidx.lifecycle.LiveData

class TDRepository(private val testDataDao: TestDataDao) {

    val readAllData: LiveData<List<TestData>> = testDataDao.readAllData()

    suspend fun addTestData(td: TestData){
        testDataDao.addTestData(td)
    }
}