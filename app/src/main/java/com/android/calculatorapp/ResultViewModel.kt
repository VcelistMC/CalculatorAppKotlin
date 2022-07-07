package com.android.calculatorapp

import android.os.Bundle
import androidx.lifecycle.ViewModel

class ResultViewModel(private val finalResult: Float): ViewModel() {
    val result = finalResult

    companion object{
        fun extractFromBundle(incomingData: Bundle?): Float{
            if(incomingData != null) {
                return incomingData.getFloat("result")
            }
            return 0.0f
        }
    }
}