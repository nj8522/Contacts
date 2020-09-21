package com.nash.contactsapp.contactdata

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import android.util.Log
import com.nash.contactsapp.database.ContactAppContract
import com.nash.contactsapp.model.ContactModel
import com.nash.contactsapp.provider.ContactProvider
import java.io.File
import java.io.FileOutputStream

class RetrieveContactData {

    //Data
    var contactDataId : String? = null
    var dataSelection : String? = null

    //Data Base Values
    /*val CONTACTS_ID = "_ID"
    val CONTACTS_NAME = "CONTACT_NAME"
    val CONTACTS_IMAGE = "CONTACT_PHOTO"
    val PHONE_MOBILE = "PHONE_MOBILE"
    val PHONE_WORK = "PHONE_WORK"
    val PHONE_HOME = "PHONE_HOME"
    val EMAIL_HOME = "EMAIL_HOME"
    val EMAIL_WORK = "EMAIL_WORK"
    val ORGANIZATION_HOME = "ORGANIZATION_HOME"*/


    fun getContactDetails(context: Context) : MutableList<ContactModel>{



         val contactDataList : MutableList<ContactModel> = mutableListOf()

        //Content Resolver
        /*val resolver = context.contentResolver
        val contentValue = ContentValues()*/

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

                //var name: String

                val contactModel = ContactModel()


                contactDataId = cursorContact.getString(cursorContact.getColumnIndexOrThrow("_ID"))

                val dataUri = ContactsContract.Data.CONTENT_URI
                dataSelection = "${ContactsContract.Data.CONTACT_ID} = $contactDataId"
                val  dataCursor = context.contentResolver.query(dataUri, null, dataSelection, null, null)

                if(dataCursor!!.moveToFirst()) {

                    contactModel.displayName  = dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                    //contentValue.put(CONTACTS_NAME, name)

                    do {

                        var phoneMobile : String
                        var phoneHome : String
                        var phoneWork : String
                        var emailHome : String
                        var emailWork : String
                        var orgHome   : String
                        var imagePath : String


                      //Mobile
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)) == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data2"))) {

                              ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ->
                              {   phoneMobile = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  //contentValue.put(PHONE_MOBILE, phoneMobile)
                                  contactModel.phoneNumber["Mobile"] = phoneMobile
                              }

                              ContactsContract.CommonDataKinds.Phone.TYPE_HOME ->
                              {   phoneHome = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  //contentValue.put(PHONE_HOME, phoneHome)
                                  contactModel.phoneNumber["Home"] = phoneHome
                              }

                              ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                              {   phoneWork = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  //contentValue.put(PHONE_WORK, phoneWork)
                                  contactModel.phoneNumber["Work"] = phoneWork
                              }
                          }

                      }


                      //Email
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)) == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data2"))) {

                              ContactsContract.CommonDataKinds.Email.TYPE_HOME ->
                              {   emailHome = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  //contentValue.put(EMAIL_HOME, emailHome)
                                  contactModel.emailId["Home"] = emailHome
                              }

                              ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                              {   emailWork = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                                  //contentValue.put(EMAIL_WORK, emailWork)
                                  contactModel.emailId["Work"] = emailWork
                              }
                          }
                      }



                      //Organization
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)) == ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data1"))) {

                              ContactsContract.CommonDataKinds.Email.TYPE_HOME ->
                              {   orgHome = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data4"))
                                  //contentValue.put(ORGANIZATION_HOME, orgHome)
                                  contactModel.organization = orgHome
                              }
                          }
                      }


                      //Photo
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)) == ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE) {
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

                          imagePath = tempFile.path
                          //contentValue.put(CONTACTS_IMAGE, imagePath)
                          contactModel.contactImage = imagePath
                       }
                      }


                  }  while (dataCursor.moveToNext())

                }

                /*val result = resolver.insert(ContactProvider.CONTENT_URI, contentValue)
                Log.i("uri", result.toString())*/

                contactDataList.add(contactModel)

            } while (cursorContact.moveToNext())

        }

        cursorContact.close()

        return contactDataList
    }

 }


