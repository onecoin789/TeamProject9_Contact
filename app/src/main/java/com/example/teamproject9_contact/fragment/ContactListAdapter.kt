package com.example.teamproject9_contact.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.R
import com.example.teamproject9_contact.databinding.LayoutContactListDefBinding

class ContactListAdapter(private val contactList: MutableList<Contact>) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    interface Click {
        fun clicked(view: View, position: Int)
    }

    var click: Click? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutContactListDefBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 2 == 1) holder.setBg()

        holder.layout.setOnClickListener {
            click?.clicked(it, position)
        }
        holder.bind(contactList[position])

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ViewHolder(private val binding: LayoutContactListDefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.layoutContact

        fun setBg() {
            binding.layout.setBackgroundColor(
                ContextCompat.getColor(
                    binding.layout.context,
                    R.color.petal_light
                )
            )
        }

        fun bind(info: Contact) {
            binding.ivProfileImg.setImageResource(info.imgResource)
            binding.tvName.text = info.name
            binding.tvPhoneNum.text = info.phoneNum

            val bookMark = binding.ivBookmark
            bookMark.isSelected = info.bookmark
            bookMark.setOnClickListener {
                bookMark.isSelected = bookMark.isSelected != true
            }

        }

    }
}