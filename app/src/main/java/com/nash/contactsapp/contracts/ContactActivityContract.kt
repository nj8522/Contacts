package com.nash.contactsapp.contracts

import android.content.Context
import com.nash.contactsapp.uidatamodel.ContactModel


interface ContactActivityContract {


    interface ContactPresenter {

        fun getProviderData()
        fun updateContact() : MutableList<ContactModel>
        fun checkIfDbIsEmpty() : Boolean
        fun presenterLog(message : String)
    }

    interface DataFromProvider {
        fun generateContacts(context: Context): MutableList<ContactModel>
    }

    interface InsertIntoDb {
        fun dataFromTheProvider(context: Context, contactList : MutableList<ContactModel>)
    }

    interface LocalDb {
        fun getContactsFromLocalDb(context: Context) : MutableList<ContactModel>
    }


}