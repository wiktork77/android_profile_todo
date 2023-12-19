package com.example.android4

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class PhotoItem(
    val name: String,
    val uri: Uri
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readParcelable(Uri::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(uri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItem> {
        override fun createFromParcel(parcel: Parcel): PhotoItem {
            return PhotoItem(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItem?> {
            return arrayOfNulls(size)
        }
    }
}