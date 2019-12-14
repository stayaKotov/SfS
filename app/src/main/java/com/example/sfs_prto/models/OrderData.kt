package com.example.sfs_prto.models

//import android.os.Parcelable
//import kotlinx.android.parcel.Parcelize

//@Parcelize
class OrderData (
    var id: String?, var title: String, var description: String, var from:String,
    var to:String, var price:String
) {
    constructor(): this("","","","","","")

}