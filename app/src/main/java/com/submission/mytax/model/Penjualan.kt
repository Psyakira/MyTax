package com.submission.mytax.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class Penjualan(
    val namaPembeli: String?,
    val npwpPembeli: String?,
    val faktur: String?,
    val tanggal: String?,
    val total: String?,
    val ppn: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaPembeli)
        parcel.writeString(npwpPembeli)
        parcel.writeString(faktur)
        parcel.writeString(tanggal)
        parcel.writeString(total)
        parcel.writeString(ppn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Penjualan> {
        override fun createFromParcel(parcel: Parcel): Penjualan {
            return Penjualan(parcel)
        }

        override fun newArray(size: Int): Array<Penjualan?> {
            return arrayOfNulls(size)
        }

        fun fromJson(json: String?): Penjualan? {
            if (json.isNullOrEmpty()) return null
            val jsonObject = JSONObject(json)
            return Penjualan(
                jsonObject.optString("namaPembeli", null),
                jsonObject.optString("npwpPembeli", null),
                jsonObject.optString("faktur", null),
                jsonObject.optString("tanggal", null),
                jsonObject.optString("total", null),
                jsonObject.optString("ppn", null)
            )
        }

        fun toJson(penjualan: Penjualan): String {
            val jsonObject = JSONObject()
            jsonObject.put("namaPembeli", penjualan.namaPembeli)
            jsonObject.put("npwpPembeli", penjualan.npwpPembeli)
            jsonObject.put("faktur", penjualan.faktur)
            jsonObject.put("tanggal", penjualan.tanggal)
            jsonObject.put("total", penjualan.total)
            jsonObject.put("ppn", penjualan.ppn)
            return jsonObject.toString()
        }
    }
}
