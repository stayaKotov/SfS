package com.example.sfs_prto

class DataHolder {
    var name: String? = null
    var surname: String? = null
    var login: String? = null
    var password: String? = null
    var age: String? = null
    var email: String? = null
    var phone: String? = null
    var is_client: String? = null

    companion object {
        val instance = DataHolder()
    }
}