package com.example.sfs_prto

import android.util.Log
import com.mongodb.MongoClient
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.BsonString
import com.example.sfs_prto.DataHolder

class DataWrangler {

    var listOfUserParams = listOf(
        "name", "surname", "login", "password", "age", "email", "phone", "is_client"
    )
    val mongo = MongoClient("172.17.0.1", 27017)
    val db = this.mongo.getDatabase("Data")
    val userColl  = this.db.getCollection("Users", BsonDocument::class.java)

    fun getUsersByLoginPassword(login: String, password: String): List<BsonDocument>? {
        val cursor = this.userColl.find(
            BsonDocument("login", BsonString(login)).append("password", BsonString(password)
            ), BsonDocument::class.java
        ).toMutableList<BsonDocument>()
        return if (cursor.size > 0) cursor else null
    }
    fun getUsersByLogin(login: String): List<BsonDocument>? {
        val cursor = this.userColl.find(
            BsonDocument("login", BsonString(login)), BsonDocument::class.java
        ).toMutableList<BsonDocument>()
        return if (cursor.size > 0) cursor else null
    }

    fun getUserByLoginPassword(login: String, password: String): BsonDocument? {
        val cursor = this.getUsersByLoginPassword(login, password)
        Log.d("wrangler", " user exist = ${cursor}")
        return if (cursor != null) cursor[0] else null
    }

    fun getUserByLogin(login: String): BsonDocument? {
        val cursor = this.getUsersByLogin(login)
        return if (cursor != null) cursor[0] else null
    }

    fun checkUserExistance(login: String) : Boolean {
        val maybeUserExist = this.getUserByLogin(login)
        return maybeUserExist != null
    }

    fun writeNewUserIntoDB(
        login: String, password: String, phone: String, email: String, isclient: Boolean
    ) : String {
        fun Boolean.toInt() = if (this) 1 else 0

        this.userColl.insertOne(
            BsonDocument("login", BsonString(login))
            .append("password", BsonString(password))
            .append("phone", BsonString(phone))
            .append("email", BsonString(email))
            .append("name", BsonString("-None"))
            .append("surname", BsonString("-None"))
            .append("age", BsonInt32(-1))
            .append("is_client", BsonInt32(isclient.toInt()))
        )
        return  "Done"
    }

    fun isPastedDataForNewUserOK(
        login: String, password: String, repeatPassword: String, phone: String, email: String
    ): Boolean {
        if (login.equals("")) {
            return false
        }
        if (password.equals("")) {
            return false
        }
        if (repeatPassword.equals("")) {
            return false
        }
        if (phone.equals("")) {
            return false
        }
        if (email.equals("")) {
            return false
        }
        if (password.equals(repeatPassword).not()) {
            return false
        }
        return true
    }

    fun updateDataHolder(user: BsonDocument?) {
        var data_holder = DataHolder.instance
        if (user != null) {
            Log.d("wrangler data holder", "user = $user")

            data_holder.name = user.get("name")?.asString()?.value.toString()
            data_holder.surname = user.get("surname")?.asString()?.value.toString()
            data_holder.login = user.get("login")?.asString()?.value.toString()
            data_holder.password = user.get("password")?.asString()?.value.toString()
            data_holder.age = user.get("age")?.asString()?.value.toString()
            data_holder.email = user.get("email")?.asString()?.value.toString()
            data_holder.phone = user.get("phone")?.asString()?.value.toString()
            data_holder.is_client = user.get("is_client")?.asString()?.value.toString()
        }
        Log.d("wrangler data holder 1", "data holder name = ${data_holder.name}")
    }

    fun loadDataToDataHolderWithLogin(login: String) {
        var currentUser = this.getUserByLogin(login)
        this.updateDataHolder(currentUser)
    }

    fun loadDataToDataHolderWithLoginPassword(login: String, password: String) {
        var currentUser = this.getUserByLoginPassword(login, password)
        this.updateDataHolder(currentUser)
        var data_holder = DataHolder.instance
        Log.d("wrangler data holder", "data holder name = ${data_holder.name}")
    }

    fun createNewUser(
        login: String, password: String, repeatPassword: String, phone: String, email: String,
        isClient: Boolean
    ): Boolean {
        var isUserExist = this.checkUserExistance(login)
        var isDataGood = this.isPastedDataForNewUserOK(login, password, repeatPassword, phone, email)
        var isNewUserCreated = false
        Log.d("Debug", "$isUserExist")
        Log.d("Debug", "$isDataGood")
        if (isUserExist.not() && isDataGood) {
            Log.d("Debug", "All are good")
            this.writeNewUserIntoDB(login, password, phone, email, isClient)
            isNewUserCreated = true
        }
        return isNewUserCreated

    }
}