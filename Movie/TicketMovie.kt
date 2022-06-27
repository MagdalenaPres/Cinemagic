package com.example.cinemagic.Movie

import android.os.Parcelable
import android.text.Editable
import android.widget.EditText
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TicketMovie(
    var id: String, var title: String, var mainPhoto: String, var date: String , var hour: String, var seat: String
): Parcelable
