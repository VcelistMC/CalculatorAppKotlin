package com.android.calculatorapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class ResultFragment : Fragment() {
    lateinit var resultView: TextView
    lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view);
        initClickListeners();
        receiveBundleIfExists()
    }

    private fun initClickListeners() {
        initBackButtonListener()
    }

    private fun initBackButtonListener() {
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initViews(view: View) {
        resultView = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }


    private fun receiveBundleIfExists() {
        val incomingData: Bundle? = this.arguments;
        if(incomingData != null){
            val result = incomingData.getFloat("result")
            resultView.text = result.toString()

        }
    }

}