package com.pipi.pipix.src.chart

import android.graphics.Color
import android.os.Bundle
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
import com.pipi.pipix.databinding.ActivityMainBinding
class ChartActivity  : BaseActivity<ActivityChartBinding>(ActivityChartBinding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setScreen()

        binding.chartButtonBack.setOnClickListener {
            finish()
        }


        //val data = intent.getSerializableExtra("data") as PureResult 리사이클러뷰에서 데이터 받아오는 방법 찾아야함

        var lineChart: LineChart? = null

        lineChart = binding.chart

        lineChart.setPinchZoom(false) // 두손가락으로 줌 설정
        lineChart.isDragEnabled = false
        lineChart.setTouchEnabled(false) // 차트 터치 막기



        //예상 데이터 형식 PureResult
        val testData = PureResult(0,1,"test",100,20,30,20,80,25,40,35,10,0,55
            ,25,25,60,55,20,15,10,0,5)

        val entries = ArrayList<Entry>()
        val entries2 = ArrayList<Entry>()


        //그래프에 들어갈 좌표값 입력

        //왼쪽 데이터 리스트
        entries.add(Entry(1f, testData.L_250!!.toFloat()))
        entries.add(Entry(2f, testData.L_500!!.toFloat()))
        entries.add(Entry(3f, testData.L_750!!.toFloat()))
        entries.add(Entry(4f, testData.L_1000!!.toFloat()))
        entries.add(Entry(5f, testData.L_1500!!.toFloat()))
        entries.add(Entry(6f, testData.L_2000!!.toFloat()))
        entries.add(Entry(7f, testData.L_3000!!.toFloat()))
        entries.add(Entry(8f, testData.L_4000!!.toFloat()))
        entries.add(Entry(9f, testData.L_6000!!.toFloat()))
        entries.add(Entry(10f, testData.L_8000!!.toFloat()))



        //오른쪽 데이터 리스트트
        entries2.add(Entry(1f, testData.R_250!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(2f, testData.R_500!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(3f, testData.R_750!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(4f, testData.R_1000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(5f, testData.R_1500!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(6f, testData.R_2000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(7f, testData.R_3000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(8f, testData.R_4000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(9f, testData.R_6000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))
        entries2.add(Entry(10f, testData.R_8000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star)))


        //entry add는 알아서 반복문을 넣든 함수 활용하든 각자 코드에 맞게 응용하시면 됨.




        val chartData = LineData()
        val set1 = LineDataSet(entries, " : 왼쪽 귀")
        chartData.addDataSet(set1)
        set1.setColor(Color.RED)
        set1.lineWidth = 1F
        set1.circleRadius = 10F
        set1.setDrawCircleHole(true)
        set1.circleHoleRadius = 6F
        set1.setCircleColor(Color.RED)
        set1.valueTextSize = 15F
        set1.valueTextColor = Color.RED






        val set2 = LineDataSet(entries2, " : 오른쪽 귀")
        chartData.addDataSet(set2)
        set2.setColor(Color.BLUE)
        set2.lineWidth = 1F
        set2.circleSize = 0f
        set2.setDrawCircleHole(false)
        set2.setCircleColor(Color.BLUE)
        set2.valueTextSize = 15F
        set2.valueTextColor = Color.BLUE
        set2.setDrawIcons(set2.isDrawIconsEnabled())


        lineChart.data = chartData


        lineChart.setDrawBorders(true)


        // Description set
        lineChart.setDescription(null) //차트에서 Description 설정 저는 따로 안했습니다.


        //legend set
        var legend = lineChart.legend
        legend.setTextColor(Color.BLACK)
        legend.setTextSize(15F)
        legend.xEntrySpace = 20F
        //legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setForm(Legend.LegendForm.CIRCLE)

        //Axis set
        val xAxis = lineChart.xAxis
        xAxis.setLabelCount(10, true)
        xAxis.setDrawGridLines(true) // 격자

        val yAxisRight =lineChart.axisRight
        val yAxisLeft = lineChart.axisLeft


        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.setDrawGridLines(false)

        yAxisRight.setDrawGridLines(true) // 격자
        //yAxisRight.axisMaximum = 100f
        //yAxisRight.axisMinimum = 0f // 최소값 0
        yAxisRight.granularity = 10f // 10 단위마다 선을 그리려고 granularity 설정 해 주었다.
        //yAxisRight.setLabelCount(10)


        xAxis.valueFormatter = MyXAxisFormatter()
        yAxisRight.valueFormatter = MyYAxisFormatter()


        lineChart.invalidate()


    }

    inner class MyXAxisFormatter : ValueFormatter() {
        private val Hz = arrayOf("250", "500", "750", "1000","1500", "2000","3000", "4000","6000", "8000")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return Hz.getOrNull(value.toInt() - 1) ?: Hz.toString()
        }
    }

    inner class MyYAxisFormatter : ValueFormatter() {
        private val dB = arrayOf("0", "10", "20", "30", "40", "50", "60", "70","80","90","100")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return dB.getOrNull(value.toInt() - 1) ?: value.toInt().toString() + "dB"
        }

    }

    private fun setScreen(){
        //Set FullScreen, No actionbar
        supportActionBar?.hide()
    }





}