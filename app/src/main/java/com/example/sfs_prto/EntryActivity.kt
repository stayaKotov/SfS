package com.example.sfs_prto
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.entry.*
import com.example.sfs_prto.models.DataHolder
import com.example.sfs_prto.models.DataWrangler
import com.example.sfs_prto.models.OrderData
import com.example.sfs_prto.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

//fun get_specific_user(login: String, password: String) {
// env MONGODB=mongodb://root:example@localhost:27017/Data


data class Users(val login: String, val password: String)




class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry)

        enterBtn.setOnClickListener {
            trySignIn()
        }
        registrationBtn.setOnClickListener {
            val createNewUserIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(createNewUserIntent)
        }
    }

    private fun getUserFromDB(email: String, context: Context) {
        var ref = FirebaseDatabase.getInstance().getReference("users")
//        var currentUser: RegistrationActivity.User? = null
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        Log.d("Iterate", it.toString())
                        val currentUser = it.getValue(User::class.java)
                        Log.d("Test", currentUser?.login)

                        if (currentUser != null) {
                            Log.d("Find User", "currentUser is not NULL")
                            Log.d("Find User", "currentUser ${currentUser.client}")
                            var data_wrangler = DataWrangler()
                            data_wrangler.updateDataHolderFromFirebase(currentUser)
                            var data_holder = DataHolder.instance

                            if (data_holder.user?.client.equals("1")) {
                                val openClientActivityIntent =
                                    Intent(context, ClientActivity::class.java)
                                openClientActivityIntent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(openClientActivityIntent)
                            } else {
                                val openRunnerActivityIntent =
                                    Intent(context, RunnerActivity::class.java)
                                openRunnerActivityIntent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(openRunnerActivityIntent)
                            }

                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    //
                }
            }
        )
    }



    private fun trySignIn() {
        var etEmail = et_email_main.text.toString()
        var etPassword = et_password_main.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail, etPassword)
            .addOnCompleteListener {
                    getUserFromDB(etEmail, this)
                }

            .addOnFailureListener {
                Log.d("Entry", "Cant find user with this info: ${etEmail}, ${etPassword}")
            }

    }
}
