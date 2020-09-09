package com.caldremch.simplehttp

import com.caldremch.android.http.SimpleRequest
import com.caldremch.android.http.callback.HttpCallback
import com.caldremch.simplehttp.customhttp.SampleConvert
import com.caldremch.simplehttp.customhttp.SampleObsHandler
import com.caldremch.simplehttp.customhttp.SampleServerUrlConfig
import com.caldremch.simplehttp.customhttp.TestApiConstant
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Before
    fun be(){
        SimpleRequest.register(SampleConvert(), SampleObsHandler(), SampleServerUrlConfig())
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

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