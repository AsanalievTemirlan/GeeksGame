package com.example.geeksgame.model

import com.google.gson.annotations.SerializedName

data class LeadModel(
    @SerializedName("ID") val id: String,
    @SerializedName("TITLE") val title: String?,
    @SerializedName("NAME") val name: String?,
    @SerializedName("PHONE") val phone: List<Phone>?
)

data class Phone(
    @SerializedName("VALUE") val value: String?,
    @SerializedName("VALUE_TYPE") val valueType: String?
)

data class LeadListResponse(
    @SerializedName("result") val result: List<LeadModel>
)
