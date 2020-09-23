package com.nash.contactsapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nash.contactsapp.R
import com.nash.contactsapp.model.AddressDetail
import com.nash.contactsapp.model.ContactModel
import com.nash.contactsapp.ui.ContactDetailActivity
import kotlinx.android.synthetic.main.contact_list_names.view.*
import java.io.Serializable

class ContactListViewHelper(var contactList: MutableList<ContactModel>) :
    RecyclerView.Adapter<ContactListViewHelper.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_names, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contactList = contactList
        val contactModel: ContactModel = contactList[position]
        holder.displayName.text = contactModel.displayName

        //Id
        holder.id = contactModel.id
        Log.i("id", holder.id.toString())

        //Name
        holder.name = contactModel.displayName

        //Numbers
        holder.phoneNumber = contactModel.phoneNumber as HashMap
        holder.customPhoneTag = contactModel.customPhoneTag

        //Address
        holder.address = contactModel.address as HashMap
        holder.addressTag = contactModel.customAddressTag

        //Email
        holder.emailId = contactModel.emailId as HashMap
        holder.emailTag = contactModel.customEmailTag


        //Organization
        holder.organization = contactModel.organization

        //Image
        holder.contactImage = contactModel.contactImage.toString()


    }

    override fun getItemCount() = contactList.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val displayName = itemView.contact_display_names

        var id: Int = 0

        var contactImage: String = ""

        var name: String? = ""

        var customPhoneTag: String? = ""
        var phoneNumber: HashMap<String, String> = hashMapOf()

        var address: HashMap<String, String> = hashMapOf()
        var addressTag: String? = ""

        var emailId: HashMap<String, String> = hashMapOf()
        var emailTag: String? = ""

        var organization: String? = null


        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {

            val intent = Intent(v?.context, ContactDetailActivity::class.java).apply {

                putExtra("id", id)
                putExtra("name", name)
                putExtra("number", phoneNumber as Serializable)
                putExtra("customNum", customPhoneTag)
                putExtra("address", address)
                putExtra("customAdd", addressTag)
                putExtra("email", emailId as Serializable)
                putExtra("customMail", emailTag)
                putExtra("org", organization)
                putExtra("img", contactImage)
            }

            v!!.context.startActivities(arrayOf(intent))

        }
    }


}


