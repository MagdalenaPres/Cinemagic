package com.example.cinemagic.Movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Movie(
    var id: String, var title: String, var type: String,
    var duration: String, var description: String, var premiere: String,
    var video: String, var mainPhoto: String, var hours: Map<String, String>, var images: Array<String>
): Parcelable