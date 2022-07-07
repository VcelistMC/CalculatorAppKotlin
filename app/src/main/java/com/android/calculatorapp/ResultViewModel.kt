package com.android.calculatorapp

import androidx.lifecycle.ViewModel

class ResultViewModel(private val finalResult: Float): ViewModel() {
    val result = finalResult
}