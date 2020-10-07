package com.nash.contactsapp.ui

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.nash.contactsapp.R
import com.nash.contactsapp.provider.ContactProvider
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class AddNewContact : AppCompatActivity() {

    private lateinit var contactImage: AppCompatImageButton

    private lateinit var contactName: TextInputEditText

    private lateinit var contactMobileNumber: TextInputEditText
    private lateinit var contactPhoneTag : AutoCompleteTextView



    /* private lateinit var contactWorkNumber: TextView
     private lateinit var contactCustomNumber: TextView*/

    private lateinit var contactAddressHome : TextInputEditText
    private lateinit var contactAddressTag : AutoCompleteTextView


   /* private lateinit var contactAddressWork : TextView
    private lateinit var contactCustomAddress : TextView*/


    private lateinit var contactEmailHome: TextInputEditText
    private lateinit var contactEmailTag : AutoCompleteTextView


    /*private lateinit var contactEmailWork : TextView
    private lateinit var contactCustomEmail: TextView*/

    private lateinit var contactOrganization: TextInputEditText


    private val labels = arrayOf("Mobile", "Work", "Home","Main")


    //AppBar
    private lateinit var topAppBar : MaterialToolbar


    //Tag
   /* private var contactTag : String? = ""
    private var addressTag : String? = ""
    private var emailTag : String? = ""*/


    //Data
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

    //Uri
    private val uri = ContactProvider.CONTENT_URI

    //Result Code
    val GALLERY = 103

    //Image Data
    var imageUriData: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)


        //Image
        contactImage = findViewById(R.id.contact_select_image)

        //Name
        contactName = findViewById(R.id.add_contact_name)

        //Contact Numbers
        contactMobileNumber = findViewById(R.id.add_contact_phone)
        contactPhoneTag = findViewById(R.id.phone_auto_complete)

        /*contactWorkNumber = findViewById(R.id.new_workNumber)
        contactCustomNumber = findViewById(R.id.new_customNumber)*/

        //Address
        contactAddressHome = findViewById(R.id.add_contact_address)
        contactAddressTag = findViewById(R.id.address_auto_complete)

        /*contactAddressWork = findViewById(R.id.new_address_work)
        contactCustomAddress = findViewById(R.id.new_address_custom)*/

        //Email
        contactEmailHome = findViewById(R.id.add_contact_email)
        contactEmailTag = findViewById(R.id.email_auto_complete)

        /*contactEmailWork = findViewById(R.id.new_email_work)
        contactCustomEmail = findViewById(R.id.new_email_custom)*/

        //Organization
        contactOrganization = findViewById(R.id.add_contact_organization)


        //Adapter
        val labelAdapter = ArrayAdapter(this, R.layout.drop_down_menu, labels)
        contactAddressTag.setAdapter(labelAdapter)
        contactPhoneTag.setAdapter(labelAdapter)
        contactEmailTag.setAdapter(labelAdapter)


        //ToolBar
        topAppBar = findViewById(R.id.topAppBar)
        topAppBar.title = "Create contact"
        setSupportActionBar(topAppBar)
        topAppBar.setNavigationIcon(R.drawable.ic_close_18dp)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_contact_menu, menu)
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

    fun setNewCustomNumber(view: View) {

     /*   val builder = AlertDialog.Builder(this)

        val mLayout = LinearLayout(this)
        val customTag = EditText(this)
        val customNumber = EditText(this)

        customTag.hint = "Name Tag"
        customNumber.hint = "Custom Number"

        customTag.setSingleLine()
        customNumber.setSingleLine()

        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(customTag)
        mLayout.addView(customNumber)

        builder.setView(mLayout)

        if(customNumber.text != null){

            customTag.setText(contactTag)
            customNumber.setText(contactCustomNumber.text)
        }


        if(customNumber.text != null && customTag.text != null) {

            builder.setPositiveButton("Save") { dialogInterface, i ->
                contactCustomNumber.text = customNumber.text.toString().trim()
                contactTag = customTag.text.toString().trim()
            }

        } else {
            Toast.makeText(this, "Text is Missing", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") {dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.create().show()*/
    }

    fun setNewCustomAddress(view: View) {

       /* val builder = AlertDialog.Builder(this)

        val mLayout = LinearLayout(this)
        val customTag = EditText(this)
        val customNumber = EditText(this)

        customTag.hint = "Name Tag"
        customNumber.hint = "Custom Address"

        customTag.setSingleLine()
        customNumber.setSingleLine()

        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(customTag)
        mLayout.addView(customNumber)

        builder.setView(mLayout)

        if(customNumber.text != null){

            customTag.setText(addressTag)
            customNumber.setText(contactCustomAddress.text)
        }

        if(customNumber.text != null && customTag != null) {

            builder.setPositiveButton("Save") {dialogInterface, i ->
                contactCustomAddress.text = customNumber.text.toString().trim()
                addressTag = customTag.text.toString().trim()
            }
        } else {
            Toast.makeText(this, "Text is Missing", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") {dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.create().show()*/
    }

    fun setNewCustomEmail(view: View) {

       /* val builder = AlertDialog.Builder(this)

        val mLayout = LinearLayout(this)
        val customTag = EditText(this)
        val customNumber = EditText(this)

        customTag.hint = "Name Tag"
        customNumber.hint = "Custom Email"

        customTag.setSingleLine()
        customNumber.setSingleLine()

        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(customTag)
        mLayout.addView(customNumber)

        builder.setView(mLayout)

        if(customNumber.text != null){

            customTag.setText(emailTag)
            customNumber.setText(contactCustomEmail.text)
        }

        if(customNumber.text != null && customTag != null) {

            builder.setPositiveButton("Save") {dialogInterface, i ->
                contactCustomEmail.text = customNumber.text.toString().trim()
                emailTag = customTag.text.toString().trim()
            }
        } else {
            Toast.makeText(this, "Text is Missing", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") {dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.create().show()*/
    }

    fun insertContactImage(view: View) {

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
            val imageNum = rand(1000, 2000)
            val tempFile = File(cacheDir.path + "/contactApp $imageNum.png")

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

    fun addNewContact(view: View) {

        val contentValue = ContentValues()

        if (imageUriData != null || imageUriData != "") {
            contentValue.put(CONTACTS_IMAGE, imageUriData)
        }

        /*Name and Number*/

        if (contactName.text!!.isNotEmpty() && contactMobileNumber.text!!.isNotEmpty()) {
            contentValue.put(CONTACTS_NAME, contactName.text.toString().trim())
            contentValue.put(PHONE_MOBILE, contactMobileNumber.text.toString().trim())
        } else {
            Toast.makeText(this, "Name and Mobile Fields are Empty", Toast.LENGTH_SHORT).show()
        }

       /* if (contactWorkNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_WORK, contactWorkNumber.text.toString().trim())
        }

        if (contactCustomNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_CUSTOM_TaG, contactTag)
            contentValue.put(PHONE_CUSTOM, contactCustomNumber.text.toString().trim())
        }*/


        /*Contact Address*/

        if(contactAddressHome.text!!.isNotEmpty()) {
            contentValue.put(ADDRESS_HOME, contactAddressHome.text.toString().trim())
        }

       /* if(contactAddressWork.text.isNotEmpty()) {
            contentValue.put(ADDRESS_WORK, contactAddressWork.text.toString().trim())
        }

        if(contactCustomAddress.text.isNotEmpty()) {
            contentValue.put(ADDRESS_TAG, addressTag)
            contentValue.put(ADDRESS_CUSTOM, contactAddressHome.text.toString().trim())
        }*/



        /*Contact Email*/

        if(contactEmailHome.text != null) {
            contentValue.put(EMAIL_HOME, contactEmailHome.text.toString())
        }

        /*if(contactEmailWork.text != null) {
            contentValue.put(EMAIL_WORK, contactEmailWork.text.toString())
        }

        if(contactCustomEmail.text != null) {
            contentValue.put(EMAIL_TAG, emailTag)
            contentValue.put(EMAIL_CUSTOM, contactCustomEmail.text.toString())
        }*/


        /*Contact Organization*/

        if (contactOrganization.text!!.isNotEmpty()) {
            contentValue.put(ORGANIZATION_HOME, contactOrganization.text.toString().trim())
        }


        /*Save Button*/

        if (contactName.text!!.isNotEmpty() && (contactMobileNumber.text!!.isNotEmpty())) {

            val isSuccessful = contentResolver.insert(uri, contentValue)
            Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ContactActivity::class.java))
            finish()
        } else {

            Toast.makeText(this, "Please fill in Contact Name and Number", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }


}