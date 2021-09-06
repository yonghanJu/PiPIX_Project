package com.pipi.pipix.data

import androidx.lifecycle.LiveData

class PRRepository(private val pureResultDao: PureResultDao) {

    val readAllData: LiveData<List<PureResult>> = pureResultDao.readAllData()

    suspend fun addTestData(pr: PureResult){
        pureResultDao.addPureResult(pr)
    }
}