package com.factus.app.data.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(@SerializedName("data") val data: List<T>){

}
