package com.nash.contactsapp.database

import android.provider.BaseColumns

object ContactAppContract {

    const val CONTACTS_APP_CREATE_ENTRY = "CREATE TABLE ${ContactAppEntry.TABLE_NAME} (" +
            "${ContactAppEntry.CONTACTS_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${ContactAppEntry.CONTACTS_NAME} TEXT," +
            "${ContactAppEntry.CONTACTS_IMAGE} TEXT,"+
            "${ContactAppEntry.PHONE_MOBILE} TEXT,"+
            "${ContactAppEntry.PHONE_WORK} TEXT,"+
            "${ContactAppEntry.PHONE_HOME} TEXT,"+
            "${ContactAppEntry.EMAIL_HOME} TEXT," +
            "${ContactAppEntry.EMAIL_WORK} TEXT," +
            "${ContactAppEntry.ORGANIZATION_HOME} TEXT)"

   /* const val CONTACTS_APP_CREATE_DATA_ENTRY = "CREATE TABLE ${ContactDataEntry.TABLE_NAME} (" +
            "${ContactDataEntry.DATA_ID} INTEGER PRIMARY KEY," +
            "${ContactDataEntry.CONTACTS_IMAGE} TEXT," +
            "${ContactDataEntry.PHONE_MOBILE} TEXT," +
            "${ContactDataEntry.PHONE_WORK} TEXT," +
            "${ContactDataEntry.PHONE_HOME} TEXT," +
            "${ContactDataEntry.EMAIL_HOME} TEXT," +
            "${ContactDataEntry.EMAIL_WORK} TEXT," +
            "${ContactDataEntry.ORGANIZATION_HOME} TEXT)"*/



    const val CONTACTS_APP_DELETE_ENTRY = "DROP TABLE IF EXISTS ${ContactAppEntry.TABLE_NAME}"

    //const val CONTACTS_APP_DELETE_DATA_ENTRY = "DROP TABLE IF EXISTS ${ContactDataEntry.TABLE_NAME}"


    object ContactAppEntry : BaseColumns {

        const val TABLE_NAME = "Contacts"
        const val CONTACTS_ID = "_ID"
        const val CONTACTS_NAME = "CONTACT_NAME"
        const val CONTACTS_IMAGE = "CONTACT_PHOTO"
        const val PHONE_MOBILE = "PHONE_MOBILE"
        const val PHONE_WORK = "PHONE_WORK"
        const val PHONE_HOME = "PHONE_HOME"
        const val EMAIL_HOME = "EMAIL_HOME"
        const val EMAIL_WORK = "EMAIL_WORK"
        const val ORGANIZATION_HOME = "ORGANIZATION_HOME"
    }

   /* object ContactDataEntry : BaseColumns {

        const val TABLE_NAME = "DATA"
        const val DATA_ID = "_ID"
        const val CONTACTS_IMAGE = "CONTACT_PHOTO"
        const val PHONE_MOBILE = "PHONE_MOBILE"
        const val PHONE_WORK = "PHONE_WORK"
        const val PHONE_HOME = "PHONE_HOME"
        const val EMAIL_HOME = "EMAIL_HOME"
        const val EMAIL_WORK = "EMAIL_WORK"
        const val ORGANIZATION_HOME = "ORGANIZATION_HOME"
    }*/



}