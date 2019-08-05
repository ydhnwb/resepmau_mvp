package com.ydhnwb.resepmaumvp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ydhnwb.resepmaumvp.contracts.MainActivityContract
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(@SerializedName("id") var id : Int?, @SerializedName("title") var title : String?, @SerializedName("content") var content : String?) : Parcelable{
    constructor() : this(null, null, null)
}