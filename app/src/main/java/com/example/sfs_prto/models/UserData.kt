package com.example.sfs_prto.models

class User(
    val uid: String, val name:String, val surname: String, val email:String, val phone:String,
    val client:String, val age:String, val login:String, var orders:String
) {
    constructor(): this("","","","","","","","", "")
}