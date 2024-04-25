package com.example.contact_refac.presentation.main.fragment.contactList

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import com.example.contact_refac.data.Contact
import com.example.contact_refac.databinding.ItemGridContactListBinding
import com.example.contact_refac.presentation.detail.DetailActivity


class ContactListGridViewAdapter(private val contactList: List<Contact>) : BaseAdapter() {
    override fun getCount(): Int {
        return contactList.size
    }

    override fun getItem(position: Int): Contact {
        return contactList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding: ItemGridContactListBinding

        if (convertView == null) {
            binding = ItemGridContactListBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemGridContactListBinding
        }

        with(binding) {
            val data = contactList[position]
            binding.tvName1.text = data.name
            if (data.isUri) {
                val uri = Uri.parse(data.imgResource)
                ivProfile1.setImageURI(uri)
            } else if (data.imgResource != "") {
                ivProfile1.setImageResource(data.imgResource.toInt())
            } else {
                ivProfile1.setImageResource(com.example.contact_refac.R.drawable.ic_user)
            }

            ivProfile1.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("Contact", data)
                binding.root.context.startActivity(intent)
            }
        }




        return binding.root
    }
}
