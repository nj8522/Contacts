package com.nash.contactsapp.presenters

import android.content.Context
import android.util.Log
import com.nash.contactsapp.contactdata.ConvertContactToObjects
import com.nash.contactsapp.contracts.ContactActivityContract
import com.nash.contactsapp.models.RetrieveContactDataFromProvider
import com.nash.contactsapp.uidatamodel.ContactModel

class ContactActivityPresenter (_view : ContactActivityContract.ContactView, var context: Context) : ContactActivityContract.ContactPresenter {

    private val contactView : ContactActivityContract.ContactView  = _view
    private val contactModel : ContactActivityContract.ContactModel = RetrieveContactDataFromProvider()

    init {
        contactView.initView()
    }

    override fun getProviderData() {
        Log.i("mvp", "Got to generating Contacts")
        contactModel.generateContacts(context)
        Log.i("mvp", "Done generating Contacts")
        contactModel.insertContactsToDb()
        contactView.updateView()
    }

    override fun updateContact(): MutableList<ContactModel> {
        val getContactList = ConvertContactToObjects()
        Log.i("mvp", "From DB")
        return getContactList.changeDataToModelObjects(context)
    }
}