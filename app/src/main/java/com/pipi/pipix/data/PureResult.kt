package com.pipi.pipix.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pure_result_table")
data class PureResult (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tpaRight: Int,
    val tpaLeft: Int,
    val date: String,

    val R_250: Int? = null,
    val R_500: Int? = null,
    val R_750: Int? = null,
    val R_1000: Int? = null,
    val R_1500: Int? = null,
    val R_2000: Int? = null,
    val R_3000: Int? = null,
    val R_4000: Int? = null,
    val R_6000: Int? = null,
    val R_8000: Int? = null,

    val L_250: Int? = null,
    val L_500: Int? = null,
    val L_750: Int? = null,
    val L_1000: Int? = null,
    val L_1500: Int? = null,
    val L_2000: Int? = null,
    val L_3000: Int? = null,
    val L_4000: Int? = null,
    val L_6000: Int? = null,
    val L_8000: Int? = null
)