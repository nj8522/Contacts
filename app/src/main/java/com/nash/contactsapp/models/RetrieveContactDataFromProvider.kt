package com.nash.contactsapp.models


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import android.util.Log
import com.nash.contactsapp.contracts.ContactActivityContract
import com.nash.contactsapp.database.DataFromProvider
import com.nash.contactsapp.uidatamodel.ContactModel
import java.io.File
import java.io.FileOutputStream

class RetrieveContactDataFromProvider : ContactActivityContract.ContactModel {

    //Data
    var contactDataId : String? = null
    var dataSelection : String? = null

    var context : Context? = null

    //Contact List
    private val contactDataList : MutableList<ContactModel> = mutableListOf()


   private fun getContactDetails(context: Context) {

       //Uri
        val contactUri = ContactsContract.Contacts.CONTENT_URI

        //Contact Sort Order
        val sortContactInAscending = ContactsContract.Contacts.DISPLAY_NAME+" COLLATE LOCALIZED ASC"

        //Contact Cursor Query
       var cursorContact = context.contentResolver.query(
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

                        //Mobile
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)) == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data2"))) {

                              ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ->
                                  contactModel.phoneNumber["Mobile"] = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))


                              ContactsContract.CommonDataKinds.Phone.TYPE_HOME ->
                                  contactModel.phoneNumber["Home"] = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))


                              ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                                  contactModel.phoneNumber["Work"] = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                          }

                      }


                      //Email
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)) == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data2"))) {

                              ContactsContract.CommonDataKinds.Email.TYPE_HOME ->
                                  contactModel.emailId["Home"] = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))


                              ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                                  contactModel.emailId["Work"] = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"))
                          }
                      }



                      //Organization
                      if(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MIMETYPE)) == ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE) {

                          when (dataCursor.getInt(dataCursor.getColumnIndexOrThrow("data1"))) {

                              ContactsContract.CommonDataKinds.Email.TYPE_HOME ->
                                  contactModel.organization = dataCursor.getString(dataCursor.getColumnIndexOrThrow("data4"))
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

                          val imagePath = tempFile.path
                          contactModel.contactImage = imagePath
                        }

                      }

                    }  while (dataCursor.moveToNext())

                }

                contactDataList.add(contactModel)

            } while (cursorContact.moveToNext())

        }

        cursorContact.close()

   }


    override fun generateContacts(context: Context) {
        getContactDetails(context)
        this.context = context
        Log.i("mvp", "generating Contacts")
    }


    override fun insertContactsToDb() {
        val dataFromProvider = DataFromProvider()
        dataFromProvider.convertObjectToData(context!!, contactDataList)
    }

}


