package com.caldremch.simplehttp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.SimpleRequest
import com.caldremch.callback.HttpCallback
import com.caldremch.simplehttp.customhttp.TestApiConstant

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startRequest(view: View) {

        SimpleRequest.post("").execute(object : HttpCallback<Thread>(){
            override fun onSuccess(data: Thread?) {
            }
        })

        SimpleRequest.get(TestApiConstant.login)
            .put("phone", "15521029734")
            .put("verifyCode", "111")
            .put("phoneModel", "2323")
            .put("userSource",1)
            .execute(object : HttpCallback<Any>(){
                override fun onSuccess(data: Any?) {
                    System.out.println("testHttp$ onSuccess")
                }
                override fun onError(e: Throwable?) {
                    System.out.println("testHttp$ onError")
                }
            })

    }
}
