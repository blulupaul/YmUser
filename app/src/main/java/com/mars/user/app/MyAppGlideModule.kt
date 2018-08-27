package com.mars.user.app

import android.os.Parcel
import android.os.Parcelable
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * Created by gu on 2018/07/20
 * desc: ${desc}
 */
@GlideModule
class MyAppGlideModule() : AppGlideModule(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyAppGlideModule> {
        override fun createFromParcel(parcel: Parcel): MyAppGlideModule {
            return MyAppGlideModule(parcel)
        }

        override fun newArray(size: Int): Array<MyAppGlideModule?> {
            return arrayOfNulls(size)
        }
    }
}