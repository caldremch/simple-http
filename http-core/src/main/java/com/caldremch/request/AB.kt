package com.caldremch.request

/**
 * @author Caldremch
 * @date 2020-09-08
 * @email caldremch@163.com
 * @describe
 */
class AB : Abs<AB>(){
    fun c():AB{
       return this
    }
    fun d():AB{
        return this
    }
}

class DDD{
    fun a(){
        val ab = AB().c().d().a()
    }
}


