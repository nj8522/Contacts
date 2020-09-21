package com.nash.contactsapp.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nash.contactsapp.R
import com.nash.contactsapp.provider.ContactProvider


class ContactDetailActivity : AppCompatActivity() {

    //Image
    lateinit var contactUserImage: ImageView
    lateinit var image: Bitmap

    //Name
    lateinit var contactName: TextView

    //Number
    private lateinit var contact_mobile_number: TextView
    private lateinit var contact_home_number: TextView
    private lateinit var contact_work_number: TextView

    //Email
    lateinit var contact_work_email: TextView
    lateinit var contact_home_email: TextView

    //Organization
    lateinit var contactOrganization: TextView

    //Data to Update Activity
    private var contactId: Int = 0

    private var updateImageData: String? = ""

    private var updateName: String? = ""

    private var updateMobile: String? = ""
    private var updateWork: String? = ""
    private var updateHome: String? = ""

    private var updateEmailWork: String? = ""
    private var updateEmailHome: String? = ""

    private var updateOrganization: String? = ""

    //uri
    private val uri = ContactProvider.CONTENT_URI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)


        contactUserImage = findViewById(R.id.contactImage)
        contactName = findViewById(R.id.contact_name)

        //Mobile
        contact_mobile_number = findViewById(R.id.contact_mobile_number)
        contact_work_number = findViewById(R.id.contact_work_number)
        contact_home_number = findViewById(R.id.contact_home_number)

        //Email
        contact_work_email = findViewById(R.id.email_work)
        contact_home_email = findViewById(R.id.email_home)

        //Organization
        contactOrganization = findViewById(R.id.contact_home_organization)



        contactId = intent.extras?.getInt("id")!!
        Log.i("detail", contactId.toString())

        contactUserImage()

        val name = intent.extras?.getString("name")!!
        contactName.text = name.toString()
        updateName = name


        //Retrieve Number
        retrievePhoneNumber()

        //Retrieve Email
        retrieveEmail()

        //Organization
        retrieveOrganization()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun contactUserImage() {

        val imageData = intent.extras?.getString("img")

        updateImageData = imageData

        if (imageData != null && imageData != "") {

            image = BitmapFactory.decodeFile(imageData)

            if (image != null) {
                contactUserImage.setImageBitmap(image)
            } else {

                val new_image = resources.getDrawable(R.drawable.ic_launcher_background)
                contactUserImage.setBackgroundDrawable(new_image)

                /* image = BitmapFactory.decodeFile(ic_launcher_background.toString())
                 contactUserImage.setImageBitmap(image)*/
            }
        } else {

            val new_image = resources.getDrawable(R.drawable.ic_launcher_background)
            contactUserImage.setBackgroundDrawable(new_image)
        }

    }

    private fun retrievePhoneNumber() {

        val numberMap: HashMap<String, String> =
            intent.getSerializableExtra("number") as HashMap<String, String>
        val mobileNumber = numberMap["Mobile"]
        updateMobile = mobileNumber
        Log.i("activity", mobileNumber.toString())
        val workNumber = numberMap["Work"]
        updateWork = workNumber
        val homeNumber = numberMap["Home"]
        updateHome = homeNumber


        if (mobileNumber != "" && mobileNumber != null) {
            contact_mobile_number.text = mobileNumber.toString()
        } else {
            contact_mobile_number.visibility = View.INVISIBLE
        }

        if (workNumber != "" && workNumber != null) {
            contact_work_number.text = workNumber.toString()
        } else {
            contact_work_number.visibility = View.INVISIBLE
        }

        if (homeNumber != "" && homeNumber != null) {
            contact_home_number.text = homeNumber.toString()
        } else {
            contact_home_number.visibility = View.INVISIBLE
        }


    }

    private fun retrieveEmail() {

        val emailMap: HashMap<String, String> =
            intent.getSerializableExtra("email") as HashMap<String, String>
        val workEmail = emailMap["Work"]
        updateEmailWork = workEmail
        val homeEmail = emailMap["Home"]
        updateEmailHome = homeEmail

        if (workEmail != "" && workEmail != null) {
            contact_work_email.text = workEmail.toString()
        } else {
            contact_work_email.visibility = View.INVISIBLE
        }

        if (homeEmail != "" && homeEmail != null) {
            contact_home_email.text = homeEmail.toString()
        } else {
            contact_home_email.visibility = View.INVISIBLE
        }

    }

    private fun retrieveOrganization() {

        val orgMap: String? = intent.extras?.getString("org")
        val homeOrg = orgMap
        updateOrganization = homeOrg

        if (homeOrg != "" && homeOrg != null) {
            contactOrganization.text = homeOrg.toString()
        } else {
            contactOrganization.visibility = View.INVISIBLE
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.update_contact -> {
                val updateIntent = Intent(this, UpdateContact::class.java).apply {

                    putExtra("id", contactId)
                    putExtra("image", updateImageData)
                    putExtra("name", updateName)
                    putExtra("mobileNum", updateMobile)
                    putExtra("workNum", updateWork)
                    putExtra("homeNum", updateHome)
                    putExtra("mailWork", updateEmailWork)
                    putExtra("mailHome", updateEmailHome)
                    putExtra("org", updateOrganization)
                }
                this.startActivity(updateIntent)
            }

             else -> deleteContact()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteContact(){

        val whereClause = "_ID = $contactId"

        val isSuccessful = contentResolver.delete(uri, whereClause, null)

        if(isSuccessful > 0) {
            Toast.makeText(this, "Contact Deleted Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity :: class.java))
            finish()
        } else {
            Toast.makeText(this, "Failed to Delete Contact", Toast.LENGTH_SHORT).show()
        }

    }



}


