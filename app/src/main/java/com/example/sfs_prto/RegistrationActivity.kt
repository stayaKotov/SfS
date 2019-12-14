package com.example.sfs_prto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sfs_prto.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        createUserBtn.setOnClickListener{
            tryCreateNewUser()


        }
    }

    private fun tryCreateNewUser() {
        val etNewLogin = et_new_login.text.toString()
        val etNewPassword = et_new_password.text.toString()
        val etNewPasswordRepeat = et_new_password_repeat.text.toString()
        val etNewPhone = et_new_phone.text.toString()
        val etNewEmail = et_new_email.text.toString()
        val cbNewIsClient = cb_is_new_client.isChecked
        val cbNewIsRunner = cb_is_new_runner.isChecked
        val isNewClient = cbNewIsClient && cbNewIsRunner.not()

        if (etNewEmail.isEmpty() || etNewPassword.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }

        if (etNewPassword.equals(etNewPasswordRepeat).not()) {
            Toast.makeText(this, "Passwords doesnt matched", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(etNewEmail, etNewPassword)
            .addOnCompleteListener{
                if (!it .isSuccessful) return@addOnCompleteListener
                Log.d("Registration", "Successfully create user with uid: ${it.result?.user?.uid}")

                var uid = FirebaseAuth.getInstance().uid ?: ""
                var user_ref = FirebaseDatabase.getInstance().getReference("/users/${uid}")
                var new_user = User(
                    uid, "None", "None", etNewEmail, etNewPhone, isNewClient.toInt().toString(),
                    "-1", etNewLogin, "0"
                )
                user_ref.setValue(new_user)
                    .addOnCompleteListener {
                        Log.d("Save new user", "We did it!!!")

                        if (isNewClient) {
                            val createNewUserIntent = Intent(this, ClientActivity::class.java)
                            createNewUserIntent .flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(createNewUserIntent)
                        } else {
                            val createNewUserIntent = Intent(this, RunnerActivity::class.java)
                            createNewUserIntent .flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(createNewUserIntent)
                        }

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                        Log.d("Registration", "Failed to create user : ${it.message}")
                    }

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                Log.d("Registration", "Failed to create user : ${it.message}")
            }

    }
    private fun Boolean.toInt() = if (this) 1 else 0

}
