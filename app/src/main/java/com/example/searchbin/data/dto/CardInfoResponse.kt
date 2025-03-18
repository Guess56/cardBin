package com.example.searchbin.data.dto


data class CardInfoResponse (
    val number: NumberInfoDto?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val country: CountryInfoDto?,
    val bank: BankInfoDto?
)