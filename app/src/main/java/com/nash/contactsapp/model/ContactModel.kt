package com.nash.contactsapp.model

import java.io.Serializable


class ContactModel {

    var id : Int = 0
    var displayName : String? = null
    var contactImage : String? = null
    var customPhoneTag : String? = null
    var phoneNumber : MutableMap<String, String>  = mutableMapOf()
    var emailId : MutableMap<String, String> = mutableMapOf()

    var address : MutableMap<String, AddressDetail> = mutableMapOf()

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