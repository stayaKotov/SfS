package com.example.sfs_prto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sfs_prto.models.DataHolder
import com.example.sfs_prto.models.OrderData
import com.example.sfs_prto.ui.orders_client.OrdersClientFragment
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_new_order.*
import kotlinx.android.synthetic.main.fragment_orders.*

class NewOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order)


        btn_create_new_order.setOnClickListener {
            var title = et_titie.text.toString()
            var description = et_description.text.toString()
            var price = et_price.text.toString()
            var data_holder = DataHolder.instance
            var order_count = data_holder.user?.orders?.toInt()
            var login_from = data_holder.user?.login.toString()


            var base_ref = FirebaseDatabase.getInstance().getReference("orders").push()

            var new_order = OrderData(
                base_ref.key, title,description,login_from,"None",price
            )
            data_holder.user?.orders = (data_holder.user?.orders?.toInt()?.plus(1)).toString()

            base_ref.setValue(new_order)
                .addOnCompleteListener {
                    Log.d("Insert order", "Order inserted")
                    var intent = Intent(this,  ClientActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d("Insert order", "Order not inserted")
                }


        }
    }
}
