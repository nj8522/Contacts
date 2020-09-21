package com.nash.contactsapp.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nash.contactsapp.R




class ContactDetailActivity : AppCompatActivity() {

    //Image
    lateinit var contactUserImage : ImageView
    lateinit var image : Bitmap

    //Name
    lateinit var contactName : TextView

    //Number
    private lateinit var contact_mobile_number : TextView
    private lateinit var contact_home_number : TextView
    private lateinit var contact_work_number : TextView

    //Email
    lateinit var contact_work_email : TextView
    lateinit var contact_home_email : TextView

    //Organization
    lateinit var contactOrganization : TextView


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


        contactUserImage()

        val name = intent.extras?.getString("name")
        contactName.text = name.toString()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }


    private fun contactUserImage() {

        val imageData = intent.extras?.getString("img")
        if(imageData != null && imageData != "") {

            image = BitmapFactory.decodeFile(imageData)

            if(image != null) {
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

    private fun retrievePhoneNumber(){

        val numberMap : HashMap<String, String> = intent.getSerializableExtra("number") as HashMap<String, String>
        val mobileNumber = numberMap["Mobile"]
        Log.i("activity", mobileNumber.toString())
        val workNumber = numberMap["Work"]
        val homeNumber = numberMap["Home"]


        if(mobileNumber != "" && mobileNumber != null){
            contact_mobile_number.text = mobileNumber.toString()
        } else {
            contact_mobile_number.visibility = View.INVISIBLE
        }

        if(workNumber != "" && workNumber != null){
            contact_work_number.text = workNumber.toString()
        } else {
            contact_work_number.visibility = View.INVISIBLE
        }

        if(homeNumber != "" && homeNumber != null){
            contact_home_number.text = homeNumber.toString()
        } else {
            contact_home_number.visibility = View.INVISIBLE
        }


    }

    private fun retrieveEmail() {

        val emailMap : HashMap<String, String> =  intent.getSerializableExtra("email") as HashMap<String, String>
        val workEmail =  emailMap["Work"]
        val homeEmail = emailMap["Home"]

        if(workEmail != "" && workEmail != null){
            contact_work_email.text = workEmail.toString()
        } else {
            contact_work_email.visibility = View.INVISIBLE
        }

        if(homeEmail != "" && homeEmail != null){
            contact_home_email.text = homeEmail.toString()
        } else {
            contact_home_email.visibility = View.INVISIBLE
        }

    }

    private fun retrieveOrganization() {

      val orgMap : String? = intent.extras?.getString("org")
      val homeOrg = orgMap

      if(homeOrg != "" && homeOrg != null) {
          contactOrganization.text = homeOrg.toString()
      } else {
          contactOrganization.visibility = View.INVISIBLE
      }

    }

}


