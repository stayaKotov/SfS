package com.example.sfs_prto
import com.example.sfs_prto.DataWrangler
import com.mongodb.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.bson.BsonDocument
import org.bson.BsonString
import org.w3c.dom.Text
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.example.sfs_prto.DataHolder

import org.w3c.dom.Document

//fun get_specific_user(login: String, password: String) {
// env MONGODB=mongodb://root:example@localhost:27017/Data


data class Users(val login: String, val password: String)




class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterBtn.setOnClickListener {
            var etLogin = findViewById<EditText>(R.id.et_login_main)
            var etPassword = findViewById<EditText>(R.id.et_password_main)
            var data_wrangler = DataWrangler()
            var data_holder = DataHolder.instance
            var context = this
            doAsync {
                data_wrangler.loadDataToDataHolderWithLoginPassword(
                    etLogin.text.toString(), etPassword.text.toString()
                )
                uiThread {
                    Log.d("dataholderentryacti", "${data_holder.is_client}")
                    if (data_holder.is_client.equals("1")) {
                        val openClientActivityIntent = Intent(context, ClientActivity::class.java)
                        startActivity(openClientActivityIntent)
                    } else {
                        val openRunnerActivityIntent = Intent(context, RunnerActivity::class.java)
                        startActivity(openRunnerActivityIntent)
                    }
                }
            }

//            println(result)
//            get_specific_user()
////            get_specific_user(etLogin.text.toString(), etPassword.text.toString())
////            val createNewUserIntent = Intent(this, ClientActivity::class.java)
////            startActivity(createNewUserIntent)
        }
        registrationBtn.setOnClickListener {
//            val createNewUserIntent = Intent(this, ClientActivity::class.java)
//            startActivity(createNewUserIntent)
            val createNewUserIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(createNewUserIntent)
        }


    }
}
