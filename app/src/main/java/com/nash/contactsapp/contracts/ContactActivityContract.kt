package com.nash.contactsapp.contracts

import android.content.Context
import com.nash.contactsapp.uidatamodel.ContactModel

interface ContactActivityContract {

   interface ContactView {
       fun initView()
       fun updateView()
   }

    interface ContactPresenter {

        fun getProviderData()
        fun updateContact() : MutableList<com.nash.contactsapp.uidatamodel.ContactModel>
    }

    interface ContactModel {

        fun generateContacts(context: Context)
        fun insertContactsToDb()
    }

}