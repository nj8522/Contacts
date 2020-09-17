package com.nash.contactsapp.contactdata

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import com.nash.contactsapp.model.ContactModel
import java.io.File
import java.io.FileOutputStream

class RetrieveContactData {

    //Data
    var contactDataId : String? = null
    var dataSelection : String? = null


    fun getContactDetails(context: Context): MutableList<ContactModel> {


        //List to Contact Data
        val providerContactList: MutableList<ContactModel> = mutableListOf()

        //Uri
        val contactUri = ContactsContract.Contacts.CONTENT_URI

        //Contact Sort Order
        val sortContactInAscending = ContactsContract.Contacts.DISPLAY_NAME+" COLLATE LOCALIZED ASC"

        //Contact Cursor Query
        val cursorContact = context.contentResolver.query(
            contactUri,
            null,
            null,
            null,
            sortContactInAscending
        )


        //Contact Data Variables
        val MIMETYPE  = "MIMETYPE"


        if(cursorContact!!.moveToFirst()) {

            do {
                //Model Data Class
                val contactModel = ContactModel()

                contactDataId = cursorContact.getString(cursorContact.getColumnIndexOrThrow("_ID"))

                val dataUri = ContactsContract.Data.CONTENT_URI
                dataSelection = "${ContactsContract.Data.CONTACT_ID} = $contactDataId"
                val  dataCursor = context.contentResolver.query(dataUri, null, dataSelection, null, null)

                if(dataCursor!!.moveToFirst()) {

                    contactModel.displayName = dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))

                    do {

                      //Mobile
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data2"))) {

                              ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ->
                              {   val mobile = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  contactModel.phoneNumber["Mobile"] = mobile
                              }

                              ContactsContract.CommonDataKinds.Phone.TYPE_HOME ->
                              {   val mobile = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  contactModel.phoneNumber["Home"] = mobile
                              }

                              ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                              {   val mobile = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  contactModel.phoneNumber["Work"] = mobile
                              }
                          }

                      }


                      //Email
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)).equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data2"))) {

                              ContactsContract.CommonDataKinds.Email.TYPE_HOME ->
                              {   val email = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  contactModel.emailId["Home"] = email
                              }

                              ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                              {   val email = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  contactModel.emailId["Work"] = email
                              }
                          }
                      }



                      //Organization
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)).equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data1"))) {

                              ContactsContract.CommonDataKinds.Email.TYPE_HOME ->
                              {   val company = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data4"))
                                  contactModel.organization["Home"] = company
                              }
                          }
                      }


                      //Photo
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)).equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                      val photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"))

                      if(photoByte != null){

                          val bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.size)
                          val cacheDir = context.cacheDir
                          val tempFile = File(cacheDir.path +"/contactApp $contactDataId.png")
                          try {

                              val fileOutPutStream = FileOutputStream(tempFile)
                              bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutPutStream)
                              fileOutPutStream.flush()
                              fileOutPutStream.close()

                          }  catch (e : Exception) {
                              e.printStackTrace()
                          }
                            contactModel.contactImage = tempFile.path
                       }
                      }


                  }  while (dataCursor.moveToNext())

                }

                providerContactList.add(contactModel)

            } while (cursorContact.moveToNext())


        }

        cursorContact.close()

        return providerContactList
    }

 }


