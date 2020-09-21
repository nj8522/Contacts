package com.nash.contactsapp.ui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.nash.contactsapp.R
import com.nash.contactsapp.provider.ContactProvider

class AddNewContact : AppCompatActivity() {

    private lateinit var contactImage: ImageView

    private lateinit var contactName: TextView

    private lateinit var contactMobileNumber: TextView
    private lateinit var contactWorkNumber: TextView
    private lateinit var contactHomeNumber: TextView

    private lateinit var contactWorkEmail: TextView
    private lateinit var contactHomeEmail: TextView

    private lateinit var contactOrganization: TextView

    //Data
    private val CONTACTS_NAME = "CONTACT_NAME"
    private val CONTACTS_IMAGE = "CONTACT_PHOTO"
    private val PHONE_MOBILE = "PHONE_MOBILE"
    private val PHONE_WORK = "PHONE_WORK"
    private val PHONE_HOME = "PHONE_HOME"
    private val EMAIL_HOME = "EMAIL_HOME"
    private val EMAIL_WORK = "EMAIL_WORK"
    private val ORGANIZATION_HOME = "ORGANIZATION_HOME"

    //Uri
    private val uri = ContactProvider.CONTENT_URI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)

        contactImage = findViewById(R.id.new_contact__image)

        contactName = findViewById(R.id.new_contact_name)

        contactMobileNumber = findViewById(R.id.new_mobileNumber)
        contactWorkNumber = findViewById(R.id.new_workNumber)
        contactHomeNumber = findViewById(R.id.new_customNumber)

        contactWorkEmail = findViewById(R.id.new_email_work)
        contactHomeEmail = findViewById(R.id.new_email_home)

        contactOrganization = findViewById(R.id.new_organization)

    }


    fun addNewContact(view: View) {

        val contentValue = ContentValues()

        if (contactName.text.isNotEmpty() && contactMobileNumber.text.isNotEmpty()) {
            contentValue.put(CONTACTS_NAME, contactName.text.toString().trim())
            contentValue.put(PHONE_MOBILE, contactMobileNumber.text.toString().trim())
        } else {
            Toast.makeText(this, "Name and Mobile Fields are Empty", Toast.LENGTH_SHORT).show()
        }

        if (contactWorkNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_WORK, contactWorkNumber.text.toString().trim())
        }

        if (contactHomeNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_HOME, contactHomeNumber.text.toString().trim())
        }

        if (contactWorkEmail.text.isNotEmpty()) {
            contentValue.put(EMAIL_WORK, contactWorkEmail.text.toString().trim())
        }

        if (contactHomeEmail.text.isNotEmpty()) {
            contentValue.put(EMAIL_HOME, contactHomeEmail.text.toString().trim())
        }

        if (contactOrganization.text.isNotEmpty()) {
            contentValue.put(ORGANIZATION_HOME, contactOrganization.text.toString().trim())
        }


        if(contactName.text.isNotEmpty() && (contactMobileNumber.text.isNotEmpty() || contactWorkNumber.text.isNotEmpty() || contactHomeNumber.text.isNotEmpty())){

            val isSuccessful = contentResolver.insert(uri, contentValue)
            Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {

            Toast.makeText(this, "Please fill in Contact Name and Number", Toast.LENGTH_SHORT).show()
        }

    }
}