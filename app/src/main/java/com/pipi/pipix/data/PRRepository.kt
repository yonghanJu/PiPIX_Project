package com.pipi.pipix.data

import androidx.lifecycle.LiveData

class PRRepository(private val pureResultDao: PureResultDao) {

    val readAllData: LiveData<List<PureResult>> = pureResultDao.readAllData()

    suspend fun deletePureResult(pr: PureResult){
        pureResultDao.deletePureResult(pr)
    }

    suspend fun addPureResult(pr: PureResult){
        pureResultDao.addPureResult(pr)
    }
}