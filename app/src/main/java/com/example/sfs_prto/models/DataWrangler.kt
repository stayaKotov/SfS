package com.example.sfs_prto.models

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.sfs_prto.ClientActivity
import com.example.sfs_prto.RunnerActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mongodb.client.ChangeStreamIterable
import com.mongodb.client.model.changestream.ChangeStreamDocument
//import com.mongodb.MongoClient
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.BsonString
import org.litote.kmongo.KMongo
import org.litote.kmongo.find
import org.litote.kmongo.listen

class DataWrangler {
//
//    var listOfUserParams = listOf(
//        "name", "surname", "login", "password", "age", "email", "phone", "is_client"
//    )
//
//
//    fun getUsersByLoginPassword(login: String, password: String): List<BsonDocument>? {
//        //"{'login': ${login}, 'password': ${password}}"
//        val cursor = this.userColl.find(
//            BsonDocument("login", BsonString(login)).append("password", BsonString(password)
//            ), BsonDocument::class.java
//        ).toMutableList<BsonDocument>()
//        Log.d("getUsersByLoginPassword", "${cursor.size}")
//        return if (cursor.size > 0) cursor else null
//    }
//    fun getUsersByLogin(login: String): List<BsonDocument>? {
//        // "{'login': ${login}}"
//        val cursor = this.userColl.find(
//            BsonDocument("login", BsonString(login)), BsonDocument::class.java
//        ).toMutableList<BsonDocument>()
//        return if (cursor.size > 0) cursor else null
//    }
//
//    fun getUserByLoginPassword(login: String, password: String): BsonDocument? {
//        val cursor = this.getUsersByLoginPassword(login, password)
//        Log.d("wrangler", " user exist = ${cursor}")
//        return if (cursor != null) cursor[0] else null
//    }
//
//    fun getUserByLogin(login: String): BsonDocument? {
//        val cursor = this.getUsersByLogin(login)
//        return if (cursor != null) cursor[0] else null
//    }
//
//    fun getOrderInfo(order_id: String): BsonDocument? {
//        val cursor = this.orderColl.find(
//            BsonDocument("order_id", BsonString(order_id)), BsonDocument::class.java
//        ).toMutableList<BsonDocument>()
//        return if (cursor.size > 0) cursor[0] else null
//    }
//
//    fun checkUserExistance(login: String) : Boolean {
//        val maybeUserExist = this.getUserByLogin(login)
//        return maybeUserExist != null
//    }
//
//    fun writeNewUserIntoDB(
//        login: String, password: String, phone: String, email: String, isclient: Boolean
//    ) : String {
//        fun Boolean.toInt() = if (this) 1 else 0
//
//        this.userColl.insertOne(
//            BsonDocument("login", BsonString(login))
//            .append("password", BsonString(password))
//            .append("phone", BsonString(phone))
//            .append("email", BsonString(email))
//            .append("name", BsonString("-None"))
//            .append("surname", BsonString("-None"))
//            .append("age", BsonInt32(-1))
//            .append("is_client", BsonInt32(isclient.toInt()))
//        )
//        return  "Done"
//    }
//
//    fun isPastedDataForNewUserOK(
//        login: String, password: String, repeatPassword: String, phone: String, email: String
//    ): Boolean {
//        if (login.equals("")) {
//            return false
//        }
//        if (password.equals("")) {
//            return false
//        }
//        if (repeatPassword.equals("")) {
//            return false
//        }
//        if (phone.equals("")) {
//            return false
//        }
//        if (email.equals("")) {
//            return false
//        }
//        if (password.equals(repeatPassword).not()) {
//            return false
//        }
//        return true
//    }
//
////    fun updateDataHolder(user: BsonDocument?) {
////        var data_holder = DataHolder.instance
////        if (user != null) {
////            Log.d("wrangler data holder", "user = $user")
////
////            data_holder.name = user["name"]?.asString()?.value.toString()
////            data_holder.surname = user["surname"]?.asString()?.value.toString()
////            data_holder.login = user["login"]?.asString()?.value.toString()
////            data_holder.password = user["password"]?.asString()?.value.toString()
////            data_holder.age = user["age"]?.asString()?.value.toString()
////            data_holder.email = user["email"]?.asString()?.value.toString()
////            data_holder.phone = user["phone"]?.asString()?.value.toString()
////            data_holder.is_client = user["is_client"]?.asString()?.value.toString()
////
////            Log.d("updateDataHolder 0","${data_holder.login}, ${data_holder.name}")
////
////            var order_ids = user["orders_id"]?.asArray()?.values?.toList()
////            if (order_ids  != null) {
////                var orders_iterator = order_ids.iterator()
////                var orders: MutableList<OrderData> = mutableListOf<OrderData>()
////                Log.d("updateDataHolder 1", "${data_holder.login}, ${data_holder.name}")
////                while (orders_iterator.hasNext()) {
////                    var order_info: BsonDocument? =
////                        getOrderInfo(orders_iterator.next().asString().value.toString())
////                    if (order_info != null) {
////                        val new_order_instance = OrderData()
////                        val tmp_order_id: String? =
////                            order_info["order_id"]?.asString()?.value?.toString()
////                        val tmp_order_title: String? =
////                            order_info["order_title"]?.asString()?.value?.toString()
////                        val tmp_client_login: String? =
////                            order_info["from"]?.asString()?.value?.toString()
////                        val tmp_runner_login: String? =
////                            order_info["to"]?.asString()?.value?.toString()
////                        val tmp_sum: String? = order_info["sum"]?.asString()?.value?.toString()
////                        new_order_instance.order_id = tmp_order_id ?: "OrderId is None"
////                        new_order_instance.order_title = tmp_order_title ?: "OrderTitle is None"
////                        new_order_instance.from = tmp_client_login ?: "ClientLogin is None"
////                        new_order_instance.to = tmp_runner_login ?: "RunnerLogin is None"
////                        new_order_instance.sum = tmp_sum ?: "sum is None"
////                        orders.add(new_order_instance)
////                        Log.d("Order INFO", "${new_order_instance.from}, ${new_order_instance.to}")
////                    }
////
////                }
////                Log.d("updateDataHolder 2", "${data_holder.login}, ${data_holder.name}")
////                data_holder.orders = orders
////            } else {
////                data_holder.orders = null
////            }
////        }
//////        Log.d("wrangler data holder 1", "data holder ordersID_info = ${data_holder.orders}")
////    }
//
////    fun loadDataToDataHolderWithLogin(login: String) {
////        var currentUser = this.getUserByLogin(login)
////        this.updateDataHolder(currentUser)
////    }
//
//
////
////    fun loadDataToDataHolderWithLoginPassword(login: String, password: String): Boolean {
////        var currentUser = this.getUserByLoginPassword(login, password)
////        Log.d("loadDataWithLoginPasswo", "${currentUser}")
////        if (currentUser != null){
////            this.updateDataHolder(currentUser)
////            var data_holder = DataHolder.instance
////            Log.d("loadDataWithLoginPasswo", "data holder name = ${data_holder.name}")
////            return true
////        } else {
////            Log.d("loadDataWithLoginPasswo", "Current user is NULL")
////        }
////        return false
////    }
//
//    fun createNewUser(
//        login: String, password: String, repeatPassword: String, phone: String, email: String,
//        isClient: Boolean
//    ): Boolean {
//        var isUserExist = this.checkUserExistance(login)
//        var isDataGood = this.isPastedDataForNewUserOK(login, password, repeatPassword, phone, email)
//        var isNewUserCreated = false
//        Log.d("Debug", "$isUserExist")
//        Log.d("Debug", "$isDataGood")
//        if (isUserExist.not() && isDataGood) {
//            Log.d("Debug", "All are good")
//            this.writeNewUserIntoDB(login, password, phone, email, isClient)
//            isNewUserCreated = true
//        }
//        return isNewUserCreated
//    }
//
//
//    fun getMessagesByOrderId(order_id: String): List<BsonDocument>? {
//        val cursor = this.messageColl.find(
//            BsonDocument("order_id", BsonString(order_id)), BsonDocument::class.java
//        ).toMutableList<BsonDocument>()
//        Log.d("getMessagesByOrderId", "There is ${cursor.size} messages")
//        return if (cursor.size > 0) cursor else null
//
//    }
//
//    fun getMessages(order_id: String): List<MessageData>? {
//        val bson_messages = this.getMessagesByOrderId(order_id)
//        Log.d("bson_messages length", "${bson_messages?.size}")
//        Log.d("getMesssage", "$order_id")
//        var messages : MutableList<MessageData> = mutableListOf<MessageData>()
//        if (bson_messages != null) {
//            bson_messages.forEach{
//                var new_message = MessageData()
//                new_message.order_id = it["order_id"]?.asString()?.value.toString()
//                new_message.to = it["to"]?.asString()?.value.toString()
//                new_message.from = it["from"]?.asString()?.value.toString()
//                new_message.message = it["message"]?.asString()?.value.toString()
//                messages.add(new_message)
//            }
//            messages.forEach {
//                Log.d("ffff", "${it.message}")
//            }
//            return messages
//        }
//        return null
//    }
//
//    fun getMessagesFromBsonDoc(bson_messages: List<BsonDocument>?): List<MessageData>? {
//        var messages : MutableList<MessageData> = mutableListOf<MessageData>()
//        if (bson_messages != null) {
//            bson_messages.forEach{
//                var new_message = MessageData()
//                new_message.order_id = it["order_id"]?.asString()?.value.toString()
//                new_message.to = it["to"]?.asString()?.value.toString()
//                new_message.from = it["from"]?.asString()?.value.toString()
//                new_message.message = it["message"]?.asString()?.value.toString()
//                messages.add(new_message)
//            }
//            messages.forEach {
//                Log.d("ffff", "${it.message}")
//            }
//            return messages
//        }
//        return null
//    }
//
//
//    fun writeNewMessageIntoDB(order_id: String, from: String, to: String, message: String): Boolean {
//        Log.d("Debug", "$order_id")
//        Log.d("Debug", "$from")
//        Log.d("Debug", "$to")
//        Log.d("Debug", "$message")
//
//        this.messageColl.insertOne(
//            BsonDocument("order_id", BsonString(order_id))
//            .append("from", BsonString(from))
//            .append("to", BsonString(to))
//            .append("message", BsonString(message))
//        )
//        var isNewUserCreated = true
//        return  isNewUserCreated
//    }

    fun updateDataHolderFromFirebase(user: User) {
        var data_holder = DataHolder.instance
        data_holder.user = user
    }

    fun updateOrdersInDataHolderFromFirebase(orders: MutableList<OrderData>) {
        var data_holder = DataHolder.instance
        data_holder.orders = orders
    }



}