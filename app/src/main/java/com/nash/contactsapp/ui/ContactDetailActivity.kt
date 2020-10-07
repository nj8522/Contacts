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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.nash.contactsapp.R
import com.nash.contactsapp.provider.ContactProvider


class ContactDetailActivity : AppCompatActivity() {

    //Image
    lateinit var contactUserImage: ImageView
    lateinit var image: Bitmap

    //Name
    lateinit var contactName: TextView

    //Number
    private lateinit var contactMobileNumber : TextView
    private lateinit var contactHomeNumber : TextView
    private lateinit var contactWorkNumber : TextView
    private lateinit var contactCustomTag  : TextView

    //Email
    private lateinit var contactWorkEmail : TextView
    private lateinit var contactHomeEmail : TextView
    private lateinit var contactCustomEmail : TextView
    private lateinit var customMailTag : TextView

    //Organization
    lateinit var contactOrganization: TextView

    //Address
    private lateinit var contactAddressHome : TextView
    private lateinit var contactAddressWork : TextView
    private lateinit var contactAddressCustom : TextView
    private lateinit var customAddressTag : TextView

    //Layout
    private lateinit var layoutThree: LinearLayout
    private lateinit var layoutFive : LinearLayout

    //Phone Inner Layout
    private lateinit var mobileNumberLayout: LinearLayout
    private lateinit var workNumberLayout: LinearLayout
    private lateinit var customNumberLayout: LinearLayout

    //Address Inner layout
    private lateinit var addressWorkLayout : LinearLayout
    private lateinit var addresssHomeLayout : LinearLayout
    private lateinit var addressCustomLayout : LinearLayout


    //Email Inner Layout
    private lateinit var emailWorkLayout: LinearLayout
    private lateinit var emailHomeLayout: LinearLayout
    private lateinit var emailCustomLayout: LinearLayout


    //Data to Update Activity
    private var contactId: Int = 0

    private var updateImageData: String? = ""

    private var updateName: String? = ""

    private var updateMobile: String? = ""
    private var updateWork: String? = ""
    private var updateCustom: String? = ""

    private var updateAddressHome : String = ""
    private var updateAddressWork : String = ""
    private var updateAddressCustom : String = ""

    private var updateEmailWork: String? = ""
    private var updateEmailHome: String? = ""
    private var updateEmailCustom : String? = ""

    private var updateOrganization: String? = ""

    private var numTag : String? = ""
    private var  addressTag : String? = ""
    private var  emailTag : String = ""


    //TopBar
    private lateinit var topBar : MaterialToolbar

    //uri
    private val uri = ContactProvider.CONTENT_URI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)


        //TopBar
        topBar = findViewById(R.id.topAppBar)
        topBar.title = ""
        setSupportActionBar(topBar)
        topBar.setNavigationIcon(R.drawable.arrow_back)






       /* contactUserImage = findViewById(R.id.contact_image)
        contactName = findViewById(R.id.contact_name)

        //Mobile
        contactMobileNumber = findViewById(R.id.contact_mobile_number)
        contactWorkNumber = findViewById(R.id.contact_work_number)
        contactHomeNumber = findViewById(R.id.contact_home_number)
        contactCustomTag = findViewById(R.id.custom_number_text)

        //Email
        contactWorkEmail = findViewById(R.id.contact_email_work)
        contactHomeEmail = findViewById(R.id.contact_email_home)
        contactCustomEmail = findViewById(R.id.contact_email_custom)
        customMailTag = findViewById(R.id.email_custom_text)


        //Organization
        contactOrganization = findViewById(R.id.contact_organization)

        //Address
        contactAddressHome = findViewById(R.id.contact_address_home)
        contactAddressWork = findViewById(R.id.contact_address_work)
        contactAddressCustom = findViewById(R.id.contact_address_custom)
        customAddressTag = findViewById(R.id.address_custom_text)


        //Layout
        layoutThree = findViewById(R.id.linear_layout_Three)
        layoutFive = findViewById(R.id.linear_layout_Five)

        //Phone Inner Layout
        mobileNumberLayout = findViewById(R.id.mobile_num_layout)
        workNumberLayout = findViewById(R.id.work_num_layout)
        customNumberLayout = findViewById(R.id.custom_num_layout)

        //Address Inner Layout
        addresssHomeLayout = findViewById(R.id.address_home_layout)
        addressWorkLayout = findViewById(R.id.address_work_layout)
        addressCustomLayout = findViewById(R.id.address_custom_layout)


        //Email Inner Layout
        emailWorkLayout = findViewById(R.id.work_email_layout)
        emailHomeLayout = findViewById(R.id.home_email_layout)
        emailCustomLayout = findViewById(R.id.custom_email_layout)*/


        contactId = intent.extras?.getInt("id")!!
        Log.i("detail", contactId.toString())

        contactUserImage()

        val name = intent.extras?.getString("name")!!
       /* contactName.text = name.toString()*/
        updateName = name


        //Retrieve Number
        retrievePhoneNumber()

        //Retrieve Email
        retrieveEmail()

        //Organization
        retrieveOrganization()

        //Address
        retrieveAddress()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_contact -> deleteContact()
            else -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun openUpdateActivity(view: View) {

        val updateIntent = Intent(this, UpdateContact::class.java).apply {

            putExtra("id", contactId)
            putExtra("image", updateImageData)
            putExtra("name", updateName)
            putExtra("mobileNum", updateMobile)
            putExtra("workNum", updateWork)
            putExtra("homeNum", updateCustom)
            putExtra("customNum", numTag)
            putExtra("addressHome", updateAddressHome)
            putExtra("addressWork", updateAddressWork)
            putExtra("addressCustom", updateAddressCustom)
            putExtra("customAddress", addressTag)
            putExtra("mailWork", updateEmailWork)
            putExtra("mailHome", updateEmailHome)
            putExtra("mailCustom", updateEmailCustom)
            putExtra("customMail", emailTag)
            putExtra("org", updateOrganization)
        }
        this.startActivity(updateIntent)
    }

    private fun contactUserImage() {
/*
        val imageData = intent.extras?.getString("img")

        updateImageData = imageData
        Log.i("image", updateImageData)

        if (imageData != null && imageData != "" && imageData != "null") {

            image = BitmapFactory.decodeFile(imageData)

            if (image != null) {
                contactUserImage.setImageBitmap(image)
            } else {

                val new_image = resources.getDrawable(R.drawable.ic_launcher_background)
                contactUserImage.setBackgroundDrawable(new_image)

                *//* image = BitmapFactory.decodeFile(ic_launcher_background.toString())
                 contactUserImage.setImageBitmap(image)*//*
            }
        } else {

            val new_image = resources.getDrawable(R.drawable.ic_launcher_background)
            contactUserImage.setBackgroundDrawable(new_image)
        }*/

    }

    private fun retrievePhoneNumber() {

       /* val numberMap: HashMap<String, String> = intent.getSerializableExtra("number") as HashMap<String, String>

        val mobileNumber = numberMap["Mobile"]
        updateMobile = mobileNumber

        val workNumber = numberMap["Work"]
        updateWork = workNumber

        val homeNumber = numberMap["Home"]
        updateCustom = homeNumber

        numTag = intent.extras?.getString("customNum")


        if (mobileNumber != "" && mobileNumber != null) {
            contactMobileNumber.text = mobileNumber.toString()
        } else {
            mobileNumberLayout.visibility = View.GONE
        }

        if (workNumber != "" && workNumber != null) {
            contactWorkNumber.text = workNumber.toString()
        } else {
            workNumberLayout.visibility = View.GONE
            //contact_work_number.visibility = View.INVISIBLE
        }

        if (homeNumber != "" && homeNumber != null) {
            contactCustomTag.text = numTag
            contactHomeNumber.text = homeNumber.toString()
        } else {
            customNumberLayout.visibility = View.GONE
            //contact_home_number.visibility = View.INVISIBLE
        }*/

    }

    private fun retrieveAddress() {

      /*  val addressMap : HashMap<String, String> = intent.getSerializableExtra("address") as HashMap<String, String>

        val homeAddress = addressMap["Home"]
        updateAddressHome = homeAddress.toString()

        val workAddress = addressMap["Work"]
        updateAddressWork = workAddress.toString()

        val customAddress = addressMap["Custom"]
        updateAddressCustom = customAddress.toString()

        val customTag = intent.extras?.getString("customAdd")
        addressTag = customTag.toString()

        if(homeAddress != null && homeAddress != ""){
            contactAddressHome.text = homeAddress
        } else {
            addresssHomeLayout.visibility = View.GONE
        }

        if(workAddress != null && workAddress != ""){
            contactAddressWork.text = workAddress
        } else {
            addressWorkLayout.visibility = View.GONE
        }

        if(customAddress != null && customAddress != ""){
            customAddressTag.text = customTag
            contactAddressCustom.text = customAddress
        } else {
            addressCustomLayout.visibility = View.GONE
        }

        if(homeAddress == "" && workAddress == "" && customAddress == "") {
            layoutThree.visibility = View.GONE
        }*/
    }

    private fun retrieveEmail() {

       /* val emailMap: HashMap<String, String> = intent.getSerializableExtra("email") as HashMap<String, String>
        val workEmail = emailMap["Work"]
        updateEmailWork = workEmail

        val homeEmail = emailMap["Home"]
        updateEmailHome = homeEmail

        val customEmail = emailMap["Custom"]
        updateEmailCustom = customEmail

        val customTag = intent.extras?.getString("customMail")
        emailTag = customTag.toString()


        if (workEmail != "" && workEmail != null) {
            contactWorkEmail.text = workEmail.toString()
        } else {
            emailWorkLayout.visibility = View.GONE
            //contact_work_email.visibility = View.INVISIBLE
        }

        if (homeEmail != "" && homeEmail != null) {
            contactHomeEmail.text = homeEmail.toString()
        } else {
            emailHomeLayout.visibility = View.GONE
            //contact_home_email.visibility = View.INVISIBLE
        }

        if (customEmail != "" && customEmail != null) {
            customMailTag.text = customTag
            contactCustomEmail.text = customEmail.toString()
        } else {
            emailCustomLayout.visibility = View.GONE
        }*/
    }

    private fun retrieveOrganization() {

      /*  val orgMap: String? = intent.extras?.getString("org")
        val homeOrg = orgMap
        updateOrganization = homeOrg

        if (homeOrg != "" && homeOrg != null) {
            contactOrganization.text = homeOrg.toString()
        } else {
            layoutFive.visibility = View.GONE
        }*/
    }

    private fun deleteContact() {

        val whereClause = "_ID = $contactId"

        val isSuccessful = contentResolver.delete(uri, whereClause, null)

        if (isSuccessful > 0) {
            Toast.makeText(this, "Contact Deleted Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ContactActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Failed to Delete Contact", Toast.LENGTH_SHORT).show()
        }

    }



}


