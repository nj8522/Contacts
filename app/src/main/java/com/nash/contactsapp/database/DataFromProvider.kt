package com.nash.contactsapp.database

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.nash.contactsapp.contactdata.RetrieveContactData
import com.nash.contactsapp.model.ContactModel
import com.nash.contactsapp.provider.ContactProvider

class DataFromProvider {


    val contentValue = ContentValues()



    val CONTACTS_NAME = "CONTACT_NAME"
    val CONTACTS_IMAGE = "CONTACT_PHOTO"
    val PHONE_MOBILE = "PHONE_MOBILE"
    val PHONE_WORK = "PHONE_WORK"
    val PHONE_CUSTOM = "PHONE_CUSTOM"
    val PHONE_CUSTOM_TaG = "PHONE_TAG"
    val ADDRESS_WORK = "ADDRESS_WORK"
    val ADDRESS_HOME = "ADDRESS_HOME"
    val ADDRESS_CUSTOM = "ADDRESS_CUSTOM"
    val ADDRESS_TAG = "ADDRESS_TAG"
    val EMAIL_HOME = "EMAIL_HOME"
    val EMAIL_WORK = "EMAIL_WORK"
    val EMAIL_CUSTOM = "EMAIL_CUSTOM"
    val EMAIL_TAG = "EMAIL_TAG"
    val ORGANIZATION_HOME = "ORGANIZATION_HOME"

    fun convertObjectToData(context : Context, contactList : MutableList<ContactModel>) {

        //var contactList  = contactListFromMain

        //contactList = retrieveContactData.getContactDetails(context)

        val resolver = context.contentResolver

        contactList.forEach { data ->

            if(data.displayName != null) {
                contentValue.put(CONTACTS_NAME, data.displayName)
            }

            if(data.contactImage != null || data.contactImage != "") {
                contentValue.put(CONTACTS_IMAGE, data.contactImage)
            }

            if(data.phoneNumber["Mobile"] != null || data.phoneNumber["Mobile"] != "") {
                contentValue.put(PHONE_MOBILE, data.phoneNumber["Mobile"])
            }

            if(data.phoneNumber["Work"] != null || data.phoneNumber["Work"] != "") {
                contentValue.put(PHONE_WORK, data.phoneNumber["Work"])
            }

            if(data.phoneNumber["Home"] != null || data.phoneNumber["Home"] != "") {
                contentValue.put(PHONE_CUSTOM, data.phoneNumber["Home"])
            }

            if(data.emailId["Home"] != null || data.phoneNumber["Home"] != "") {
                contentValue.put(EMAIL_HOME, data.emailId["Home"])
            }

            if(data.emailId["Work"] != null || data.phoneNumber["Work"] != "") {
                contentValue.put(EMAIL_WORK, data.emailId["Work"])
            }

            if(data.organization != null) {
                contentValue.put(ORGANIZATION_HOME, data.organization)
            }

            resolver.insert(ContactProvider.CONTENT_URI, contentValue)
        }

           Toast.makeText(context, "Contact Added Successfully", Toast.LENGTH_SHORT).show()

    }




}