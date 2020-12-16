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
        SimpleRequest.post("/test/request/post")
            .put("param1", "value1")
            .put("param2", "value2")
            .put("param3", "value3")
            .execute(object : HttpCallback<Any>() {
                override fun onSuccess(data: Any?) {

                }
            })


        SimpleRequest.get("/test/request/get")
            .put("param1", "value1")
            .put("param2", "value2")
            .put("param3", "value3")
            .execute(object : HttpCallback<Any>() {
                override fun onSuccess(data: Any?) {

                }
            })
    }
}
