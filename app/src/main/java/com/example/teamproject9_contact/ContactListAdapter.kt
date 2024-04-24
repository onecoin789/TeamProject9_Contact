package com.example.teamproject9_contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.RecyclerView
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
            holder.setBg()
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
            binding.ivProfileImg.setImageResource(info.imgResource)
            binding.tvName.setText(info.name)
            binding.tvPhoneNum.setText(info.phoneNum)
            binding.ivBookmark.setOnClickListener {
                if (info.bookmark) {
                    info.copy(bookmark = false)
                    binding.ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_str)
                } else {
                    info.copy(bookmark = true)
                    binding.ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_fill)
                }
            }
        }
    }

    inner class ViewHolderBg(private val binding: LayoutContactListDefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.layoutContact
        fun setBg() {
            binding.layout.setBackgroundColor(ContextCompat.getColor(binding.layout.context, R.color.petal_light))
        }
        fun bind(info: Contact) {
            binding.ivProfileImg.setImageResource(info.imgResource)
            binding.tvName.setText(info.name)
            binding.tvPhoneNum.setText(info.phoneNum)
            binding.ivBookmark.setOnClickListener {
                if (info.bookmark) {
                    info.copy(bookmark = false)
                    binding.ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_str)
                } else {
                    info.copy(bookmark = true)
                    binding.ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_fill)
                }
            }
        }
    }

}