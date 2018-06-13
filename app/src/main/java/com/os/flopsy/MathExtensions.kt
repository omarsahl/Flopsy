package com.os.flopsy

/**
 * Created by Omar on 12-Jun-18 4:22 AM.
 */

fun map(value: Float, iStart: Float, iStop: Float, oStart: Float, oStop: Float): Float {
    return oStart + (oStop - oStart) * ((value - iStart) / (iStop - iStart))
}