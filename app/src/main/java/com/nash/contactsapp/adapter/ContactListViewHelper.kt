package com.nash.contactsapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nash.contactsapp.R
import com.nash.contactsapp.model.ContactModel
import com.nash.contactsapp.ui.ContactDetailActivity
import kotlinx.android.synthetic.main.contact_list_names.view.*
import java.io.Serializable

class ContactListViewHelper(var contactList : MutableList<ContactModel>) : RecyclerView.Adapter<ContactListViewHelper.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_names, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contactList = contactList
        val contactModel : ContactModel = contactList[position]
        holder.displayName.text = contactModel.displayName

        //Id
        holder.id  = contactModel.id
        Log.i("id", holder.id.toString())

        //Name
        holder.name = contactModel.displayName!!

        //Numbers
        holder.phoneNumber = contactModel.phoneNumber as HashMap

        //Email
        holder.emailId = contactModel.emailId as HashMap

        //Organization
        holder.organization = contactModel.organization

        //Image
        holder.contactImage = contactModel.contactImage.toString()

    }

    override fun getItemCount() = contactList.size


    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val displayName = itemView.contact_display_names

        var id : Int  = 0
        var name : String = ""
        var phoneNumber : HashMap<String, String> = hashMapOf()
        var emailId : HashMap<String, String> = hashMapOf()
        var organization : String? = null

        var contactImage : String = ""

        init {
            itemView.setOnClickListener(this)
        }



        override fun onClick(v: View?) {

            val intent = Intent(v?.context, ContactDetailActivity :: class.java).apply {

                /*val bundle = Bundle()
                bundle.putString("name", name)
                bundle.putString("number", primaryNumber)
                bundle.putString("email", emailId)
                bundle.putString("org", organization)
                bundle.putString("image", contactImage)
                putExtras(bundle)
*/
                    putExtra("id", id)
                    putExtra("name", name)
                    putExtra("number", phoneNumber as Serializable)
                    putExtra("email", emailId as Serializable)
                    putExtra("org", organization)
                    putExtra("img", contactImage)

            }
            v!!.context.startActivities(arrayOf(intent))

        }


    }


}


