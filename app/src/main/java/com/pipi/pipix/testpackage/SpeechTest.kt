package com.pipi.pipix.testpackage

import android.content.Context
import android.media.MediaPlayer
import android.os.SystemClock.sleep
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.pipi.pipix.R
import com.pipi.pipix.data.PRViewModel
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.pow

class SpeechTest(private val tpaRight: Int, private val tpaLeft: Int, private val textCount: TextView, private val speechViewModel: SpeechViewModel, val prViewModel: PRViewModel, val context: Context) {
    private var recordString = ""
    private var isPaused = false
    private var isRecorded = false
    private var isTest1Fin = false
    private var isTest2Fin = false
    private var recordFin = false
    private var currentDb = 0
    private var progress = 0
    private val dbMap = mutableMapOf<Int,Float>()
    private var result = mutableListOf(0,0,0,0) // index 0,1,2,3 -> tpaRight, tpaLeft, scoreRight, scoreLeft


    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var usedWordsSet: MutableSet<Int>
    private lateinit var dbSet: MutableSet<Int>

    private val wordsList = arrayOf("국수","권투","극장","까치","꽃병","눈물","달걀","독약","땅콩","뚜껑","목욕","발톱",
        "방석","빛깔","색칠","석탄","송곳","약국","양복","연필","엽서","욕심","육군",
        "접시","찰떡","찹쌀","책상","콩팥","톱밥","팥죽","폭발","필통","학생","합격")
    private var wordsList2 = arrayOf("갓","강","국","귀","꿀","남","녹","논","닭","답","발","범","불","산","솔,","운","잔","짐","터","통")

    private val wordsIdList = arrayOf(R.raw.w1,R.raw.w2,R.raw.w3,R.raw.w4,R.raw.w5,R.raw.w6,R.raw.w7,R.raw.w8,R.raw.w9,R.raw.w10,
        R.raw.w11,R.raw.w12,R.raw.w13,R.raw.w14,R.raw.w15,R.raw.w16,R.raw.w17,R.raw.w18,R.raw.w19,R.raw.w20,R.raw.w21,R.raw.w22,R.raw.w23,
        R.raw.w24,R.raw.w25,R.raw.w26,R.raw.w27,R.raw.w28,R.raw.w29,R.raw.w30,R.raw.w31,R.raw.w32,R.raw.w33,R.raw.w34)

    private var wordsIdList2 = arrayOf(R.raw.c1,R.raw.c2,R.raw.c3,R.raw.c4,R.raw.c5,R.raw.c6,R.raw.c7,R.raw.c8,R.raw.c9,R.raw.c10,R.raw.c11,
        R.raw.c12,R.raw.c13,R.raw.c14,R.raw.c15,R.raw.c16,R.raw.c17,R.raw.c18,R.raw.c19,R.raw.c20)

    // db 세팅
    init {
        for(i in 0..100 step 5) dbMap[i] = (1.0/ (10.0).pow((100.0-i)/20.0) ).toFloat()
    }



    // 1 -> Right , 0 -> Left
    fun doTest1(direc: Int): Boolean{
        isTest1Fin = false

        // 시작 데시벨 설정
        currentDb = if(direc==1) tpaRight+25 else tpaLeft+25
        usedWordsSet = mutableSetOf()
        dbSet = mutableSetOf()

        // 테스트 실행
        while(!isPaused && !isTest1Fin){

            Log.d("tag", "direc: $direc db : $currentDb")
            when(currentDb){
                in -15..-5 -> {
                    test1Fin(direc, 0)
                    return true
                }
                in 100..110->{
                    test1Fin(direc, 100)
                    return true
                }
            }
            // 랜덤 단어 선택 0~33
            var randomInt = 0
            var rWait = true
            while(!isPaused && !isTest1Fin){
                randomInt = Random().nextInt(34)
                if(!usedWordsSet.contains(randomInt)){
                    usedWordsSet.add(randomInt)
                    break
                }
            }

            // 음원 준비
            mediaPlayer = MediaPlayer.create(context, wordsIdList[randomInt])
            mediaPlayer.setVolume((1-direc)* dbMap[currentDb]!!,direc* dbMap[currentDb]!!)
            isRecorded = false
            recordFin = false

            // 버튼 잠금--------------------카운트 동안 버튼 이미지 변결 필요
            speechViewModel.currentClickable.postValue(false)
            setImageGone()
            setCountVisible()

            if(isPaused) return false
            textCount.text = "3"
            sleep(1000)
            if(isPaused) return false
            textCount.text = "2"
            sleep(1000)
            if(isPaused) return false
            textCount.text = "1"
            sleep(1000)

            setCountGone()
            setImageVisible()
            mediaPlayer.start()

            // 버튼 풀기--------------------카운트 동안 버튼 이미지 변결 필요
            speechViewModel.currentClickable.postValue(true)

            val job = thread {
                while(!recordFin && rWait && !isPaused){}
                if(recordFin) {                                 // 레코딩이 끝났다면

                    if(recordString == wordsList[randomInt]){       // 단어를 맞췄다면
                        currentDb -=10
                        if(currentDb == -5) currentDb = 0

                    }else{                                          // 단어를 틀렸다면
                        if(dbSet.contains(currentDb)){ test1Fin(direc, currentDb) }// 2 번 틀렸다면
                        else {                                          // 처음 틀렸다면
                            dbSet.add(currentDb)
                            currentDb +=5
                        }
                    }
                }else{                                          // 3초간 반응이 없다면
                    if(dbSet.contains(currentDb)){ test1Fin(direc, currentDb) }// 2 번 틀렸다면
                    else {                                          // 처음 틀렸다면
                        dbSet.add(currentDb)
                        currentDb +=5
                    }
                }
            }

            thread { // 3초 카운팅
                sleep(3000)
                if(!isRecorded){
                    speechViewModel.currentClickable.postValue(false)
                    rWait = false
                }
            }

            runBlocking { job.join() }

        } // end of while(!isPaused)

        // 테스트 성공 여부 true or false 반환
        if(isPaused) return false
        thread{ for(i in 0..29){
            Thread.sleep(30)
            speechViewModel.currentProgress.postValue(++progress)
        }}
        return true
    }

    // 테스트 2(명료도)
    fun doTest2(direc: Int): Boolean{
        isTest2Fin = false
        // 시작 데시벨 설정
        currentDb = if(direc==1) result[0]+35 else result[1] +35

        val test2Item = mutableListOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19).shuffled() as MutableList<Int>
        var currentScore = 0

        var count = 0
        for(i in test2Item){
            if(count>9) break
            else count++
            var rWait = true
            if(isPaused) return false
            // 음원 준비
            mediaPlayer = MediaPlayer.create(context, wordsIdList2[i])
            mediaPlayer.setVolume((1-direc)* dbMap[currentDb]!!,direc* dbMap[currentDb]!!)
            isRecorded = false
            recordFin = false

            // 버튼 잠금--------------------카운트 동안 버튼 이미지 변결 필요
            speechViewModel.currentClickable.postValue(false)
            setImageGone()
            setCountVisible()

            if(isPaused) return false
            textCount.text = "3"
            sleep(1000)
            if(isPaused) return false
            textCount.text = "2"
            sleep(1000)
            if(isPaused) return false
            textCount.text = "1"
            sleep(1000)

            setCountGone()
            setImageVisible()
            mediaPlayer.start()

            // 버튼 풀기--------------------카운트 동안 버튼 이미지 변결 필요
            speechViewModel.currentClickable.postValue(true)

            val job = thread {
                while(!recordFin && rWait && !isPaused){}
                if(recordFin && recordString == wordsList2[i]){
                    currentScore++
                }
            }

            thread { // 3초 카운팅
                sleep(3000)
                if(!isRecorded){ // 녹음 시작이 안됐을 때
                    speechViewModel.currentClickable.postValue(false)
                    rWait = false
                }
            }

            runBlocking { job.join() }
            thread{
                speechViewModel.currentProgress.postValue(++progress)
                Thread.sleep(30)
                speechViewModel.currentProgress.postValue(++progress)
            }
        } // end of for Loop

        test2Fin(direc, currentScore)

        // 테스트 성공 여부 true or false 반환
        if(isPaused) return false
        return true
    }

    fun pause(){
        isPaused = true
    }

    private fun test1Fin(direc: Int, db: Int){
        isTest1Fin = true
        if(direc==1) result[0] = db
        else result[1] = db
    }

    private fun test2Fin(direc: Int,score: Int){
        isTest2Fin = true
        if(direc==1) result[2] = score
        else result[3] = score
    }

    fun getResult(): MutableList<Int> {
        return result
    }

    fun recordText(string: String){
        recordString = string
        recordFin = true
    }

    fun recordStart(){
        isRecorded = true
    }

    private fun setImageVisible(){
        speechViewModel.currentImageVisible.postValue(View.VISIBLE)
    }

    private fun setImageGone(){
        speechViewModel.currentImageVisible.postValue(View.GONE)
    }

    private fun setCountVisible(){
        speechViewModel.currentCountVisible.postValue(View.VISIBLE)
    }

    private fun setCountGone(){
        speechViewModel.currentCountVisible.postValue(View.GONE)
    }
}