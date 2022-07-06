package com.android.calculatorapp

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Character.isDigit
import java.util.*


class OperationsFragment : Fragment() {
    lateinit var linearLayoutRowOne: LinearLayout
    lateinit var linearLayoutRowTwo: LinearLayout
    lateinit var linearLayoutRowThree: LinearLayout
    lateinit var linearLayoutRowFour: LinearLayout
    lateinit var screen: TextView
    lateinit var screenData: ScreenData;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_operations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initVars()
    }

    private fun initVars() {
        screenData = ScreenData(this);
    }

    private fun initViews(parentView: View) {
        linearLayoutRowOne = parentView.findViewById(R.id.linear_layout_row_one)
        constructRow(arrayOf('1', '2', '3', '+'), linearLayoutRowOne)

        linearLayoutRowTwo = parentView.findViewById(R.id.linear_layout_row_two)
        constructRow(arrayOf('4', '5', '6', '-'), linearLayoutRowTwo)

        linearLayoutRowThree = parentView.findViewById(R.id.linear_layout_row_three)
        constructRow(arrayOf('7', '8', '9', '*'), linearLayoutRowThree)

        linearLayoutRowFour = parentView.findViewById(R.id.linear_layout_row_four)
        constructRow(arrayOf('C', '0', '=', '/'), linearLayoutRowFour)

        screen = parentView.findViewById(R.id.input_text_view)
    }

    private fun constructRow(rowElements: Array<Char>, parentLayout: LinearLayout){
        for(element in rowElements){
            createAndAddButton(
                element.toString(),
                parentLayout,
                isDigit(element)
            )
        }
    }

    private fun createAndAddButton(buttonText: String, parentLayout: LinearLayout, isNumberButton: Boolean){
        val newButton = createButton(buttonText, isNumberButton)
        parentLayout.addView(newButton)
    }

    private fun createButton(buttonText: String, isNumberButton: Boolean): Button{
        val newButton: Button = Button(requireActivity())
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(10,0,10,0);
        newButton.layoutParams = params
        newButton.text = buttonText

        newButton.setOnClickListener {
            val clickedButton = it as Button;
            this.screenData.receiveInput(clickedButton.text.toString().first());
        }
        return newButton;
    }

    fun updateView(text: String){
        screen.text = text
    }

    fun showResult(result: Float){
        val dataToSend: Bundle = Bundle()
        dataToSend.putFloat("result", result)

        val resFragment = ResultFragment()
        resFragment.arguments = dataToSend

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, resFragment)
            .addToBackStack(null)
            .commit()
    }

}