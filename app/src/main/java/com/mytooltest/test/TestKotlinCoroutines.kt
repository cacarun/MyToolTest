package com.mytooltest.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {

    GlobalScope.launch {

        println("1:${Thread.currentThread().name}")

        delay(1000)

        println("2:${Thread.currentThread().name}")
    }

    println("3:${Thread.currentThread().name}")

    Thread.currentThread().join()
}