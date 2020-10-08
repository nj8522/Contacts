package com.nash.contactsapp.contactdata

import com.nash.contactsapp.ui.ContactActivity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock



internal class RetrieveContactDataTest {


    private val retrieveContactData = RetrieveContactData()
    private val contactActivityMock = mock(ContactActivity::class.java)


    @Test
    fun checkingContactData() {
        val mockList = retrieveContactData.getContactDetails(contactActivityMock)
        assertFalse(mockList.size > 0)
    }

 }