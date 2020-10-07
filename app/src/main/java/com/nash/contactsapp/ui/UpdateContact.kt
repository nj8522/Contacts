package com.nash.contactsapp.ui

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.nash.contactsapp.R
import com.nash.contactsapp.provider.ContactProvider
import kotlinx.android.synthetic.main.activity_update_contact.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class UpdateContact : AppCompatActivity() {

    private lateinit var contactImage: AppCompatImageButton

    private lateinit var contactName: TextInputEditText

    private lateinit var contactMobileNumber: TextInputEditText

    /*private lateinit var contactWorkNumber: TextView
    private lateinit var contactCustomNumber: TextView*/


    private lateinit var contactAddressHome: TextInputEditText

    /*private lateinit var contactAddressWork: TextView
    private lateinit var contactAddressCustom: TextView*/

    private lateinit var contactWorkEmail: TextInputEditText

    /* private lateinit var contactHomeEmail: TextView
    private lateinit var contactCustomEmail: TextView*/

    private lateinit var contactOrganization: TextInputEditText

    //Id
    private var contactPersonId: Int? = 0

    //Tag Name
    private var contactTag: String = ""
    private var addressTag: String = ""
    private var mailTag: String = ""


    //Uri
    private val uri = ContactProvider.CONTENT_URI


    //Data
    val CONTACTS_ID = "_ID"
    val CONTACTS_NAME = "CONTACT_NAME"
    val CONTACTS_IMAGE = "CONTACT_PHOTO"
    val PHONE_MOBILE = "PHONE_MOBILE"
    val PHONE_WORK = "PHONE_WORK"
    val PHONE_CUSTOM = "PHONE_CUSTOM"
    val PHONE_CUSTOM_TaG = "PHONE_TAG"
    val ADDRESS_WORK = "ADDRESS_WORK"
    val ADDRESS_HOME = "ADDRESS_HOME"
    val ADDRESS_CUSTOM = "ADDRESS_CUSTOM"
    val ADDRESS_TAG = "ADDRESS_TAG"
    val EMAIL_HOME = "EMAIL_HOME"
    val EMAIL_WORK = "EMAIL_WORK"
    val EMAIL_CUSTOM = "EMAIL_CUSTOM"
    val EMAIL_TAG = "EMAIL_TAG"
    val ORGANIZATION_HOME = "ORGANIZATION_HOME"


    //Gallery Code
    val GALLERY = 101

    //Image Uri
    var imageUriData: String? = ""

    //Top bar
    private lateinit var topBar : MaterialToolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)

        topBar = findViewById(R.id.topAppBar)
        topBar.title = "Edit contact"
        setSupportActionBar(topBar)
        topBar.setNavigationIcon(R.drawable.ic_close_18dp)



        contactImage = findViewById(R.id.contact_select_image)
        contactName = findViewById(R.id.add_contact_name)

        //Mobile
        contactMobileNumber = findViewById(R.id.add_contact_phone)

        /*contactWorkNumber = findViewById(R.id.workNumber)
        contactCustomNumber = findViewById(R.id.customNumber)*/


        //Address
        contactAddressHome = findViewById(R.id.add_contact_address)

        /*contactAddressWork = findViewById(R.id.update_address_work)
        contactAddressCustom = findViewById(R.id.update_address_custom)*/


        //Email
        contactWorkEmail = findViewById(R.id.add_contact_email)

        /*contactHomeEmail = findViewById(R.id.email_home)
        contactCustomEmail = findViewById(R.id.email_custom)*/

        //Org
        contactOrganization = findViewById(R.id.add_contact_organization)

        //Data From Detail Activity

        contactPersonId = intent.extras?.getInt("id")
        val image = intent.extras?.getString("image")
        val name = intent.extras?.getString("name")
        Log.i("update", name.toString())

        val phoneMobil = intent.extras?.getString("mobileNum")
        val phoneWork = intent.extras?.getString("workNum")
        val phoneHome = intent.extras?.getString("homeNum")
        val customNumTag = intent.extras?.getString("customNum")

        val addressHome = intent.extras?.getString("addressHome")
        val addressWork = intent.extras?.getString("addressWork")
        val addressCustom = intent.extras?.getString("addressCustom")
        val customAddressTag = intent.extras?.getString("customAddress")

        val emailWork = intent.extras?.getString("mailWork")
        val emailHome = intent.extras?.getString("mailHome")
        val emailCustom = intent.extras?.getString("mailCustom")
        val customEmailTag = intent.extras?.getString("customMail")

        val organization = intent.extras?.getString("org")


        /*  if (image != null && image != "") {

            val imageData = BitmapFactory.decodeFile(image)
            contactImage.setImageBitmap(imageData)
            imageUriData = image
        }

            if (name != null || name != "") {
                contactName.text = SpannableStringBuilder(name)
            }

            if (phoneMobil != null && phoneMobil != "") {
                contactMobileNumber.text = phoneMobil
            }

            if (phoneWork != null && phoneWork != "") {
                contactWorkNumber.text = phoneWork
            }

            if (phoneHome != null && phoneHome != "") {
                contactTag = customNumTag.toString()
                contactCustomNumber.text = phoneHome
            }

            if (addressHome != null && addressHome != "null") {
                contactAddressHome.text = addressHome
            }

            if (addressWork != null && addressWork != "null") {
                contactAddressWork.text = addressWork
            }

            if (addressCustom != null && addressCustom != "null") {
                addressTag = customAddressTag.toString()
                contactAddressCustom.text = addressCustom
            }


            if (emailWork != null && emailWork != "") {
                contactWorkEmail.text = emailWork
            }

            if (emailHome != null && emailHome != "") {
                contactHomeEmail.text = emailHome
            }

            if (emailCustom != null && emailCustom != "") {
                mailTag = customEmailTag.toString()
                contactCustomEmail.text = emailCustom
            }


            if (organization != null && organization != "") {
                contactOrganization.text = organization
            }
        */

    } //onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.save_contact -> Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            R.id.action_settings -> Toast.makeText(this, "Help and feedback", Toast.LENGTH_SHORT).show()
            else -> finish()

        }

        return super.onOptionsItemSelected(item)
    }



    fun pickImage(view: View) {

        val imageIntent = Intent()
        imageIntent.type = "image/*"
        imageIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(imageIntent, "Pick Image"), GALLERY)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY && resultCode == RESULT_OK && data != null) {

            val imageData = data.data
            contactImage.setImageURI(imageData)


            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageData)
            val cacheDir = this.cacheDir
            val tempFile = File(cacheDir.path + "/contactApp$contactPersonId.png")

            try {

                val fileOutPutStream = FileOutputStream(tempFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutPutStream)
                fileOutPutStream.flush()
                fileOutPutStream.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            imageUriData = tempFile.path

        }
    }

    fun updateCustomNumber(view: View) {

       /* val builder = AlertDialog.Builder(this)

        val mLayout = LinearLayout(this)
        val customTag = EditText(this)
        val customNumber = EditText(this)

        customTag.hint = "Name"
        customNumber.hint = "Custom Number"

        customTag.setSingleLine()
        customNumber.setSingleLine()

        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(customTag)
        mLayout.addView(customNumber)

        builder.setView(mLayout)

        if (customNumber.text != null) {

            customTag.setText(contactTag)
            customNumber.setText(contactCustomNumber.text)
        }

        if (customNumber.text != null && customTag != null) {

            builder.setPositiveButton("Save") { dialogInterface, i ->
                contactCustomNumber.text = customNumber.text.toString().trim()
                contactTag = customTag.text.toString().trim()
            }
        } else {
            Toast.makeText(this, "Text is Missing", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.create().show()*/

    }

    fun updateEmailCustom(view: View) {

        /*val builder = AlertDialog.Builder(this)

        val mLayout = LinearLayout(this)
        val customTag = EditText(this)
        val customNumber = EditText(this)

        customTag.hint = "Name"
        customNumber.hint = "Custom Email"

        customTag.setSingleLine()
        customNumber.setSingleLine()

        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(customTag)
        mLayout.addView(customNumber)

        builder.setView(mLayout)

        if (customNumber.text != null) {

            customTag.setText(mailTag)
            customNumber.setText(contactCustomEmail.text)
        }

        if (customNumber.text != null && customTag != null) {

            builder.setPositiveButton("Save") { dialogInterface, i ->
                contactCustomEmail.text = customNumber.text.toString().trim()
                mailTag = customTag.text.toString().trim()
            }
        } else {
            Toast.makeText(this, "Text is Missing", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.create().show()*/


    }

    fun updateAddressCustom(view: View) {

       /* val builder = AlertDialog.Builder(this)

        val mLayout = LinearLayout(this)
        val customTag = EditText(this)
        val customNumber = EditText(this)

        customTag.hint = "Name"
        customNumber.hint = "Custom Number"

        customTag.setSingleLine()
        customNumber.setSingleLine()

        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(customTag)
        mLayout.addView(customNumber)

        builder.setView(mLayout)

        if (customNumber.text != null) {

            customTag.setText(addressTag)
            customNumber.setText(contactAddressCustom.text)
        }

        if (customNumber.text != null && customTag != null) {

            builder.setPositiveButton("Save") { dialogInterface, i ->
                contactAddressCustom.text = customNumber.text.toString().trim()
                addressTag = customTag.text.toString().trim()
            }
        } else {
            Toast.makeText(this, "Text is Missing", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.create().show()*/
    }

    fun updateContact(view: View) {

        /*  val contentValue = ContentValues()

            if (contactName.text!!.isNotEmpty() && contactMobileNumber.text.isNotEmpty()) {
                contentValue.put(CONTACTS_NAME, contactName.text.toString().trim())
                contentValue.put(PHONE_MOBILE, contactMobileNumber.text.toString().trim())
            } else {
                Toast.makeText(this, "Name and Mobile Fields are Empty", Toast.LENGTH_SHORT).show()
                updateContactDetails.isEnabled = false
            }

            if (contactImage.drawable != null) {
                contentValue.put(CONTACTS_IMAGE, imageUriData)
            }

            if (contactWorkNumber.text.isNotEmpty()) {
                contentValue.put(PHONE_WORK, contactWorkNumber.text.toString().trim())
            } else {
                contentValue.put(PHONE_WORK, contactWorkNumber.text.toString().trim())
            }

            if (contactCustomNumber.text.isNotEmpty()) {
                contentValue.put(PHONE_CUSTOM_TaG, contactTag.trim())
                contentValue.put(PHONE_CUSTOM, contactCustomNumber.text.toString().trim())
            } else {
                contentValue.put(PHONE_CUSTOM_TaG, contactTag.trim())
                contentValue.put(PHONE_CUSTOM, contactCustomNumber.text.toString().trim())
            }

            *//*Address*//*

            if (contactAddressHome.text.isNotEmpty()) {
                contentValue.put(ADDRESS_HOME, contactAddressHome.text.toString().trim())
            } else {
                contentValue.put(ADDRESS_HOME, contactAddressHome.text.toString().trim())
            }

            if (contactAddressWork.text.isNotEmpty()) {
                contentValue.put(ADDRESS_WORK, contactAddressWork.text.toString().trim())
            } else {
                contentValue.put(ADDRESS_WORK, contactAddressWork.text.toString().trim())
            }

            if (contactAddressCustom.text.isNotEmpty()) {
                contentValue.put(ADDRESS_TAG, addressTag.trim())
                contentValue.put(ADDRESS_CUSTOM, contactAddressCustom.text.toString().trim())
            } else {
                contentValue.put(ADDRESS_TAG, addressTag.trim())
                contentValue.put(ADDRESS_CUSTOM, contactAddressCustom.text.toString().trim())
            }

            *//*Email*//*

            if (contactWorkEmail.text.isNotEmpty()) {
                contentValue.put(EMAIL_WORK, contactWorkEmail.text.toString().trim())
            } else {
                contentValue.put(EMAIL_WORK, contactWorkEmail.text.toString().trim())
            }

            if (contactHomeEmail.text.isNotEmpty()) {
                contentValue.put(EMAIL_HOME, contactHomeEmail.text.toString().trim())
            } else {
                contentValue.put(EMAIL_HOME, contactHomeEmail.text.toString().trim())
            }

            if (contactCustomEmail.text.isNotEmpty()) {
                contentValue.put(EMAIL_TAG, mailTag.trim())
                contentValue.put(EMAIL_CUSTOM, contactCustomEmail.text.toString().trim())
            } else {
                contentValue.put(EMAIL_TAG, mailTag.trim())
                contentValue.put(EMAIL_CUSTOM, contactCustomEmail.text.toString().trim())
            }

            *//*Org*//*

            if (contactOrganization.text.isNotEmpty()) {
                contentValue.put(ORGANIZATION_HOME, contactOrganization.text.toString().trim())
            } else {
                contentValue.put(ORGANIZATION_HOME, contactOrganization.text.toString().trim())
            }

            val whereClause = "$CONTACTS_ID = $contactPersonId"

            val isSuccessful = contentResolver.update(uri, contentValue, whereClause, null)

            if (isSuccessful > 0) {
                Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ContactActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
            }
        }
*/
    }

 }