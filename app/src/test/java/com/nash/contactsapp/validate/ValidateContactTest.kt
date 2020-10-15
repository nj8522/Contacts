package com.nash.contactsapp.validate

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ValidateContactTest {


    private val validateContact = ValidateContact()



    @Test
    fun verifyContactNameWithData() {
        val actualValue = validateContact.checkContactName("John Doe")
        assertEquals(true, actualValue)
    }

    @Test
    fun verifyContactNameWithOutData() {
        val actualValue = validateContact.checkContactName("")
        assertEquals(false, actualValue)
    }

  @Test
  fun verifyPhoneNumber() {
      val actualValue = validateContact.checkContactPhoneNumber("123456")
      assertEquals(true, actualValue)
  }

    @Test
    fun verifyThatIsNotPhoneNumber() {
        val actualValue = validateContact.checkContactPhoneNumber("qwerty")
        assertEquals(false, actualValue)
    }

    @Test
    fun verifyLabelName() {
        val actualValue = validateContact.checkContactLabel("Main")
        assertEquals(true, actualValue)
    }

    @Test
    fun `Passing Label to Return False`(){
        val actualValue = validateContact.checkContactLabel("Label")
        assertEquals(false, actualValue)
    }

    @Test
    fun verifyEmailAddress() {
        val actualValue = validateContact.checkContactEmail("asd@mail.com")
        assertEquals(true, actualValue)
    }

    @Test
    fun `Check for fake email Address`() {
        val actualValue = validateContact.checkContactEmail("asd.com")
        assertEquals(false, actualValue)
    }


    @Test
    fun verifyContactAddressWithData() {
        val actualValue = validateContact.checkUserAddress("1st Street, 3rd Cross, Goa, India")
        assertEquals(true, actualValue)
    }

    @Test
    fun verifyContactAddressWithOutData() {
        val actualValue = validateContact.checkUserAddress("")
        assertEquals(false, actualValue)
    }


    @Test
    fun verifyContactOrganizationWithData() {
        val actualValue = validateContact.checkContactOrganization("MicroSoft")
        assertEquals(true, actualValue)
    }

    @Test
    fun verifyContactOrganizationWithOutData() {
        val actualValue = validateContact.checkContactOrganization("")
        assertEquals(false, actualValue)
    }


}