package com.example.sfs_prto.ui.orders_client

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sfs_prto.*
import com.example.sfs_prto.models.DataHolder
import com.example.sfs_prto.models.DataWrangler
import com.example.sfs_prto.models.OrderData
import com.example.sfs_prto.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.fragment_orders.view.*
import kotlinx.android.synthetic.main.orders_item.view.*

class OrdersClientFragment : Fragment() {

    private lateinit var ordersClientViewModel: OrdersClientViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ordersClientViewModel =
            ViewModelProviders.of(this).get(OrdersClientViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_orders, container, false)

        root.btn_new_order.setOnClickListener {
            var intent = Intent(root.context, NewOrderActivity::class.java)
            startActivity(intent)
        }

        getOrderdsFromDB(root)
//        fetchOrders(root)
        return root
    }

    companion object {
        val ORDER_ID_KEY = "ORDER_ID_KEY"
        val login_from = "login_from"
        val login_to = "login_to"
    }

    private fun getOrderdsFromDB(root: View) {
        var data_holder = DataHolder.instance
        var data_wrangler = DataWrangler()
        var ref = FirebaseDatabase.getInstance().getReference("orders")
        var orders: MutableList<OrderData> = mutableListOf<OrderData>()
        Log.d("data_holder.user?.login", "${data_holder.user?.login}")
        var field =  if (data_holder.user?.client.equals("1")) "from" else "to"
        ref.orderByChild(field).equalTo(data_holder.user?.login)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        p0.children.forEach {
                            Log.d("Iterate", it.toString())
                            val currentOrder = it.getValue(OrderData::class.java)
                            Log.d("AA", "$currentOrder")
                            orders.add(currentOrder!!)
                        }
                        data_wrangler.updateOrdersInDataHolderFromFirebase(orders)
                        Log.d("Entry", "${data_holder.orders?.size}")
                        fetchOrders(root)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        //
                    }
                }
            )
    }

    private fun fetchOrders(root: View) {
        val adapter = GroupAdapter<GroupieViewHolder>()

        var data_holder = DataHolder.instance
        if (data_holder.orders != null) {
//            Log.d("OrderdsClientFrag", "${data_holder.orders.size}")
            var orders_iterator = data_holder.orders!!.iterator()
            while (orders_iterator.hasNext()) {
                var tmp_order_data = orders_iterator.next()
                Log.d("tmp_order_data", "${tmp_order_data.title}")

                val from_user = if (data_holder.user?.login.equals(tmp_order_data.from)) tmp_order_data.from else tmp_order_data.to
                val to_user = if (data_holder.user?.login.equals(tmp_order_data.from)) tmp_order_data.to else tmp_order_data.from

                adapter.add(
                    OrderItem(
                        tmp_order_data.id,
                        "${tmp_order_data.title}", "${tmp_order_data.description}",
                        from_user, to_user
                    )
                )
            }
            adapter.setOnItemClickListener { item, view ->

                var orderItem  = item as OrderItem
                if (orderItem.order_to != "None") {
                    var intent = Intent(view.context, ChatActivity::class.java)
                    intent.putExtra(ORDER_ID_KEY, orderItem.order_id)
                    intent.putExtra(login_from, orderItem.order_from)
                    intent.putExtra(login_to, orderItem.order_to)
                    startActivity(intent)
                } else {
                    Log.d("Open Chat", "Cant open chat for order ${orderItem.order_id}")
                }
            }
        }
        root.recycler_view_orders.adapter = adapter
    }

}


class OrderItem(
    val order_id: String?, val title: String, val description:String, val order_from: String, val order_to:String
): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_order_id.text = "${title}"
        viewHolder.itemView.tv_additional_info.text = "Additional info: ${description}"
    }
    override fun getLayout(): Int {
        return R.layout.orders_item

    }
}