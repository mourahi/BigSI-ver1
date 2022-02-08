package com.mourahi.bigsi.phones

data class Phone(
    var ref: Int = -1,
    var cycle: String = "",
    var commune: String = "",
    var gresa: String = "",
    var ecole: String = "",
    var nom: String = "",
    var tel: String = "",
    var fonction: String = "",
    var email: String = "",
    var geo: String = "",
    var fav: Boolean = false,
    var refgroup: Int = -1,
)