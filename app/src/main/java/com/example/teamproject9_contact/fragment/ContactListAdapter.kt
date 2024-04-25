package com.example.teamproject9_contact.fragment

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.R
import com.example.teamproject9_contact.data.ContactList
import com.example.teamproject9_contact.databinding.LayoutContactListDefBinding

class ContactListAdapter(private val contactList: MutableList<Contact>) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {


    interface Click {
        fun clicked(view: View, position: Int)
    }

    var click: Click? = null
    private lateinit var binding: LayoutContactListDefBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutContactListDefBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.binding = binding
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 2 == 1) holder.setBg()

        holder.layout.setOnClickListener {
            click?.clicked(it, position)
        }
        holder.bind(position)
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
            binding.layoutContact.setBackgroundColor(
                ContextCompat.getColor(
                    binding.layoutContact.context,
                    R.color.petal_light)
            )
        }

        fun bind(position: Int) {
            val contact = contactList[position]

            if(contact.isUri) {
                val uri = Uri.parse(contact.imgResource)
                binding.ivProfileImg.setImageURI(uri)
            } else {
                binding.ivProfileImg.setImageResource(contact.imgResource.toInt())
            }
            binding.tvName.text = contact.name
            binding.tvPhoneNum.text = contact.phoneNum
            binding.ivProfileImg.clipToOutline = true

            val bookMark = binding.ivBookmark
            bookMark.isSelected = ContactList.list[position].bookmark
            bookMark.setOnClickListener {
                bookMark.isSelected = bookMark.isSelected != true
                ContactList.list[position].bookmark = ContactList.list[position].bookmark != true
            }

        }

    }

    fun call(){
        val phoneNum = binding.tvPhoneNum.text
        val call = Uri.parse("tel:${phoneNum}")
        binding.root.context.startActivity(Intent(Intent.ACTION_DIAL, call))
    }

    fun message(){
        val phoneNum = binding.tvPhoneNum.text
        val call = Uri.parse("smsto:${phoneNum}")
        binding.root.context.startActivity(Intent(Intent.ACTION_SENDTO, call))
    }
}