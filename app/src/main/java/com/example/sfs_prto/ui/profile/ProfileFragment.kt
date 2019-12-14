package com.example.sfs_prto.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sfs_prto.models.DataHolder
import com.example.sfs_prto.R
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
        root.tv_profile_surname_name.setText("${data_holder.user?.surname.toString()} ${data_holder.user?.name.toString()}")
//        root.et_profile_user_surname.setText(data_holder.surname.toString())
        root.tv_profile_login.setText(data_holder.user?.login.toString())
        root.et_profile_age.setText(data_holder.user?.age.toString())
        root.tv_profile_email.setText(data_holder.user?.email.toString())
        root.tv_profile_phone.setText(data_holder.user?.phone.toString())
        if (data_holder.user?.client.toString().equals("1")) {
            root.tv_client.setText("Client")
        } else {
            root.tv_client.setText("Runner")
        }


        return root
    }
}