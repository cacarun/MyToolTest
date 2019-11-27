package com.mytooltest.test

fun main() {

//    var age = "str"
//    var result = age?.toInt()
//    print(result)


//    var a: String?= "abc"
//    a = null
//    print(a)
//    print(a?.length)


//    print(if (a != null) a.length else -1)
    /**
     * Elvis 操作符
     *
     * A ?: B 等价于 if(A == null) B
     * A?.B ?: C 等价于 if(A != null) A.B else C
     *
     */
//    print(a?.length ?: -1)


//    val listWithNulls: List<String?> = listOf("Kotlin", null)
//    for (item in listWithNulls) {
//        item?.let { println(it) } // 输出 Kotlin 并忽略 null
//    }


//    val result = "testlet".let {
//        println(it.length)
//        1000
//    }
//    println(result)


//    val a = 123
//    val aInt: Int? = a as? Int
//    print(aInt)


//    val x = 10
//    val y = 9
//    if (x in 1..y+1) {
//        println("fits in range")
//    }


//    val list = listOf("a", "b", "c")
//
//    if (-1 !in 0..list.lastIndex) {
//        println("-1 is out of range")
//    }
//    if (list.size !in list.indices) {
//        println("list size is out of valid list indices range, too")
//    }


    // ----------------------------------------
    // lambda
//    val lambda = {
//        left: Int, right: Int -> left + right
//    }
//    print(lambda(2, 3))


//    val args = Array(4){ i -> (i * i).toString()}
//    val args = arrayOf("0", "1", "4", "9", "16")

//    args.forEach({ item -> print(item) })
//    args.forEach() { item -> print(item) }
//    args.forEach { item -> print(item) }
//    args.forEach { print(it) }
//    args.forEach(::print)

//    args.forEach {
//        if(it == "9") return
//        println(it)
//    }
//    println("The End")







}