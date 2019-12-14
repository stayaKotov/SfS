package com.example.sfs_prto.models

class MessageData(
    var messageId: String?, var orderId:String, var fromMessage : String, var toMessage: String,
    var message: String, var timestamp: Long
) {
    constructor(): this("","","","", "", 0)
}