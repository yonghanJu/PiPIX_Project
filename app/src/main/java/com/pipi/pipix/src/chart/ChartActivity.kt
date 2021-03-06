package com.pipi.pipix.src.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.pipi.pipix.R
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.data.PureResult
import com.pipi.pipix.databinding.ActivityChartBinding
import java.security.AccessController.getContext

class ChartActivity  : BaseActivity<ActivityChartBinding>(ActivityChartBinding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setScreen()

        binding.chartImageviewBtnBack.setOnClickListener {
            finish()
        }


        val data = intent.getSerializableExtra("test") as PureResult

        val pta = (data.tpaLeft + data.tpaRight) / 2
        Log.d("test",pta.toString())

        var image1 = binding.chartImageviewAngry
        var image2 = binding.chartImageviewSad
        var image3 = binding.chartImageviewNeutral
        var image4 = binding.chartImageviewSmile

        var text1 = binding.chartTextviewAngry
        var text2 = binding.chartTextviewSad
        var text3 = binding.chartTextviewNeutural
        var text4 = binding.chartTextviewSmile
        when(pta){
            in 0..10 -> {  image4.setImageResource(R.drawable.smile2)
                text4.setTextColor(getColor(R.color.colorAccent))
                text4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_lens_24_2, 0, 0)}
            in 10..30 -> {image3.setImageResource(R.drawable.neutral2)
                text3.setTextColor(getColor(R.color.colorAccent))
                text3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_lens_24_2, 0, 0)}
            in 30..50 -> {image2.setImageResource(R.drawable.sad2)
                text2.setTextColor(getColor(R.color.colorAccent))
                text2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_lens_24_2, 0, 0)}
            else -> {image1.setImageResource(R.drawable.angry2)
                text1.setTextColor(getColor(R.color.colorAccent))
                text1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_lens_24_2, 0, 0)}

        }



        Log.d("test",data.toString())
        var lineChart: LineChart? = null

        lineChart = binding.chart

        lineChart.setPinchZoom(false) // ?????????????????? ??? ??????
        lineChart.isDragEnabled = false
        lineChart.setTouchEnabled(false) // ?????? ?????? ??????


        val entries = ArrayList<Entry>()
        val entries2 = ArrayList<Entry>()


        //???????????? ????????? ????????? ??????

        //?????? ????????? ?????????
        if(data.R_250 != null)
            entries.add(Entry(1f, data.R_250!!.toFloat()))
        if(data.R_500 != null)
            entries.add(Entry(2f, data.R_500!!.toFloat()))
        if(data.R_1000 != null)
            entries.add(Entry(3f, data.R_1000!!.toFloat()))
        if(data.R_2000 != null)
            entries.add(Entry(4f, data.R_2000!!.toFloat()))
        if(data.R_4000 != null)
            entries.add(Entry(5f, data.R_4000!!.toFloat()))
        if(data.R_8000 != null)
        entries.add(Entry(6f, data.R_8000!!.toFloat()))





        //????????? ????????? ?????????
        if(data.L_250 != null)
            entries2.add(Entry(1f, data.L_250!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconx2)))
        if(data.L_500!= null)
            entries2.add(Entry(2f, data.L_500!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconx2)))
        if(data.L_1000 != null)
            entries2.add(Entry(3f, data.L_1000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconx2)))
        if(data.L_2000 != null)
            entries2.add(Entry(4f, data.L_2000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconx2)))
        if(data.L_4000 != null)
            entries2.add(Entry(5f, data.L_4000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconx2)))
        if(data.L_8000 != null)
        entries2.add(Entry(6f, data.L_8000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconx2)))








        //????????? ??????

        val chartData = LineData()
        val set1 = LineDataSet(entries, " : ?????? ???")
        chartData.addDataSet(set1)
        set1.setColor(Color.RED)
        set1.lineWidth = 1F
        set1.circleRadius = 10F
        set1.setDrawCircleHole(true)
        set1.circleHoleRadius = 7F
        set1.setCircleColor(Color.RED)
        set1.setDrawValues(false)





        val set2 = LineDataSet(entries2, " : ????????? ???")
        chartData.addDataSet(set2)
        set2.setColor(Color.BLUE)
        set2.setDrawValues(false)
        set2.lineWidth = 1F
        set2.setDrawCircles(false)
        set2.setDrawCircleHole(false)
        set2.setDrawIcons(set2.isDrawIconsEnabled())



        //Y??? ???????????? ?????? ???????????? ?????? ???
        val entries3 = ArrayList<Entry>()

        entries3.add(Entry(0f, -10f))
        entries3.add(Entry(0f, 0f))
        entries3.add(Entry(0f, 10f))
        entries3.add(Entry(0f, 20f))
        entries3.add(Entry(0f, 30f))
        entries3.add(Entry(0f, 40f))
        entries3.add(Entry(7f, 50f))
        entries3.add(Entry(7f, 60f))
        entries3.add(Entry(7f, 70f))
        entries3.add(Entry(7f, 80f))
        entries3.add(Entry(7f, 90f))
        entries3.add(Entry(7f, 100f))

        val set3 = LineDataSet(entries3,"test")
        chartData.addDataSet(set3)
        set3.setColor(ContextCompat.getColor(this, R.color.transparency))
        set3.setDrawCircles(false)
        set3.setDrawCircleHole(false)
        set3.valueTextColor = ContextCompat.getColor(this, R.color.transparency)

        lineChart.data = chartData


        lineChart.setDrawBorders(true)


        // Description set
        lineChart.setDescription(null) //???????????? Description ?????? ?????? ?????? ???????????????.


        // Legend set
        lineChart.getLegend().setEnabled(false)


        // Axis set
        val xAxis = lineChart.xAxis
        xAxis.setLabelCount(6)
        xAxis.mAxisMaximum = 6.9f
        xAxis.mAxisMinimum = 0.9f

        xAxis.setDrawGridLines(true) // ??????

        val yAxisRight =lineChart.axisRight
        val yAxisLeft = lineChart.axisLeft
        yAxisRight.isInverted = true
        yAxisLeft.isInverted = true


        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.setDrawGridLines(false)

        yAxisRight.setDrawLabels(true)
        //yAxisRight.setDrawGridLines(true) // ??????
        yAxisRight.axisMaximum = 109f
        yAxisRight.axisMinimum = -19f //
        //yAxisRight.granularity = 10f // 10 ???????????? ?????? ???????????? granularity ?????? ??? ?????????.
        yAxisRight.setLabelCount(12)






        xAxis.valueFormatter = MyXAxisFormatter()
        yAxisRight.valueFormatter = MyYAxisFormatter()




        lineChart.animateX(1000) //X??? ???????????????
        //lineChart.animateY(1000) //Y??? ???????????????

        lineChart.invalidate()



    }

    inner class MyXAxisFormatter : ValueFormatter() {
        private val Hz = arrayOf("250", "500","1000","2000", "4000", "8000")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return Hz.getOrNull(value.toInt() - 1) ?:""
        }
    }

    inner class MyYAxisFormatter : ValueFormatter() {
        private val dB = arrayOf("100dB","90dB","80dB","70dB","60dB","50dB","40dB","30dB","20dB","10dB","0dB","-10dB")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return dB.getOrNull(value.toInt() - 1) ?: value.toInt().toString() + "dB"
        }

    }

    private fun setScreen(){
        //Set FullScreen, No actionbar
        supportActionBar?.hide()
    }





}