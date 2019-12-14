package com.example.sfs_prto.models

class DataHolder {
    var user: User? = null
    var orders: List<OrderData>? = emptyList()

//    var name: String? = null
//    var surname: String? = null
//    var login: String? = null
//    var password: String? = null
//    var age: String? = null
//    var email: String? = null
//    var phone: String? = null
//    var is_client: String? = null
//    var orders: List<OrderData>? = emptyList()
//

    companion object {
        val instance = DataHolder()
    }
}