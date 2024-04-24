package com.example.teamproject9_contact.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.databinding.LayoutContactListDefBinding
import java.lang.RuntimeException

class ContactListAdapter(private val contactList: MutableList<Contact>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Click {
        fun clicked(view: View, position: Int)
    }

    var click: Click? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingDef =
            LayoutContactListDefBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingBg =
            LayoutContactListDefBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when {
            viewType % 2 == 0 -> ViewHolderDef(bindingDef)
            viewType % 2 == 1 -> ViewHolderBg(bindingDef)
            else -> throw RuntimeException("알 수 없는 뷰 타입")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolderDef) {
            holder.layout.setOnClickListener {
                click?.clicked(it, position)
            }
            holder.bind(contactList[position])
        } else if (holder is ViewHolderBg) {
            holder.layout.setOnClickListener {
                click?.clicked(it, position)
            }
            holder.bind(contactList[position])
        } else {
            throw RuntimeException("알 수 없는 뷰 타입")
        }

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

    inner class ViewHolderDef(private val binding: LayoutContactListDefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.layoutContact
        fun bind(info: Contact) {
            binding.tvName.text = info.name
            binding.tvAddress.text = info.phoneNum
            val bookMark = binding.ivLike
            binding.ivProfileImg.setImageResource(info.imgResource)
        }
    }

    inner class ViewHolderBg(private val binding: LayoutContactListDefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.layoutContact
        fun bind(info: Contact) {
            binding.tvName.text = info.name
            binding.tvAddress.text = info.phoneNum
            val bookMark = binding.ivLike
            binding.ivProfileImg.setImageResource(info.imgResource)
        }
    }

}