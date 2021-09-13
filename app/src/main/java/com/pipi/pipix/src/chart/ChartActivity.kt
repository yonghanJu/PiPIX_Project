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

        binding.chartButtonBack.setOnClickListener {
            finish()
        }



        val data = intent.getSerializableExtra("test") as PureResult



        Log.d("test",data.toString())
        var lineChart: LineChart? = null

        lineChart = binding.chart

        lineChart.setPinchZoom(false) // 두손가락으로 줌 설정
        lineChart.isDragEnabled = false
        lineChart.setTouchEnabled(false) // 차트 터치 막기


        val entries = ArrayList<Entry>()
        val entries2 = ArrayList<Entry>()


        //그래프에 들어갈 좌표값 입력

        //왼쪽 데이터 리스트
        if(data.L_8000 != null)
        entries.add(Entry(1f, data.L_8000!!.toFloat()))
        if(data.L_4000 != null)
        entries.add(Entry(2f, data.L_4000!!.toFloat()))
        if(data.L_2000 != null)
        entries.add(Entry(3f, data.L_2000!!.toFloat()))
        if(data.L_1000 != null)
        entries.add(Entry(4f, data.L_1000!!.toFloat()))
        if(data.L_500 != null)
        entries.add(Entry(5f, data.L_500!!.toFloat()))
        if(data.L_250 != null)
        entries.add(Entry(6f, data.L_250!!.toFloat()))



        //오른쪽 데이터 리스트
        if(data.R_8000 != null)
        entries2.add(Entry(1f, data.R_8000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        if(data.R_4000 != null)
        entries2.add(Entry(2f, data.R_4000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        if(data.R_2000 != null)
        entries2.add(Entry(3f, data.R_2000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        if(data.R_1000 != null)
        entries2.add(Entry(4f, data.R_1000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        if(data.R_500!= null)
        entries2.add(Entry(5f, data.R_500!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        if(data.R_250 != null)
        entries2.add(Entry(6f, data.R_250!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        //entry add는 if문 사용 null check


        //디자인 부분

        val chartData = LineData()
        val set1 = LineDataSet(entries, " : 왼쪽 귀")
        chartData.addDataSet(set1)
        set1.setColor(Color.RED)
        set1.lineWidth = 1F
        set1.circleRadius = 7F
        set1.setDrawCircleHole(true)
        set1.circleHoleRadius = 4F
        set1.setCircleColor(Color.RED)
        set1.valueTextSize = 15F
        set1.valueTextColor = Color.RED





        val set2 = LineDataSet(entries2, " : 오른쪽 귀")
        chartData.addDataSet(set2)
        set2.setColor(Color.BLUE)
        set2.lineWidth = 1F
        set2.circleSize = 0f
        set2.setDrawCircles(false)
        set2.setDrawCircleHole(false)
        set2.valueTextSize = 15F
        set2.valueTextColor = Color.BLUE
        set2.setDrawIcons(set2.isDrawIconsEnabled())



        //Y축 레이블을 모두 나타내기 위한 틀
        val entries3 = ArrayList<Entry>()

        entries3.add(Entry(1f, -10f))
        entries3.add(Entry(1f, 0f))
        entries3.add(Entry(1f, 10f))
        entries3.add(Entry(1f, 20f))
        entries3.add(Entry(1f, 30f))
        entries3.add(Entry(1f, 40f))
        entries3.add(Entry(1f, 50f))
        entries3.add(Entry(1f, 60f))
        entries3.add(Entry(1f, 70f))
        entries3.add(Entry(1f, 80f))
        entries3.add(Entry(1f, 90f))
        entries3.add(Entry(1f, 100f))

        val set3 = LineDataSet(entries3,"test")
        chartData.addDataSet(set3)
        set3.setColor(ContextCompat.getColor(this, R.color.transparency))
        set3.setDrawCircles(false)
        set3.setDrawCircleHole(false)
        set3.valueTextColor = ContextCompat.getColor(this, R.color.transparency)



        lineChart.data = chartData


        lineChart.setDrawBorders(true)


        // Description set
        lineChart.setDescription(null) //차트에서 Description 설정 저는 따로 안했습니다.


        // Legend set
        lineChart.getLegend().setEnabled(false)
        //var legend = lineChart.legend
        //legend.setTextColor(Color.BLACK)
        //legend.setTextSize(15F)
        //legend.xEntrySpace = 20F
        //legend.orientation = Legend.LegendOrientation.VERTICAL
        //legend.setForm(Legend.LegendForm.CIRCLE)

        // Axis set
        val xAxis = lineChart.xAxis
        xAxis.setLabelCount(6, true)
        xAxis.setDrawGridLines(true) // 격자

        val yAxisRight =lineChart.axisRight
        val yAxisLeft = lineChart.axisLeft
        yAxisRight.isInverted = true
        yAxisLeft.isInverted = true


        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.setDrawGridLines(false)

        yAxisRight.setDrawLabels(true)
        //yAxisRight.setDrawGridLines(true) // 격자
        yAxisRight.axisMaximum = 109f
        yAxisRight.axisMinimum = -19f // 최소값 0
        //yAxisRight.granularity = 10f // 10 단위마다 선을 그리려고 granularity 설정 해 주었다.
        yAxisRight.setLabelCount(12)






        xAxis.valueFormatter = MyXAxisFormatter()
        yAxisRight.valueFormatter = MyYAxisFormatter()




        lineChart.animateX(1000) //X축 애니메이션
        //lineChart.animateY(1000) //Y축 애니메이션

        lineChart.invalidate()



    }

    inner class MyXAxisFormatter : ValueFormatter() {
        private val Hz = arrayOf("250", "500","1000","2000", "4000", "8000")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return Hz.getOrNull(value.toInt() - 1) ?:value.toInt().toString()
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