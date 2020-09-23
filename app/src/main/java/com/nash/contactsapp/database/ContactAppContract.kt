package com.nash.contactsapp.database

import android.provider.BaseColumns

object ContactAppContract {

    const val CONTACTS_APP_CREATE_ENTRY = "CREATE TABLE ${ContactAppEntry.TABLE_NAME} (" +

            "${ContactAppEntry.CONTACTS_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${ContactAppEntry.CONTACTS_NAME} TEXT," +
            "${ContactAppEntry.CONTACTS_IMAGE} TEXT,"+
            "${ContactAppEntry.PHONE_MOBILE} TEXT,"+
            "${ContactAppEntry.PHONE_WORK} TEXT,"+
            "${ContactAppEntry.PHONE_CUSTOM} TEXT,"+
            "${ContactAppEntry.PHONE_CUSTOM_TaG} TEXT,"+
            "${ContactAppEntry.ADDRESS_HOME} TEXT,"+
            "${ContactAppEntry.ADDRESS_WORK} TEXT,"+
            "${ContactAppEntry.ADDRESS_CUSTOM} TEXT,"+
            "${ContactAppEntry.ADDRESS_TAG} TEXT,"+
            "${ContactAppEntry.EMAIL_HOME} TEXT," +
            "${ContactAppEntry.EMAIL_WORK} TEXT," +
            "${ContactAppEntry.EMAIL_CUSTOM} TEXT," +
            "${ContactAppEntry.EMAIL_TAG} TEXT," +
            "${ContactAppEntry.ORGANIZATION_HOME} TEXT)"


    const val CONTACTS_APP_DELETE_ENTRY = "DROP TABLE IF EXISTS ${ContactAppEntry.TABLE_NAME}"




    object ContactAppEntry : BaseColumns {

        const val TABLE_NAME = "Contacts"
        const val CONTACTS_ID = "_ID"
        const val CONTACTS_NAME = "CONTACT_NAME"
        const val CONTACTS_IMAGE = "CONTACT_PHOTO"
        const val PHONE_MOBILE = "PHONE_MOBILE"
        const val PHONE_WORK = "PHONE_WORK"
        const val PHONE_CUSTOM = "PHONE_CUSTOM"
        const val PHONE_CUSTOM_TaG = "PHONE_TAG"
        const val ADDRESS_WORK = "ADDRESS_WORK"
        const val ADDRESS_HOME = "ADDRESS_HOME"
        const val ADDRESS_CUSTOM = "ADDRESS_CUSTOM"
        const val ADDRESS_TAG = "ADDRESS_TAG"
        const val EMAIL_HOME = "EMAIL_HOME"
        const val EMAIL_WORK = "EMAIL_WORK"
        const val EMAIL_CUSTOM = "EMAIL_CUSTOM"
        const val EMAIL_TAG = "EMAIL_TAG"
        const val ORGANIZATION_HOME = "ORGANIZATION_HOME"
    }


}