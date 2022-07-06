package com.android.calculatorapp

class ScreenData(val fragmentView: OperationsFragment){
    var currentResult = 0f
    var number1 = ""
    var number2 = ""
    var operation = ""

    init {
        initMembers()
    }


    fun initMembers(){
        var currentResult = 0f
        var number1 = ""
        var number2 = ""
        var operation = ""
        fragmentView.updateView("")
    }

    fun receiveInput(input: Char){
        if(Character.isDigit(input)){
            if(operation == "") number1 += input;
            else number2 += input
        }
        else{
            if(input == 'C'){
                initMembers()
//                fragmentView.updateView("");
            }
            else if(input == '=' && number1 != "" && number2 != "" && number2 != "0"){
                when(operation){
                    "+" -> currentResult = (number1.toInt() + number2.toInt()).toFloat();
                    "-" -> currentResult = (number1.toInt() - number2.toInt()).toFloat();
                    "*" -> currentResult = (number1.toInt() * number2.toInt()).toFloat();
                    "/" -> currentResult = (number1.toInt() / number2.toInt()).toFloat();
                }
                fragmentView.showResult(currentResult)
                initMembers()
            }
            else if(number1 != ""){
                operation = input.toString();
            }

        }
        fragmentView.updateView(number1 + operation + number2)

    }
}