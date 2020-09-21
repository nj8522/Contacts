package com.nash.contactsapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactAppDb(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        final const val DATABASE_NAME = "ContactApp.db"
        final const val DATABASE_VER = 1
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ContactAppContract.CONTACTS_APP_CREATE_ENTRY)
        //db?.execSQL(ContactAppContract.CONTACTS_APP_CREATE_DATA_ENTRY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(ContactAppContract.CONTACTS_APP_DELETE_ENTRY)
        //db?.execSQL(ContactAppContract.CONTACTS_APP_DELETE_DATA_ENTRY)
        onCreate(db)
    }


}