package com.nash.contactsapp.ui

import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nash.contactsapp.R
import com.nash.contactsapp.adapter.ContactListViewHelper
import com.nash.contactsapp.contactdata.RetrieveContactData
import com.nash.contactsapp.model.ContactModel


class MainActivity : AppCompatActivity() {

    private val CONTACT_PERMISSION = 2

    var contactNameList : MutableList<ContactModel> = mutableListOf()

    lateinit var recyclerView: RecyclerView
    var viewAdapter : ContactListViewHelper? = null

    lateinit var retrieveContactData: RetrieveContactData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         retrieveContactData = RetrieveContactData()

        recyclerView = findViewById(R.id.contact_name_list)
        checkForContactPermission()
        updateAdapter()
    }


    private fun checkForContactPermission() {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){

            if(ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), CONTACT_PERMISSION)

            } else {
                dataFromContentProvider()
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){

            if(!shouldShowRequestPermissionRationale(permissions[0])){
                Toast.makeText(this, "Go to Settings and Apply Permission", Toast.LENGTH_SHORT).show()
                finish()
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
        builder.setPositiveButton("Allow Permission"){ dialogInterface, which -> checkForContactPermission()}

        builder.setNegativeButton("No"){ dialogInterface, which ->
           finish()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun updateAdapter() {

        if(viewAdapter == null) {

            viewAdapter = ContactListViewHelper(contactNameList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = viewAdapter
        } else {
            viewAdapter!!.notifyDataSetChanged()
        }
    }

    private fun dataFromContentProvider() {

         contactNameList = retrieveContactData.getContactDetails(this)
         updateAdapter()
    }


}