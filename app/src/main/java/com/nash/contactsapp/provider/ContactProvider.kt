package com.nash.contactsapp.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.nash.contactsapp.database.ContactAppContract
import com.nash.contactsapp.database.ContactAppDb
import java.net.URI
import android.database.sqlite.SQLiteDatabase as SQLiteDatabase

class ContactProvider : ContentProvider() {

    lateinit var db : SQLiteDatabase

    companion object {
        final  const val AUTHORITY = "com.app.contact.provider"
        val CONTENT_URI = Uri.parse("content://${AUTHORITY}")!!
        val uriMatcher : UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    private val CONTACT = 1
    private val CONTACT_ID = 2


    init {
       uriMatcher.addURI(AUTHORITY, ContactAppContract.ContactAppEntry.TABLE_NAME, CONTACT)
       uriMatcher.addURI(AUTHORITY, "${ContactAppContract.ContactAppEntry.TABLE_NAME}/#", CONTACT_ID)
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val row = db.delete(ContactAppContract.ContactAppEntry.TABLE_NAME, selection, null)
        context!!.contentResolver.notifyChange(uri, null)
        return row
    }

    override fun getType(uri: Uri): String? {
        TODO("Implement this to handle requests for the MIME type of the data" + "at the given URI")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val row = db.insert(ContactAppContract.ContactAppEntry.TABLE_NAME, null, values)
        ContentUris.withAppendedId(CONTENT_URI, row)
        context!!.contentResolver.notifyChange(uri, null)
        return uri
    }

    override fun onCreate(): Boolean {

        val contactAppDb = ContactAppDb(context!!)
        db = contactAppDb.writableDatabase
        return db != null
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        val myQuery = SQLiteQueryBuilder()
        myQuery.tables = ContactAppContract.ContactAppEntry.TABLE_NAME

        val cursor : Cursor = myQuery.query(db, null, null, null, null, null, sortOrder+" COLLATE LOCALIZED ASC")
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return  cursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {

        val row = db.update(ContactAppContract.ContactAppEntry.TABLE_NAME, values, selection, null)
        context!!.contentResolver.notifyChange(uri, null)
        return row
    }


}
