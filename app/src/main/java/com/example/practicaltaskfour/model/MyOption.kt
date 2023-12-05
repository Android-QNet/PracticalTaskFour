package com.example.practicaltaskfour.model

data class MyOption(
    val title: String,
    val optionList: List<String>,
    val id: Int = 0
)

data class Poll(
    var poll: String,
    var pollSelect: Boolean = false
)