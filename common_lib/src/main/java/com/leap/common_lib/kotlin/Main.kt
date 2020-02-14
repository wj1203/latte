package com.leap.common_lib.kotlin

import java.io.Serializable

/**
 * @author: leap
 * @time: 2020/2/9
 * @classname: Main
 * @description:
 */
class Main {
}

fun main() {
    val name = "tony"
    val s = "我的名字是$name 名字的长度是${name.length}"
    println(s)
    val s1 = """12
        |11
        |22
        |33
    """.trimMargin()
    println(s1)



}

// 函数
fun add0(x: Int, y: Int): Int {
    return x + y
}

fun add1(x: Int, y: Int) = x + y

// 类

// 第一构造方法参数 ， 类的继承
class User(name:String):Serializable{
    var mName:String

    // 第一构造方法的方法体
    init {
        this.mName = name
    }

    //  第二构造方法，需要继承第一构造
    constructor(name:String,age:String):this(name){

    }

}

var array0 = arrayOf(1,2,3)


