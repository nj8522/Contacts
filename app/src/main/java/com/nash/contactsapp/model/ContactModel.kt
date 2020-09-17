package com.nash.contactsapp.model

import android.graphics.Bitmap
import java.io.File

class ContactModel {

    var displayName : String = ""
    var phoneNumber : MutableMap<String, String>  = mutableMapOf()
    var emailId : MutableMap<String, String> = mutableMapOf()
    var organization : MutableMap<String, String> = mutableMapOf()
    var contactImage : String? = null

}