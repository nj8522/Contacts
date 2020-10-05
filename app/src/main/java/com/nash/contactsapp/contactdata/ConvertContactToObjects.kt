package com.nash.contactsapp.contactdata

import android.database.Cursor
import android.util.Log
import com.nash.contactsapp.database.ContactAppContract
import com.nash.contactsapp.model.ContactModel

class ConvertContactToObjects {

    private val objectList : MutableList<ContactModel> = mutableListOf()

    fun changeDataToModelObjects (cursor : Cursor) : MutableList<ContactModel> {

        if(cursor.moveToFirst()) {

            do {

                val contactModel = ContactModel()
                val contactId = cursor.getInt(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.CONTACTS_ID))

                //ID
                contactModel.id = contactId


                contactModel.displayName = cursor.getString(cursor.getColumnIndexOrThrow(
                    ContactAppContract.ContactAppEntry.CONTACTS_NAME))

                if(cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.CONTACTS_IMAGE)) != null ) {

                    contactModel.contactImage = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactAppContract.ContactAppEntry.CONTACTS_IMAGE))
                }

                //Mobile
                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_MOBILE)) != null){
                    contactModel.phoneNumber["Mobile"] = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.PHONE_MOBILE))
                    Log.i("data", contactModel.phoneNumber["Mobile"].toString())
                }

                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_WORK)) != null){
                    contactModel.phoneNumber["Work"] = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.PHONE_WORK))
                    Log.i("data", contactModel.phoneNumber["Work"].toString())
                }

                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_CUSTOM)) != null){
                    contactModel.phoneNumber["Home"] = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.PHONE_CUSTOM))
                    contactModel.customPhoneTag = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.PHONE_CUSTOM_TaG))

                }

                //Address

                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_HOME)) != null) {
                    contactModel.address["Home"] = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.ADDRESS_HOME))
                }

                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_WORK)) != null) {
                    contactModel.address["Work"] = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.ADDRESS_WORK))
                }

                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_CUSTOM)) != null) {
                    contactModel.customAddressTag = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.ADDRESS_TAG))
                    contactModel.address["Custom"] = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.ADDRESS_CUSTOM))
                }


                //Email
                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.EMAIL_WORK)) != null){
                    contactModel.emailId["Work"] = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactAppContract.ContactAppEntry.EMAIL_WORK))
                }

                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.EMAIL_HOME)) != null){
                    contactModel.emailId["Home"] = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactAppContract.ContactAppEntry.EMAIL_HOME))
                }

                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.EMAIL_CUSTOM)) != null){
                    contactModel.customEmailTag = cursor.getString(cursor.getColumnIndex(
                        ContactAppContract.ContactAppEntry.EMAIL_TAG))
                    contactModel.emailId["Custom"] = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactAppContract.ContactAppEntry.EMAIL_CUSTOM))
                }


                //Org
                if(cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ORGANIZATION_HOME)) != null){
                    contactModel.organization = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactAppContract.ContactAppEntry.ORGANIZATION_HOME))
                }


                objectList.add(contactModel)

            }  while (cursor.moveToNext())

        }

        return objectList
    }


}