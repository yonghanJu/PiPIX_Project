package com.pipi.pipix.src.chart

import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pipi.pipix.config.BaseActivity
import com.pipi.pipix.databinding.ActivityChartBinding
import com.pipi.pipix.databinding.ActivityMainBinding

class ChartActivity  : BaseActivity<ActivityChartBinding>(ActivityChartBinding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //chart test code

        var lineChart: LineChart? = null

        lineChart = binding.chart
        val entries = ArrayList<Entry>()
        val entries2 = ArrayList<Entry>()

        //그래프에 들어갈 좌표값 입력
        entries.add(Entry(1f, 2f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 2f))
        entries.add(Entry(4f, 2f))
        entries.add(Entry(5f, 2f))




        entries2.add(Entry(3f, 1f))
        entries2.add(Entry(3f, 2f))
        entries2.add(Entry(3f, 3f))
        entries2.add(Entry(3f, 4f))
        entries2.add(Entry(3f, 5f))
        //entry add는 알아서 반복문을 넣든 각자 코드에 맞게 응용하시면 됨.


        //entry add는 알아서 반복문을 넣든 각자 코드에 맞게 응용하시면 됨.
        val chartData = LineData()
        val set1 = LineDataSet(entries, "라벨명1")
        chartData.addDataSet(set1)


        val set2 = LineDataSet(entries2, "라벨명2")
        chartData.addDataSet(set2)
        lineChart.data = chartData


        lineChart.invalidate()

    }
    }