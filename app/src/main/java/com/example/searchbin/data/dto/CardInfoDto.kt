package com.example.searchbin.data.dto


data class CardInfoDto(
    val number: String?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryInfoDto?,
    val bank: BankInfoDto?
)
