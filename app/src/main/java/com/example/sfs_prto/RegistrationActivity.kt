package com.example.sfs_prto

import android.content.Intent
import com.example.sfs_prto.DataWrangler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_registration.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.example.sfs_prto.DataHolder

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        createUserBtn.setOnClickListener{
            val etNewLogin = findViewById<EditText>(R.id.et_new_login).text.toString()
            val etNewPassword = findViewById<EditText>(R.id.et_new_password).text.toString()
            val etNewPasswordRepeat = findViewById<EditText>(R.id.et_new_password_repeat).text.toString()
            val etNewPhone = findViewById<EditText>(R.id.et_new_phone).text.toString()
            val etNewEmail = findViewById<EditText>(R.id.et_new_email).text.toString()
            val cbNewIsClient = findViewById<CheckBox>(R.id.cb_is_new_client).isChecked
            val cbNewIsRunner = findViewById<CheckBox>(R.id.cb_is_new_runner).isChecked
            val isNewClient = cbNewIsClient && cbNewIsRunner.not()
            var that = this

            var datawrangler = DataWrangler()
            doAsync {
                var isUserCreated = datawrangler.createNewUser(
                    etNewLogin, etNewPassword, etNewPasswordRepeat, etNewPhone, etNewEmail, isNewClient
                )
                uiThread {
                    if (isUserCreated) {
                        if (isNewClient) {
                            val createNewUserIntent = Intent(that, ClientActivity::class.java)
                            startActivity(createNewUserIntent)
                        } else {
                            val createNewUserIntent = Intent(that, RunnerActivity::class.java)
                            startActivity(createNewUserIntent)
                        }

                    } else {
                        Log.d("Debug", "Cant create new user with this data")
                        // выводить доп инфу на экран пользователю о том, что что-то не так
                    }
                }
            }


        }
    }
}
