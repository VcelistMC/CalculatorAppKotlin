package com.android.calculatorapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    private var number1 = ""
    private var number2 = ""
    private var operation = ""
    private var showResultCompleted = false

    private val _currentExpr = MutableLiveData<String>()
    val currentExpr: LiveData<String>
        get() = _currentExpr

    private val _currentResult = MutableLiveData<Float>()
    val currentResult: LiveData<Float>
        get() = _currentResult



    fun initMembers(){
        number1 = ""
        number2 = ""
        operation = ""
    }

    fun isShowResultCompleted(): Boolean{
        return showResultCompleted
    }

    fun showResultCompleted(){
        showResultCompleted = true
    }

    fun receiveInput(input: Char){
        if(Character.isDigit(input)){
            if(operation == "") number1 += input;
            else number2 += input
        }
        else{
            if(number1 != ""){
                operation = input.toString();
            }

        }
        updateCurrExpr()
    }

    private fun updateCurrExpr() {
        _currentExpr.value = number1 + operation + number2
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(this::class.java.simpleName, "Calculator View model destroyed")
    }

    fun calculateResult(){
        // prevent calculation if numbers are empty or if the user divides by zero
        if(number1 == "" || number2 == "" || (operation == "/" && number2 == "0"))
            return

        when(operation){
            "+" -> _currentResult.value = (number1.toInt() + number2.toInt()).toFloat();
            "-" -> _currentResult.value = (number1.toInt() - number2.toInt()).toFloat();
            "*" -> _currentResult.value = (number1.toInt() * number2.toInt()).toFloat();
            "/" -> _currentResult.value = (number1.toInt() / number2.toInt()).toFloat();
        }
        showResultCompleted = false
    }

    fun clear(){
        initMembers()
    }

}