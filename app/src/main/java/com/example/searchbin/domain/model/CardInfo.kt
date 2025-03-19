package com.example.searchbin.domain.model


data class CardInfo(
    val id:String,
    val number: String,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: CountryInfo,
    val bank: BankInfo
)
