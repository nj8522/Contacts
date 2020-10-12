package com.nash.contactsapp.contactdata

import com.nash.contactsapp.models.RetrieveContactDataFromProvider
import com.nash.contactsapp.ui.ContactActivity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock



internal class RetrieveContactDataTest {


    private val retrieveContactData = RetrieveContactDataFromProvider()
    private val contactActivityMock = mock(ContactActivity::class.java)


    @Test
    fun checkingContactData() {
         val listData = retrieveContactData.getContactDetails(contactActivityMock)
         assertFalse(listData.size > 0)
    }

 }