package com.nash.contactsapp.validate

import androidx.core.text.isDigitsOnly
import java.lang.NumberFormatException
import java.util.regex.Pattern

class ValidateContact {

    fun checkContactName(name : String) : Boolean {

        if(!name.isNullOrBlank()) {
            return true
        }
        return false
    }

    fun checkContactPhoneNumber(number : String) : Boolean {

        try {
            number.toInt()

        } catch (e : NumberFormatException){
            return false
        }

        return  true
    }

    fun checkContactLabel(tag : String) : Boolean{

        if(tag.equals("Label")) {
            return false
        }
        return true
    }

   fun checkContactEmail(mail : String) : Boolean {

       val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
       val pattern = Pattern.compile(emailPattern)
       return pattern.matcher(mail).matches()
   }

   fun checkContactOrganization(organization : String) : Boolean {
       if(!organization.isNullOrBlank()) {
           return true
       }
       return false
   }


    fun checkUserAddress(address : String) : Boolean {
        if(!address.isNullOrBlank()) {
            return true
        }
        return false
    }

}