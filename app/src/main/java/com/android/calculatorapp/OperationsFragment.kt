package com.android.calculatorapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class OperationsFragment : Fragment() {
    lateinit var linearLayoutRowOne: LinearLayout
    lateinit var linearLayoutRowTwo: LinearLayout
    lateinit var linearLayoutRowThree: LinearLayout
    lateinit var linearLayoutRowFour: LinearLayout
    lateinit var screen: TextView
    lateinit var calculatorViewModel: CalculatorViewModel


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
        initLiveDataObservers()
    }

    private fun initLiveDataObservers() {
        initExprObserver()
        initResultObserver()
    }

    private fun initResultObserver() {
        calculatorViewModel.currentResult.observe(viewLifecycleOwner, Observer { newResult ->
            Log.v("Bug?", "result updated signal fired $newResult")
            onResultUpdated(newResult)
        })
    }

    private fun initExprObserver() {
        calculatorViewModel.currentExpr.observe(viewLifecycleOwner, Observer { newExpr ->
            updateView(newExpr)
        })
    }

    private fun initVars() {
        calculatorViewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
    }

    private fun initViews(parentView: View) {
        linearLayoutRowOne = parentView.findViewById(R.id.linear_layout_row_one)
        constructRow(arrayOf('1', '2', '3', '+'), linearLayoutRowOne)

        linearLayoutRowTwo = parentView.findViewById(R.id.linear_layout_row_two)
        constructRow(arrayOf('4', '5', '6', '-'), linearLayoutRowTwo)

        linearLayoutRowThree = parentView.findViewById(R.id.linear_layout_row_three)
        constructRow(arrayOf('7', '8', '9', '*'), linearLayoutRowThree)

        linearLayoutRowFour = parentView.findViewById(R.id.linear_layout_row_four)
        // manually create clear and show result button since we want to have a different onClickListeners
        createAndAddButton("C", linearLayoutRowFour, this::onClear)
        createAndAddButton("0", linearLayoutRowFour, this::sendToViewModel)
        createAndAddButton("=", linearLayoutRowFour, this::onCalculateResult)
        createAndAddButton("/", linearLayoutRowFour, this::sendToViewModel)

        screen = parentView.findViewById(R.id.input_text_view)
    }

    private fun onCalculateResult(it: View){
        Log.v("Bug?", "onCalculateResultCalled")
        calculatorViewModel.calculateResult()
    }

    private fun onClear(it: View){
        calculatorViewModel.clear()
        updateView("")
    }

    private fun constructRow(rowElements: Array<Char>, parentLayout: LinearLayout){
        for(element in rowElements){
            createAndAddButton(
                element.toString(),
                parentLayout,
                this::sendToViewModel
            )
        }
    }

    private fun createAndAddButton(buttonText: String,
                                   parentLayout: LinearLayout,
                                   buttonCallback: (it: View) -> Unit
    ){
        val newButton = createButton(buttonText, buttonCallback)
        parentLayout.addView(newButton)
    }

    private fun sendToViewModel(it: View){
        val clickedButton = it as Button;
        Log.v("DefaultCallback", " ${clickedButton.text.toString()} clicked")
        this.calculatorViewModel.receiveInput(clickedButton.text.toString().first());
    }


    private fun createButton(buttonText: String, buttonCallback: (it: View) -> Unit): Button{
        Log.v("ButtonFactoryMethod", "$buttonText created")
        val newButton: Button = Button(requireContext())
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(10,0,10,0);
        newButton.layoutParams = params
        newButton.text = buttonText

        newButton.setOnClickListener {
            buttonCallback(it)
        }
        return newButton;
    }

    fun updateView(text: String){
        screen.text = text
    }

    fun onResultUpdated(result: Float){
        val dataToSend: Bundle = bundleOf("result" to result)
        Log.v("Bug?", "Result updated: $result and ${calculatorViewModel.isShowResultCompleted()}")
        if(!calculatorViewModel.isShowResultCompleted()){
            // FIXME: the resultUpdated event is fired and result fragment is shown everytime I press back idk why
            // tried to fix it by including a flag to indicate if the result was already shown
            // but this too doesn't work, and I have to press back twice before the flag is finally set to true
            calculatorViewModel.showResultCompleted()
            showResultFragment(dataToSend)
        }
    }

    fun showResultFragment(dataToSend: Bundle){
        val resFragment = ResultFragment()
        resFragment.arguments = dataToSend


        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, resFragment)
            .addToBackStack(null)
            .commit()
    }

}