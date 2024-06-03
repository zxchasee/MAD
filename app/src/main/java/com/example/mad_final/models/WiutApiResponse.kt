package com.example.mad_final.models

import com.google.gson.annotations.SerializedName

data class WiutApiResponse<T> (
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T?
)