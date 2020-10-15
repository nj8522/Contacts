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
import com.nash.contactsapp.validate.ValidateContact
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class AddNewContact : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var contactImage: AppCompatImageButton

    private lateinit var contactName: TextInputEditText

    private lateinit var contactOrganization: TextInputEditText

    private lateinit var contactMobileNumber: TextInputEditText
    private lateinit var contactPhoneTag : AutoCompleteTextView

    private lateinit var contactAddressHome : TextInputEditText
    private lateinit var contactAddressTag : AutoCompleteTextView

    private lateinit var contactEmailHome: TextInputEditText
    private lateinit var contactEmailTag : AutoCompleteTextView


    private val labels = arrayOf("Mobile", "Work", "Home","Main")

    var currentTag : String = ""

    //AppBar
    private lateinit var topAppBar : MaterialToolbar


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

    private lateinit var validateContact : ValidateContact


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)


        //Image
        contactImage = findViewById(R.id.contact_select_image)

        //Name
        contactName = findViewById(R.id.add_contact_name)

        //Organization
        contactOrganization = findViewById(R.id.add_contact_organization)

        //Contact Numbers
        contactMobileNumber = findViewById(R.id.add_contact_phone)
        contactPhoneTag = findViewById(R.id.phone_auto_complete)

        //Address
        contactAddressHome = findViewById(R.id.add_contact_address)
        contactAddressTag = findViewById(R.id.address_auto_complete)

        //Email
        contactEmailHome = findViewById(R.id.add_contact_email)
        contactEmailTag = findViewById(R.id.email_auto_complete)


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

        contactPhoneTag.onItemClickListener = this


        validateContact = ValidateContact()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
         R.id.save_contact -> addNewContact()
         R.id.action_settings -> Toast.makeText(this, "Help and feedback", Toast.LENGTH_SHORT).show()
         else -> finish()
        }

        return super.onOptionsItemSelected(item)
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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentTag = parent?.getItemAtPosition(position).toString()
    }

    private fun addNewContact() {

        val contentValue = ContentValues()

        if (imageUriData != null || imageUriData != "") {
            contentValue.put(CONTACTS_IMAGE, imageUriData)
        }

        /*Name and Number*/
        if (validateContact.checkContactName(contactName.text.toString())) {
            contentValue.put(CONTACTS_NAME, contactName.text.toString().trim())
        }

        if (validateContact.checkContactPhoneNumber(contactMobileNumber.text.toString())) {
            if (validateContact.checkContactLabel(currentTag)) {

                contentValue.put(PHONE_MOBILE, contactName.text.toString().trim())
                contentValue.put(PHONE_CUSTOM_TaG, currentTag)
            }

        }

        /*Contact Address*/
        if(validateContact.checkUserAddress(contactAddressHome.text.toString())) {
            contentValue.put(ADDRESS_HOME, contactAddressHome.text.toString().trim())
        }


        /*Contact Email*/
        if(validateContact.checkContactEmail(contactEmailHome.text.toString())) {
            contentValue.put(EMAIL_HOME, contactEmailHome.text.toString())
        }


        /*Contact Organization*/

        if (validateContact.checkContactOrganization(contactOrganization.text.toString())) {
            contentValue.put(ORGANIZATION_HOME, contactOrganization.text.toString().trim())
        }


        /*Save Button*/
        if (validateContact.checkContactName(contactName.text.toString())) {
              if(validateContact.checkContactPhoneNumber(contactMobileNumber.text.toString()) &&
                      validateContact.checkContactLabel(currentTag)){
                  contentResolver.insert(uri, contentValue)
                  Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show()
                  startActivity(Intent(this, ContactActivity::class.java))
                  finish()
              }
              else {
                  Toast.makeText(this, "Please fill in Contact Number", Toast.LENGTH_SHORT).show()
              }
          }
          else {

            Toast.makeText(this, "Please fill in Contact Name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

 }
