package com.nash.contactsapp.ui

import android.Manifest.permission.READ_CONTACTS
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.nash.contactsapp.contracts.ContactActivityContract
import com.nash.contactsapp.presenters.ContactActivityPresenter
import com.nash.contactsapp.uidatamodel.ContactModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


class ContactActivity : AppCompatActivity() {

    private val CONTACT_PERMISSION = 2

    private var viewAdapter: ContactListViewHelper? = null

    private var contactNameList: MutableList<ContactModel> = mutableListOf()

    private lateinit var recyclerView: RecyclerView

    private lateinit var searchBar : MaterialToolbar

    //Contact Presenter
    private var contactActivityPresenter : ContactActivityPresenter? = null

    private lateinit var progressBar: ProgressBar

    //Coroutines
    private val scope = CoroutineScope(IO)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)


        progressBar = findViewById(R.id.progressBar)

        searchBar = findViewById(R.id.home_search_bar)
        setSupportActionBar(searchBar)

        recyclerView = findViewById(R.id.contact_name_list)

        checkForContactPermission()


        scope.launch {

            contactActivityPresenter = ContactActivityPresenter(this@ContactActivity)
            val isDataExists = contactActivityPresenter?.checkIfDbIsEmpty()
            if(!isDataExists!!){
                contactActivityPresenter?.getProviderData()
            }
            val dataList = contactActivityPresenter?.updateContact()

            try {
                withContext(Main) {
                    contactNameList = dataList!!
                    Log.i("mvp", "converting to Main")
                    progressBar.visibility = View.GONE
                    updateAdapter()
                }

            } catch (e: Exception) {
                Log.i("error", e.toString())
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkForContactPermission() {

        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            progressBar.visibility = View.VISIBLE
            requestReadContactPermission()

        }
    }

    private fun requestReadContactPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_CONTACTS)) {

            try {
                AlertDialog.Builder(this)
                    .setTitle("Requires Permission")
                    .setMessage("This App needs Permission")
                    .setPositiveButton("Allow", DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), CONTACT_PERMISSION)
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                        takeUserToSettingsIntent()
                    })
                    .create().show()
            } catch (e : Exception){
                Log.i("error", e.toString())
            }


        } else {
            ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), CONTACT_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CONTACT_PERMISSION) {

            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }

            if(!shouldShowRequestPermissionRationale(permissions[0])){
                takeUserToSettingsIntent()
            }

            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                takeUserToSettingsIntent()
            }
        }
    }

    private fun takeUserToSettingsIntent() {

        val settingsIntent = Intent()
        settingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        settingsIntent.data = Uri.fromParts("package", this.packageName, null)
        this.startActivity(settingsIntent)

    }

    private fun updateAdapter() {

        if (viewAdapter == null) {

            viewAdapter = ContactListViewHelper(contactNameList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = viewAdapter
        } else {
            viewAdapter!!.notifyDataSetChanged()
        }
    }

    fun insertNewContactFab(view: View) {
        startActivity(Intent(this, AddNewContact::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        contactActivityPresenter = null
    }
}

