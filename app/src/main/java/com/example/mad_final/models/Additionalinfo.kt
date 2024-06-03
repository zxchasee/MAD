package com.example.mad_final.models

import com.google.gson.annotations.SerializedName

data class AdditionalInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("record_id")
    val recordId: String,
    @SerializedName("value")
    val value: String,
)