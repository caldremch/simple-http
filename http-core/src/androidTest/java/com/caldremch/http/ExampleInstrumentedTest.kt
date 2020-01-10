package com.caldremch.http

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.caldremch.SimpleRequest
import com.caldremch.SimpleRequestUtils
import com.caldremch.callback.HttpCallback
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.caldremch.http.test", appContext.packageName)
    }

    @Before
    fun before(){
    }


    @Test
    fun testHttp() {


    }

}
