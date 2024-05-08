package com.example.mytodo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// this is the enum class that will be used to store the importance of the tasks
//This is the data class that will be used to store the tasks
@Parcelize //@Parcelize is an annotation that allows you to make your class Parcelable
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    var checked: Boolean,
    val category: String,

) : Parcelable// Parcelable is an interface that allows you to pass data between activities
// and fragments