package com.nash.contactsapp.ui

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.nash.contactsapp.R
import com.nash.contactsapp.provider.ContactProvider
import org.w3c.dom.Text

class UpdateContact : AppCompatActivity() {

    private lateinit var contactImage: ImageView

    private lateinit var contactName: TextView

    private lateinit var contactMobileNumber: TextView
    private lateinit var contactWorkNumber: TextView
    private lateinit var contactHomeNumber: TextView

    private lateinit var contactWorkEmail: TextView
    private lateinit var contactHomeEmail: TextView

    private lateinit var contactOrganization: TextView

    //Id
    private var contactPersonId : Int? = 0



    //Uri
    private val uri = ContactProvider.CONTENT_URI

    //Data
    private val CONTACTS_ID = "_ID"
    private val CONTACTS_NAME = "CONTACT_NAME"
    private val CONTACTS_IMAGE = "CONTACT_PHOTO"
    private val PHONE_MOBILE = "PHONE_MOBILE"
    private val PHONE_WORK = "PHONE_WORK"
    private val PHONE_HOME = "PHONE_HOME"
    private val EMAIL_HOME = "EMAIL_HOME"
    private val EMAIL_WORK = "EMAIL_WORK"
    private val ORGANIZATION_HOME = "ORGANIZATION_HOME"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)


        contactImage = findViewById(R.id.contact_update_image)
        contactName = findViewById(R.id.contact_update_name)

        //Mobile
        contactMobileNumber = findViewById(R.id.mobileNumber)
        contactWorkNumber = findViewById(R.id.workNumber)
        contactHomeNumber = findViewById(R.id.customNumber)

        //Email
        contactHomeEmail = findViewById(R.id.email_home)
        contactWorkEmail = findViewById(R.id.email_work)

        //Org
        contactOrganization = findViewById(R.id.organization)


        contactPersonId = intent.extras?.getInt("id")
        val image = intent.extras?.getString("image")
        val name = intent.extras?.getString("name")
        Log.i("update", name.toString())

        val phoneMobil = intent.extras?.getString("mobileNum")
        val phoneWork = intent.extras?.getString("workNum")
        val phoneHome = intent.extras?.getString("homeNum")
        val emailWork = intent.extras?.getString("mailWork")
        val emailHome = intent.extras?.getString("mailHome")
        val organization = intent.extras?.getString("org")




        if (image != null && image != "") {

            val imageData = BitmapFactory.decodeFile(image)

            if (image != null) {
                contactImage.setImageBitmap(imageData)
            } else {

                val new_image = resources.getDrawable(R.drawable.ic_launcher_background)
                contactImage.setBackgroundDrawable(new_image)

                /* image = BitmapFactory.decodeFile(ic_launcher_background.toString())
                 contactUserImage.setImageBitmap(image)*/
            }
        } else {

            val new_image = resources.getDrawable(R.drawable.ic_launcher_background)
            contactImage.setBackgroundDrawable(new_image)
        }



        if (name != null || name == "") {
            contactName.text = name
        }

        if (phoneMobil != null || phoneMobil == "") {
            contactMobileNumber.text = phoneMobil
        }

        if (phoneWork != null || phoneWork == "") {
            contactWorkNumber.text = phoneWork
        }

        if (phoneHome != null || phoneHome == "") {
            contactHomeNumber.text = phoneHome
        }

        if (emailWork != null || emailWork == "") {
            contactWorkEmail.text = emailWork
        }

        if (emailHome != null || emailHome == "") {
            contactHomeEmail.text = emailHome
        }

        if (organization != null || organization == "") {
            contactOrganization.text = organization
        }



    }

    fun updateContact(view: View) {

        val contentValue = ContentValues()

        if (contactName.text.isNotEmpty() && contactMobileNumber.text.isNotEmpty()) {
            contentValue.put(CONTACTS_NAME, contactName.text.toString().trim())
            contentValue.put(PHONE_MOBILE, contactMobileNumber.text.toString().trim())
        } else {
            Toast.makeText(this, "Name and Mobile Fields are Empty", Toast.LENGTH_SHORT).show()
        }

        if(contactWorkNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_WORK, contactWorkNumber.text.toString().trim())
        }

        if(contactHomeNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_HOME, contactHomeNumber.text.toString().trim())
        }

        if(contactWorkEmail.text.isNotEmpty()) {
            contentValue.put(EMAIL_WORK, contactWorkEmail.text.toString().trim())
        }

        if(contactHomeEmail.text.isNotEmpty()) {
            contentValue.put(EMAIL_HOME, contactHomeEmail.text.toString().trim())
        }

        if(contactOrganization.text.isNotEmpty()) {
            contentValue.put(ORGANIZATION_HOME, contactOrganization.text.toString().trim())
        }

        val whereClause = "$CONTACTS_ID = $contactPersonId"

        val isSuccessful = contentResolver.update(uri, contentValue, whereClause, null)

        if(isSuccessful > 0) {
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity :: class.java))
            finish()
        } else {
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }

}