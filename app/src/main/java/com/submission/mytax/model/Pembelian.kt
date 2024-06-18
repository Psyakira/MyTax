package com.submission.mytax.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class Pembelian(
    val namaPenjual: String?,
    val npwpPenjual: String?,
    val noFaktur: String?,
    val tanggal: String?,
    val total: Double?,
    val ppnMasukan: Double = 0.0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaPenjual)
        parcel.writeString(npwpPenjual)
        parcel.writeString(noFaktur)
        parcel.writeString(tanggal)
        parcel.writeValue(total)
        parcel.writeDouble(ppnMasukan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pembelian> {
        override fun createFromParcel(parcel: Parcel): Pembelian {
            return Pembelian(parcel)
        }

        override fun newArray(size: Int): Array<Pembelian?> {
            return arrayOfNulls(size)
        }

        fun fromJson(json: String?): Pembelian? {
            if (json.isNullOrEmpty()) return null
            val jsonObject = JSONObject(json)
            return Pembelian(
                jsonObject.optString("namaPenjual", null),
                jsonObject.optString("npwpPenjual", null),
                jsonObject.optString("noFaktur", null),
                jsonObject.optString("tanggalPembelian", null),
                jsonObject.optDouble("totalPembelian", 0.0),
                jsonObject.optDouble("ppnMasukan", 0.0)
            )
        }

        fun toJson(pembelian: Pembelian): String {
            val jsonObject = JSONObject()
            jsonObject.put("namaPenjual", pembelian.namaPenjual)
            jsonObject.put("npwpPenjual", pembelian.npwpPenjual)
            jsonObject.put("noFaktur", pembelian.noFaktur)
            jsonObject.put("tanggalPembelian", pembelian.tanggal)
            jsonObject.put("totalPembelian", pembelian.total)
            jsonObject.put("ppnKeluaran", pembelian.ppnMasukan)
            return jsonObject.toString()
        }
    }
}
