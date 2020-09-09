package com.caldremch.simplehttp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.android.http.SimpleRequest
import com.caldremch.android.http.callback.HttpCallback
import com.caldremch.simplehttp.customhttp.TestApiConstant

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startRequest(view: View) {
    }
}
