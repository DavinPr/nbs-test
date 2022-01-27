package com.adityadavin.nbsmoviedb.utils

fun main(){
    rotateLeft(intArrayOf(1,2,3,4,5,6), 3)
}

fun rotateLeft(arrayInt: IntArray, numOfRotation: Int) {
    val result = IntArray(arrayInt.size)
    for (i in arrayInt.indices) {
        val j = (i + arrayInt.size - numOfRotation) % arrayInt.size
        result[j] = arrayInt[i]
    }
    result.forEach(::println)
}