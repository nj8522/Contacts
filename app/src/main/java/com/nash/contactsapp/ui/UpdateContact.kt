package com.nash.contactsapp.ui

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nash.contactsapp.R
import com.nash.contactsapp.provider.ContactProvider
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class UpdateContact : AppCompatActivity() {

    private lateinit var contactImage: ImageButton

    private lateinit var contactName: TextView

    private lateinit var contactMobileNumber: TextView
    private lateinit var contactWorkNumber: TextView
    private lateinit var contactCustomNumber: TextView

    private lateinit var contactWorkEmail: TextView
    private lateinit var contactHomeEmail: TextView

    private lateinit var contactOrganization: TextView

    //Id
    private var contactPersonId : Int? = 0

    //Tag Name
    var customTagName : String = ""


    //Uri
    private val uri = ContactProvider.CONTENT_URI

    //Data
    private val CONTACTS_ID = "_ID"
    private val CONTACTS_NAME = "CONTACT_NAME"
    private val CONTACTS_IMAGE = "CONTACT_PHOTO"
    private val PHONE_MOBILE = "PHONE_MOBILE"
    private val PHONE_WORK = "PHONE_WORK"
    private val PHONE_CUSTOM = "PHONE_CUSTOM"
    private val PHONE_CUSTOM_TAG = "TAG"
    private val EMAIL_HOME = "EMAIL_HOME"
    private val EMAIL_WORK = "EMAIL_WORK"
    private val ORGANIZATION_HOME = "ORGANIZATION_HOME"


    //Gallery Code
    val GALLERY = 101

    //Image Uri
    var imageUriData : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)


        contactImage = findViewById(R.id.contact_update_image)
        contactName = findViewById(R.id.contact_update_name)

        //Mobile
        contactMobileNumber = findViewById(R.id.mobileNumber)
        contactWorkNumber = findViewById(R.id.workNumber)
        contactCustomNumber = findViewById(R.id.customNumber)

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
        val customNumTag = intent.extras?.getString("customNum")
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
            customTagName = customNumTag.toString()
            contactCustomNumber.text = phoneHome
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


    fun pickImage(view: View) {

       val imageIntent = Intent()
       imageIntent.type = "image/*"
       imageIntent.action = Intent.ACTION_GET_CONTENT
       startActivityForResult(Intent.createChooser(imageIntent, "Pick Image"), GALLERY)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GALLERY && resultCode == RESULT_OK && data != null) {

            val imageData = data.data
            contactImage.setImageURI(imageData)


            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageData)
            val cacheDir = this.cacheDir
            val tempFile = File(cacheDir.path+"/contactApp$contactPersonId.png")

            try {

                val fileOutPutStream = FileOutputStream(tempFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutPutStream)
                fileOutPutStream.flush()
                fileOutPutStream.close()
                
            }catch (e : Exception) {
                e.printStackTrace()
            }

            imageUriData = tempFile.path

        }
    }

    fun setCustomNumber(view: View) {

        val builder = AlertDialog.Builder(this)

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

        if(customNumber.text != null){

            customTag.setText(customTagName)
            customNumber.setText(contactCustomNumber.text)
        }

        if(customNumber.text != null && customTag != null) {

            builder.setPositiveButton("Save") {dialogInterface, i ->
                contactCustomNumber.text = customNumber.text.toString().trim()
                customTagName = customTag.text.toString().trim()
            }
        } else {
            Toast.makeText(this, "Text is Missing", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") {dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.create().show()

    }

    fun updateContact(view: View) {

        val contentValue = ContentValues()

        if (contactName.text.isNotEmpty() && contactMobileNumber.text.isNotEmpty()) {
            contentValue.put(CONTACTS_NAME, contactName.text.toString().trim())
            contentValue.put(PHONE_MOBILE, contactMobileNumber.text.toString().trim())
        } else {
            Toast.makeText(this, "Name and Mobile Fields are Empty", Toast.LENGTH_SHORT).show()
        }


        if(contactImage.drawable != null) {
            contentValue.put(CONTACTS_IMAGE, imageUriData)
        }


        if(contactWorkNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_WORK, contactWorkNumber.text.toString().trim())
        }

        if(contactCustomNumber.text.isNotEmpty()) {
            contentValue.put(PHONE_CUSTOM_TAG, customTagName.trim())
            contentValue.put(PHONE_CUSTOM, contactCustomNumber.text.toString().trim())
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
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }

}