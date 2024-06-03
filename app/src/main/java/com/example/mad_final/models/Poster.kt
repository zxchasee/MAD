package com.example.mad_final.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Poster(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("age")
    var age: Int? = null,
    @SerializedName("is_it_true")
    var isItTrue: String? = null,
    @SerializedName("is_it_really_true")
    var isItReallyTrue: String? = null,
    @SerializedName("color")
    var color: String? = null,
    @SerializedName("size")
    var size: Int? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("another_date")
    var anotherDate: Date? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("int_list")
    var intList: List<AdditionalInfo>? = null,
    @SerializedName("text_list")
    var textList: List<AdditionalInfo>? = null,
    @SerializedName("double_list")
    var doubleList: List<AdditionalInfo>? = null
)