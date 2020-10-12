package com.nash.contactsapp.presenters

import android.content.Context
import android.util.Log
import com.nash.contactsapp.contactdata.ConvertContactToObjects
import com.nash.contactsapp.contracts.ContactActivityContract
import com.nash.contactsapp.database.DataFromProvider
import com.nash.contactsapp.models.RetrieveContactDataFromProvider
import com.nash.contactsapp.provider.ContactProvider
import com.nash.contactsapp.ui.ContactActivity
import com.nash.contactsapp.uidatamodel.ContactModel

class ContactActivityPresenter (var context: Context) : ContactActivityContract.ContactPresenter {


    private val dataFromProvider : ContactActivityContract.DataFromProvider = RetrieveContactDataFromProvider()
    private val insertIntoDb  : ContactActivityContract.InsertIntoDb = DataFromProvider()
    private val localDb : ContactActivityContract.LocalDb = ConvertContactToObjects()
    private val CONTACT_URI = ContactProvider.CONTENT_URI

    /*init {
        contactView.initView()
    }*/

    override fun getProviderData() {
        presenterLog("Fetch Contacts")
        val contactList = dataFromProvider.generateContacts(context)
        insertIntoDb.dataFromTheProvider(context, contactList)
        presenterLog("Fetch Done")

    }

    override fun updateContact() = localDb.getContactsFromLocalDb(context)




    override fun checkIfDbIsEmpty(): Boolean {

        val cursor = context.contentResolver.query(
            CONTACT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor!!.count > 0) { return true }
        return false

    }

    override fun presenterLog(message : String) {
        Log.i("mvp", message)
    }

}