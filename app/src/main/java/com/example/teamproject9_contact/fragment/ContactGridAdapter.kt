package com.example.teamproject9_contact.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.R

class ContactGridAdapter(private val contactList: MutableList<Contact>) : BaseAdapter() {

    interface Click {
        fun clicked(view: View, position: Int)
    }

    var click: Click? = null

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?) : View {
        val contact: Contact = contactList[position]
        val inflater = parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contactView = inflater.inflate(R.layout.layout_contact_list_grid, null)

        contactView.findViewById<ImageView>(R.id.iv_profile_img).setImageResource(contact.imgResource)
        contactView.findViewById<TextView>(R.id.tv_name).text = contact.name
        val bookMark = contactView.findViewById<ImageView>(R.id.iv_bookmark)
        bookMark.isSelected = contact.bookmark
        bookMark.setOnClickListener {
            bookMark.isSelected = bookMark.isSelected != true
        }

        contactView.setOnClickListener {
            click?.clicked(it, position)
        }

        return contactView
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return contactList.size
    }

}