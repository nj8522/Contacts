package com.nash.contactsapp.model


class ContactModel {

    var id : Int = 0
    var displayName : String? = null
    var contactImage : String? = null
    var phoneNumber : MutableMap<String, String>  = mutableMapOf()
    var emailId : MutableMap<String, String> = mutableMapOf()
    var organization : String? = null


}