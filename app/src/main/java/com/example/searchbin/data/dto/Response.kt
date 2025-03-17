package com.example.searchbin.data.dto

open class Response<T> {
    var resultCode = 0
    var body: T? = null
    var errorMessage: String? = null
}