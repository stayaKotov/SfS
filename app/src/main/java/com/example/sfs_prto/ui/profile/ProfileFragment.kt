package com.example.sfs_prto.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sfs_prto.DataHolder
import com.example.sfs_prto.EntryActivity
import com.example.sfs_prto.R
import com.example.sfs_prto.RunnerActivity
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

//        root.btnChangePassword.setOnClickListener{
//            Log.d("ChangePassMethod", "I can change your password")
//            val createNewUserIntent = Intent(activity, EntryActivity::class.java)
//            startActivity(createNewUserIntent)
//        }
//        root.btnExit.setOnClickListener{
//            Log.d("ExitMethod", "I can end all this shit")
//            val createNewUserIntent = Intent(activity, EntryActivity::class.java)
//            startActivity(createNewUserIntent)
//        }

        var data_holder = DataHolder.instance
        root.tv_profile_surname_name.setText("${data_holder.surname.toString()} ${data_holder.name.toString()}")
//        root.et_profile_user_surname.setText(data_holder.surname.toString())
        root.tv_profile_login.setText(data_holder.login.toString())
//        root.et_profile_user_age.setText(data_holder.age.toString())
        root.tv_profile_email.setText(data_holder.email.toString())
        root.tv_profile_phone.setText(data_holder.phone.toString())
//        if (data_holder.is_client.toString().equals("1")) {
//            root.cb_is_client.isChecked = true
//            root.cb_is_runner.isChecked = false
//        }  else {
//            root.cb_is_client.isChecked = false
//            root.cb_is_runner.isChecked = true
//        }
//        for is_client et_new_email.setText(data_holder.email.toString())


        return root
    }
}