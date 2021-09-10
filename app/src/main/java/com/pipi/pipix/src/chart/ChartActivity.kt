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
import com.pipi.pipix.databinding.ActivityMainBinding
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
        entries.add(Entry(1f, data.L_250!!.toFloat()))
        entries.add(Entry(2f, data.L_500!!.toFloat()))
        entries.add(Entry(3f, data.L_750!!.toFloat()))
        entries.add(Entry(4f, data.L_1000!!.toFloat()))
      //  entries.add(Entry(5f, testData.L_1500!!.toFloat()))
        entries.add(Entry(6f, data.L_2000!!.toFloat()))
        entries.add(Entry(7f, data.L_3000!!.toFloat()))
        entries.add(Entry(8f, data.L_4000!!.toFloat()))
       // entries.add(Entry(9f, testData.L_6000!!.toFloat()))
        entries.add(Entry(10f, data.L_8000!!.toFloat()))



        //오른쪽 데이터 리스트
        entries2.add(Entry(1f, testData.R_250!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        entries2.add(Entry(2f, testData.R_500!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
       // entries2.add(Entry(3f, testData.R_750!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        entries2.add(Entry(4f, testData.R_1000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        entries2.add(Entry(5f, testData.R_1500!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        entries2.add(Entry(6f, testData.R_2000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
       // entries2.add(Entry(7f, testData.R_3000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        entries2.add(Entry(8f, testData.R_4000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        entries2.add(Entry(9f, testData.R_6000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))
        entries2.add(Entry(10f, testData.R_8000!!.toFloat(),ContextCompat.getDrawable(getApplicationContext(),R.drawable.star2)))


        //entry add는 if문 사용 null check




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
        set2.setColor(ContextCompat.getColor(this, R.color.transparency))
        set2.circleSize = 0f
        set2.setDrawCircles(false)
        set2.setDrawCircleHole(false)
        set2.valueTextSize = 15F
        set2.valueTextColor = Color.BLUE
        set2.setDrawIcons(set2.isDrawIconsEnabled())





        lineChart.data = chartData


        lineChart.setDrawBorders(true)


        // Description set
        lineChart.setDescription(null) //차트에서 Description 설정 저는 따로 안했습니다.


        //legend set

        lineChart.getLegend().setEnabled(false)
        //var legend = lineChart.legend
        //legend.setTextColor(Color.BLACK)
        //legend.setTextSize(15F)
        //legend.xEntrySpace = 20F
        //legend.orientation = Legend.LegendOrientation.VERTICAL
        //legend.setForm(Legend.LegendForm.CIRCLE)

        //Axis set
        val xAxis = lineChart.xAxis
        xAxis.setLabelCount(10, true)
        xAxis.setDrawGridLines(false) // 격자

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