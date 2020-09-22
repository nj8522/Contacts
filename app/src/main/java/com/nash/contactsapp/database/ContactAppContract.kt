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
            "${ContactAppEntry.ADDRESS_HOUSE_NO} TEXT,"+
            "${ContactAppEntry.ADDRESS_LINE_TWO} TEXT,"+
            "${ContactAppEntry.ADDRESS_CITY} TEXT,"+
            "${ContactAppEntry.ADDRESS_STATE} TEXT,"+
            "${ContactAppEntry.ADDRESS_COUNTRY} TEXT,"+
            "${ContactAppEntry.ADDRESS_PINCODE} TEXT,"+
            "${ContactAppEntry.EMAIL_HOME} TEXT," +
            "${ContactAppEntry.EMAIL_WORK} TEXT," +
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
        const val PHONE_CUSTOM_TaG = "TAG"
        const val ADDRESS_HOUSE_NO = "ADDRESS_STREET"
        const val ADDRESS_LINE_TWO = "ADDRESS_LINE_TWO"
        const val ADDRESS_CITY = "ADDRESS_CITY"
        const val ADDRESS_STATE = "ADDRESS_STATE"
        const val ADDRESS_COUNTRY = "ADDRESS_COUNTRY"
        const val ADDRESS_PINCODE = "ADDRESS_PINCODE"
        const val EMAIL_HOME = "EMAIL_HOME"
        const val EMAIL_WORK = "EMAIL_WORK"
        const val ORGANIZATION_HOME = "ORGANIZATION_HOME"
    }


}