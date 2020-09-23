package com.nash.contactsapp.model

import java.io.Serializable
import java.io.StringReader


class ContactModel {

    var id : Int = 0
    var displayName : String? = null
    var contactImage : String? = null

    var phoneNumber : MutableMap<String, String>  = mutableMapOf()
    var customPhoneTag : String? = null

    var emailId : MutableMap<String, String> = mutableMapOf()
    var customEmailTag : String? = null

    var address : MutableMap<String, String> = mutableMapOf()
    var customAddressTag : String? = null


    /*var addressHouseNumber : String? = null
    var addressLineTwo : String? = null
    var addressCity : String? = null
    var addressState : String? = null
    var addressCountry : String? = null
    var addressPincode : String? = null*/

    var organization : String? = null

}


  data class AddressDetail(
      var addressHouseNumber : String? = null,
      var addressLineTwo : String? = null,
      var addressCity : String? = null,
      var addressState : String? = null,
      var addressCountry : String? = null,
      var addressPincode : String? = null
      ) : Serializable