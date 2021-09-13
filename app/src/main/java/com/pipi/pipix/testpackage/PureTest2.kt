package com.pipi.pipix.testpackage

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.pipi.pipix.R
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.math.roundToInt

class PureTest2(private val btnYes:Button, private val btnNo: Button, var context: Context, var ptViewModel: PureTest2ViewModel) {
    private  var progress: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused = false
    private var lock = false

    private val scope = CoroutineScope(CoroutineName("innerScope"))
    private val resIdList1 = arrayListOf(R.raw.hz1000, R.raw.hz2000, R.raw.hz4000, R.raw.hz8000, R.raw.hz250, R.raw.hz500)
    private val dbList = arrayListOf(1000, 2000, 4000, 8000, 250, 500)
    private val dbMap = mutableMapOf<Int,Float>()
    private var result = mutableListOf(mutableListOf(0,0,0,0,0,0), mutableListOf(0,0,0,0,0,0))
    init {
        for(i in 0..100 step 5) dbMap[i] = (1/10000f)*i + 0.00008f
    }

    fun doTest(direc: Int): Boolean {
        for(position in 0..5){
            if(!isPaused){

                val set = thread{
                    // 초기 설정
                    mediaPlayer = MediaPlayer.create(context, resIdList1[position])
                    mediaPlayer!!.isLooping = true
                    ptViewModel.setHz(dbList[position])
                    ptViewModel.setDirec(direc)
                }
                runBlocking { set.join() }

                val test = thread { testDb(direc, position,mediaPlayer!!) }
                runBlocking { test.join() }

                thread{ for(i in 0..8){
                    sleep(30)
                    ptViewModel.setProgress(++progress)
                }}
                val release = thread { mediaPlayer!!.release() }
                runBlocking { release.join() }
            }else return false
        }
        return true
    }

    private fun testDb(direc: Int, position: Int, mediaPlayer: MediaPlayer) {
        var currentDb = 30
        var dbSet = mutableSetOf<Int>()
        var isFin: Boolean = false
        btnYes.isClickable = true
        btnNo.isClickable = true
        // 버그 수정 필요
        fun play(){
            if(currentDb in 0..100 && mediaPlayer != null){
                mediaPlayer.setVolume((1-direc)*dbMap[currentDb]!!, direc*dbMap[currentDb]!!)
                mediaPlayer.start()
            }
        }

        btnYes.setOnClickListener {
            btnNo.isClickable = false
            when(currentDb){
                in -15..0 ->{result[direc][position] = 0
                    mediaPlayer.stop()
                    isFin = true
                    Log.d("tag","75")
                }
                else ->{
                    currentDb-=10
                    play()
                    Log.d("tag","80")
                }
            }
            btnNo.isClickable = true
        }

        btnNo.setOnClickListener {
            btnYes.isClickable = false
            if(currentDb>=100){
                mediaPlayer.stop()
                result[direc][position] = 100
                isFin = true
                Log.d("tag","91")
            }else if(dbSet.contains(currentDb)){
                mediaPlayer.stop()
                result[direc][position] = currentDb
                isFin = true
                Log.d("tag","96")
            }else{
                dbSet.add(currentDb)
                currentDb+=5
                play()
                Log.d("tag","101")
            }
            btnYes.isClickable = true
        }

        // 첫 실행
        play()

        val job = scope.launch {
            while (!isFin){
                if(!isPaused){}
                else break
            }
        }
        runBlocking { job.join() }
        btnYes.isClickable = false
        btnNo.isClickable = false
    }

    fun pause(){
        isPaused=true
        mediaPlayer?.release()
    }

    fun getResult(): MutableList<MutableList<Int>> {
        return result
    }

    fun getTpa(direc: Int): Int {
        return ((result[direc][0] * 2 + result[direc][1] * 2 + result[direc][2] + result[direc][5]) / 30f).roundToInt() *5
    }
}