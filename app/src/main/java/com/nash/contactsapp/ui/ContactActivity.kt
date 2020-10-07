package com.nash.contactsapp.ui

import android.Manifest.permission.READ_CONTACTS
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.nash.contactsapp.R
import com.nash.contactsapp.adapter.ContactListViewHelper
import com.nash.contactsapp.contactdata.ConvertContactToObjects
import com.nash.contactsapp.contactdata.RetrieveContactData
import com.nash.contactsapp.database.ContactAppContract
import com.nash.contactsapp.database.DataFromProvider
import com.nash.contactsapp.model.ContactModel
import com.nash.contactsapp.provider.ContactProvider


class ContactActivity : AppCompatActivity() {

    private val CONTACT_PERMISSION = 2

    private val CONTACT_URI = ContactProvider.CONTENT_URI

    private var viewAdapter: ContactListViewHelper? = null

    private var contactNameList: MutableList<ContactModel> = mutableListOf()

    private lateinit var recyclerView: RecyclerView

    private lateinit var retrieveContactData: RetrieveContactData

    private lateinit var dataFromProvider: DataFromProvider

    private lateinit var convertContactToObjects: ConvertContactToObjects

    private lateinit var searchBar : MaterialToolbar

    //private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)


        retrieveContactData = RetrieveContactData()
        dataFromProvider = DataFromProvider()
        convertContactToObjects = ConvertContactToObjects()



        //progressBar = (R.id.progressBar)findViewById

        searchBar = findViewById(R.id.home_search_bar)
        setSupportActionBar(searchBar)

        recyclerView = findViewById(R.id.contact_name_list)

        //Check Permission
        checkForContactPermission()

        //Update Adapter
        updateAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    @SuppressLint("ObsoleteSdkInt")
    private fun checkForContactPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), CONTACT_PERMISSION)

            } else {
                dataFromContentProvider()
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            if (!shouldShowRequestPermissionRationale(permissions[0])) {

                val settingsIntent = Intent()
                settingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", this.packageName, null)
                settingsIntent.data = uri
                this.startActivity(settingsIntent)

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                createPermissionAlertMessage()
            }

        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            dataFromContentProvider()
        }
    }

    private fun createPermissionAlertMessage() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Without Granting  permission to access contact. This app cannot be accessed")
        builder.setPositiveButton("Allow Permission") { dialogInterface, which -> checkForContactPermission() }

        builder.setNegativeButton("No") { dialogInterface, which ->
            finish()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun updateAdapter() {

        if (viewAdapter == null) {

            viewAdapter = ContactListViewHelper(contactNameList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = viewAdapter
        } else {
            viewAdapter!!.notifyDataSetChanged()
        }
    }

    private fun dataFromContentProvider() {

        val cursor = contentResolver.query(
            CONTACT_URI,
            null,
            null,
            null,
            ContactAppContract.ContactAppEntry.CONTACTS_NAME
        )

        if (cursor!!.count > 0) {

            //contactNameList = convertContactToObjects.changeDataToModelObjects(cursor)

            //progressBar.visibility = View.INVISIBLE

            if (cursor.moveToFirst()) {

                do {

                    val contactModel = ContactModel()
                    val contactId =
                        cursor.getInt(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.CONTACTS_ID))

                    //ID
                    contactModel.id = contactId


                    contactModel.displayName =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.CONTACTS_NAME))

                    if (cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.CONTACTS_IMAGE)) != null) {

                        contactModel.contactImage =
                            cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.CONTACTS_IMAGE))
                    }

                    //Mobile
                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_MOBILE)) != null) {
                        contactModel.phoneNumber["Mobile"] =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_MOBILE))
                        Log.i("data", contactModel.phoneNumber["Mobile"].toString())
                    }

                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_WORK)) != null) {
                        contactModel.phoneNumber["Work"] =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_WORK))
                        Log.i("data", contactModel.phoneNumber["Work"].toString())
                    }

                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_CUSTOM)) != null) {
                        contactModel.phoneNumber["Home"] =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_CUSTOM))
                        contactModel.customPhoneTag =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.PHONE_CUSTOM_TaG))

                    }

                    //Address

                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_HOME)) != null) {
                        contactModel.address["Home"] =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_HOME))
                    }

                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_WORK)) != null) {
                        contactModel.address["Work"] =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_WORK))
                    }

                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_CUSTOM)) != null) {
                        contactModel.customAddressTag =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_TAG))
                        contactModel.address["Custom"] =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ADDRESS_CUSTOM))
                    }


                    //Email
                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.EMAIL_WORK)) != null) {
                        contactModel.emailId["Work"] =
                            cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.EMAIL_WORK))
                    }

                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.EMAIL_HOME)) != null) {
                        contactModel.emailId["Home"] =
                            cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.EMAIL_HOME))
                    }

                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.EMAIL_CUSTOM)) != null) {
                        contactModel.customEmailTag =
                            cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.EMAIL_TAG))
                        contactModel.emailId["Custom"] =
                            cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.EMAIL_CUSTOM))
                    }


                    //Org
                    if (cursor.getString(cursor.getColumnIndex(ContactAppContract.ContactAppEntry.ORGANIZATION_HOME)) != null) {
                        contactModel.organization =
                            cursor.getString(cursor.getColumnIndexOrThrow(ContactAppContract.ContactAppEntry.ORGANIZATION_HOME))
                    }


                    contactNameList.add(contactModel)

                } while (cursor.moveToNext())
            }


        } else {

            //progressBar.visibility = View.VISIBLE
            val dataList = retrieveContactData.getContactDetails(this)
            dataFromProvider.convertObjectToData(this, dataList)
            dataFromContentProvider()
        }

        updateAdapter()
    }

    fun insertNewContactFab(view: View) {
        startActivity(Intent(this, AddNewContact::class.java))
    }


 }