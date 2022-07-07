package com.android.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v(this::class.java.simpleName, "Activity Created")
        initViews();
    }



    override fun onResume() {
        super.onResume()
        Log.v(this::class.java.simpleName, "Activity Resumed")
    }

    override fun onStop() {
        super.onStop()
        Log.v(this::class.java.simpleName, "Activity Stopped")
    }

    override fun onPause() {
        super.onPause()
        Log.v(this::class.java.simpleName, "Activity paused")
    }

    fun initViews(){
        replaceFragment(OperationsFragment())
    }


    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(this::class.java.simpleName, "Activity Destroyed")
    }
}