package com.example.sfs_prto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.sfs_prto.models.DataHolder
import com.example.sfs_prto.models.DataWrangler
import com.example.sfs_prto.models.MessageData
import com.example.sfs_prto.models.OrderData
import com.example.sfs_prto.ui.orders_client.OrdersClientFragment
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.litote.kmongo.Data
import org.litote.kmongo.listen

class ChatActivity : AppCompatActivity() {
    var adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        val chat_name = intent.getStringExtra(OrdersClientFragment.ORDER_ID_KEY)
        val order_from = intent.getStringExtra(OrdersClientFragment.login_from)
        val order_to = intent.getStringExtra(OrdersClientFragment.login_to)

        val data_holder = DataHolder.instance
        Log.d("Show stat", "${order_from}, ${order_to}, ${data_holder.user?.login}")

        supportActionBar?.title =  "Chat with ${order_to}"

        recycler_view_chat.adapter = adapter

//        fetchExistingMessages(chat_name, order_from)


        listenToMessages(chat_name, order_from)


        btn_chat_send.setOnClickListener {
            var entered_message_obj = et_chat_message
            var entered_message = entered_message_obj.text.toString()
            var data_holder = DataHolder.instance
            var ref = FirebaseDatabase.getInstance().getReference("messages").push()
            var newMessage = MessageData(ref.key, chat_name, order_from, order_to, entered_message, System.currentTimeMillis()/1000)
            ref.setValue(newMessage)

//            if (order_from.equals(data_holder.user?.login)) {
//                adapter.add(ChatFromItem(entered_message))
//            } else {
//                adapter.add(ChatToItem(entered_message))
//            }
//            recycler_view_chat.scrollToPosition(adapter.itemCount-1)
            entered_message_obj.text.clear()

            }
        }

//    private fun fetchExistingMessages(chat_name: String, orderFrom:String) {
//        var ref = FirebaseDatabase.getInstance().getReference("messages")
//        var messages: MutableList<MessageData> = mutableListOf()
//        ref.orderByChild("orderId").equalTo(chat_name)
//            .addListenerForSingleValueEvent(
//                object : ValueEventListener {
//                    override fun onDataChange(p0: DataSnapshot) {
//                        p0.children.forEach {
//                            Log.d("Iterate from messages", it.toString())
//                            val current_message = it.getValue(MessageData::class.java)
//                            Log.d("MESSAGES AAA", "${current_message?.message}")
//                            if (current_message?.fromMessage.equals(orderFrom)) {
//                                adapter.add(ChatFromItem(current_message?.message))
//                            } else {
//                                adapter.add(ChatToItem(current_message?.message))
//                            }
////                            messages.add(current_message!!)
//                        }
//                        recycler_view_chat.adapter = adapter
//                        recycler_view_chat.scrollToPosition(adapter.itemCount-1)
//                    }
//
//                    override fun onCancelled(p0: DatabaseError) {
//                        //
//                    }
//                }
//            )
//    }
    private fun listenToMessages(chat_name: String, orderFrom:String) {
        val ref = FirebaseDatabase.getInstance().getReference("messages")

        ref.orderByChild("orderId").equalTo(chat_name)
            .addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val addedMessages = p0.getValue(MessageData::class.java)
                Log.d("ListenToMessages", "${addedMessages!!.message}")
                if (addedMessages?.fromMessage.equals(orderFrom)) {
                    adapter.add(ChatFromItem(addedMessages?.message))
                } else {
                    adapter.add(ChatToItem(addedMessages?.message))
                }
                recycler_view_chat.scrollToPosition(adapter.itemCount-1)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}



class ChatFromItem(val text: String?): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_from_message.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_from_row

    }
}

class ChatToItem(val text: String?): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_to_message.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_to_row

    }
}
