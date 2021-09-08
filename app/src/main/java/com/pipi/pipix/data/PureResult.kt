package com.pipi.pipix.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pure_result_table")
data class PureResult (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tpa: Int,
    val date: String,

    val R_250: Int?,
    val R_500: Int?,
    val R_750: Int?,
    val R_1000: Int?,
    val R_1500: Int?,
    val R_2000: Int?,
    val R_3000: Int?,
    val R_4000: Int?,
    val R_6000: Int?,
    val R_8000: Int?,

    val L_250: Int?,
    val L_500: Int?,
    val L_750: Int?,
    val L_1000: Int?,
    val L_1500: Int?,
    val L_2000: Int?,
    val L_3000: Int?,
    val L_4000: Int?,
    val L_6000: Int?,
    val L_8000: Int?
)