package com.adewijayanto.defilmsapp3.vo

data class Resources<T>(val status: StatusMessage, val data: T?, val message: String?){
    companion object{
        fun <T> success(data: T?): Resources<T> = Resources(StatusMessage.SUCCESS, data, null)

        fun <T> error(msg: String?, data: T?): Resources<T> = Resources(StatusMessage.ERROR, data, msg)

        fun <T> loading(data: T?): Resources<T> = Resources(StatusMessage.LOADING, data, null)

    }
}