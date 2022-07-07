package com.android.calculatorapp

import android.util.Log
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var currentResult = 0f
    var number1 = ""
    var number2 = ""
    var operation = ""

    init {

    }


    fun initMembers(){
        currentResult = 0f
        number1 = ""
        number2 = ""
        operation = ""
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
            "+" -> currentResult = (number1.toInt() + number2.toInt()).toFloat();
            "-" -> currentResult = (number1.toInt() - number2.toInt()).toFloat();
            "*" -> currentResult = (number1.toInt() * number2.toInt()).toFloat();
            "/" -> currentResult = (number1.toInt() / number2.toInt()).toFloat();
        }
    }

    fun getResult(): Float{
        val res =  currentResult
        clear()
        return res
    }

    fun clear(){
        initMembers()
    }

    fun getCurrentExpr(): String{
        return number1 + operation + number2
    }
}