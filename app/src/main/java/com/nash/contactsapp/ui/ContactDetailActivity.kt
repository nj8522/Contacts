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
import com.nash.contactsapp.R
import com.nash.contactsapp.model.AddressDetail
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
    private lateinit var contactCustomTag : TextView

    //Email
    lateinit var contact_work_email: TextView
    lateinit var contact_home_email: TextView

    //Organization
    lateinit var contactOrganization: TextView

    //Address
    lateinit var addressHouse : TextView
    lateinit var addressLineTwo : TextView
    lateinit var addressCity : TextView
    lateinit var addressState : TextView
    lateinit var addressCountry : TextView
    lateinit var addressPincode : TextView

    //Layout
    lateinit var layoutOne: LinearLayout
    lateinit var layoutTwo: LinearLayout
    lateinit var layoutThree: LinearLayout
    lateinit var layoutFour: LinearLayout
    lateinit var layoutFive : LinearLayout

    //Inner Layout
    lateinit var mobileNumberLayout: LinearLayout
    lateinit var workNumberLayout: LinearLayout
    lateinit var customNumberLayout: LinearLayout

    lateinit var emailWorkLayout: LinearLayout
    lateinit var emailCustomLayout: LinearLayout


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

    private var numTag : String? = ""

    //uri
    private val uri = ContactProvider.CONTENT_URI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)


        contactUserImage = findViewById(R.id.contact_image)
        contactName = findViewById(R.id.contact_name)

        //Mobile
        contact_mobile_number = findViewById(R.id.contact_mobile_number)
        contact_work_number = findViewById(R.id.contact_work_number)
        contact_home_number = findViewById(R.id.contact_home_number)
        contactCustomTag = findViewById(R.id.custom_number_text)

        //Email
        contact_work_email = findViewById(R.id.contact_email_work)
        contact_home_email = findViewById(R.id.contact_email_custom)

        //Organization
        contactOrganization = findViewById(R.id.contact_organization)

        //Address
        addressHouse = findViewById(R.id.address_line_one)
        addressLineTwo = findViewById(R.id.address_line_two)
        addressCity = findViewById(R.id.address_city)
        addressState = findViewById(R.id.address_state)
        addressCountry = findViewById(R.id.address_country)
        addressPincode = findViewById(R.id.address_pincode)


        //Layout
        layoutThree = findViewById(R.id.linear_layout_Three)
        layoutFive = findViewById(R.id.linear_layout_Five)

        //Inner Layout
        mobileNumberLayout = findViewById(R.id.mobile_num_layout)
        workNumberLayout = findViewById(R.id.work_num_layout)
        customNumberLayout = findViewById(R.id.custom_num_layout)

        emailWorkLayout = findViewById(R.id.work_email_layout)
        emailCustomLayout = findViewById(R.id.custom_email_layout)


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

        //Address
        retrieveAddress()
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

        val numberMap: HashMap<String, String> = intent.getSerializableExtra("number") as HashMap<String, String>
        val mobileNumber = numberMap["Mobile"]
        updateMobile = mobileNumber
        Log.i("activity", mobileNumber.toString())
        val workNumber = numberMap["Work"]
        updateWork = workNumber
        val homeNumber = numberMap["Home"]
        updateHome = homeNumber
        numTag = intent.extras?.getString("customNum")
        Log.i("act", numTag.toString())

        if (mobileNumber != "" && mobileNumber != null) {
            contact_mobile_number.text = mobileNumber.toString()
        } else {
            mobileNumberLayout.visibility = View.GONE
        }

        if (workNumber != "" && workNumber != null) {
            contact_work_number.text = workNumber.toString()
        } else {
            workNumberLayout.visibility = View.GONE
            //contact_work_number.visibility = View.INVISIBLE
        }

        if (homeNumber != "" && homeNumber != null) {
            contactCustomTag.text = numTag
            contact_home_number.text = homeNumber.toString()
        } else {
            customNumberLayout.visibility = View.GONE
            //contact_home_number.visibility = View.INVISIBLE
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
            emailWorkLayout.visibility = View.GONE
            //contact_work_email.visibility = View.INVISIBLE
        }

        if (homeEmail != "" && homeEmail != null) {
            contact_home_email.text = homeEmail.toString()
        } else {
            emailCustomLayout.visibility = View.GONE
            //contact_home_email.visibility = View.INVISIBLE
        }

    }

    private fun retrieveOrganization() {

        val orgMap: String? = intent.extras?.getString("org")
        val homeOrg = orgMap
        updateOrganization = homeOrg

        if (homeOrg != "" && homeOrg != null) {
            contactOrganization.text = homeOrg.toString()
        } else {
            layoutFive.visibility = View.GONE
        }
    }

    private fun retrieveAddress() {

        val addressMap : HashMap<String, AddressDetail> = intent.getSerializableExtra("address") as HashMap<String, AddressDetail>
        val addressData = addressMap["contact"]

        val houseNumber = addressData?.addressHouseNumber
        val addressLineTwo = addressData?.addressLineTwo
        val city = addressData?.addressCity
        val state = addressData?.addressState
        val country = addressData?.addressCountry
        val pincode = addressData?.addressPincode


        if(houseNumber != null || addressLineTwo != null || city != null || state != null || country != null || pincode != null) {

            if(houseNumber != null || houseNumber != ""){
                addressHouse.text = houseNumber
            }

            if(addressLineTwo != null || addressLineTwo != ""){
                addressHouse.text = addressLineTwo
            }

            if(city != null ||  city != ""){
                addressHouse.text = city
            }

            if(state != null || state != ""){
                addressHouse.text = state
            }

            if(country != null || country != ""){
                addressHouse.text = country
            }

            if(pincode != null || pincode != ""){
                addressHouse.text = pincode
            }

        } else {
            layoutThree.visibility = View.GONE
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
                    putExtra("customNum", numTag)
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

    private fun deleteContact() {

        val whereClause = "_ID = $contactId"

        val isSuccessful = contentResolver.delete(uri, whereClause, null)

        if (isSuccessful > 0) {
            Toast.makeText(this, "Contact Deleted Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Failed to Delete Contact", Toast.LENGTH_SHORT).show()
        }

    }

}


