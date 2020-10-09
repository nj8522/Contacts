package com.nash.contactsapp.ui

import android.Manifest.permission.READ_CONTACTS
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
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
import com.nash.contactsapp.contracts.ContactActivityContract
import com.nash.contactsapp.models.RetrieveContactDataFromProvider
import com.nash.contactsapp.database.ContactAppContract
import com.nash.contactsapp.database.DataFromProvider
import com.nash.contactsapp.presenters.ContactActivityPresenter
import com.nash.contactsapp.uidatamodel.ContactModel
import com.nash.contactsapp.provider.ContactProvider


class ContactActivity : AppCompatActivity(), ContactActivityContract.ContactView {

    private val CONTACT_PERMISSION = 2

    private val CONTACT_URI = ContactProvider.CONTENT_URI

    private var viewAdapter: ContactListViewHelper? = null

    private var contactNameList: MutableList<ContactModel> = mutableListOf()

    private lateinit var recyclerView: RecyclerView

    private lateinit var searchBar : MaterialToolbar

    //Contact Presenter
    private var contactActivityPresenter : ContactActivityPresenter? = null

    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)


        progressBar = findViewById(R.id.progressBar)

        searchBar = findViewById(R.id.home_search_bar)
        setSupportActionBar(searchBar)

        recyclerView = findViewById(R.id.contact_name_list)

        checkForContactPermission()

        //Update Adapter
        updateAdapter()


    }

    override fun onStart() {
        super.onStart()
        contactActivityPresenter = ContactActivityPresenter(this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkForContactPermission() {

        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestReadContactPermission()

            } else {

                 Toast.makeText(this, "You Have given the Permission", Toast.LENGTH_SHORT).show()
            }
    }

    private fun requestReadContactPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_CONTACTS)) {
            createPermissionAlertMessage()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), CONTACT_PERMISSION)
        }
    }

    private fun createPermissionAlertMessage() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("App Does't not work without Granting Permission")
        builder.setPositiveButton("Allow"){dialog, which ->
            ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), CONTACT_PERMISSION)
        }

        builder.setNegativeButton("No"){dialog , which ->

            takeUserToSettingsIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CONTACT_PERMISSION) {

            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun takeUserToSettingsIntent() {

        val settingsIntent = Intent()
        settingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        settingsIntent.data = Uri.fromParts("package", this.packageName, null)
        this.startActivity(settingsIntent)

    }

    override fun initView() {

        progressBar.visibility = View.VISIBLE

        if(dataFromContentProvider()) {
            Log.i("mvp", "True")
            updateView()
        } else {

            Log.i("mvp", "false")
            contactActivityPresenter?.getProviderData()
        }
    }

    override fun updateView() {

        val list = contactActivityPresenter?.updateContact()
        contactNameList = list!!
        progressBar.visibility = View.GONE
        updateAdapter()
        Log.i("mvp", "updated")
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

    private fun dataFromContentProvider() : Boolean {

        val cursor = contentResolver.query(
            CONTACT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor!!.count > 0) { return true }
        return false
    }

    fun insertNewContactFab(view: View) {
        startActivity(Intent(this, AddNewContact::class.java))
    }

}

