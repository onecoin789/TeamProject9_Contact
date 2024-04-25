package com.example.contact_refac.presentation.main.fragment.contactList

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_refac.R
import com.example.contact_refac.data.Contact
import com.example.contact_refac.data.ContactList
import com.example.contact_refac.databinding.ItemContactListBinding
import com.example.contact_refac.presentation.detail.DetailActivity

class ContactListRecyclerViewAdapter :
    ListAdapter<Contact, ContactListRecyclerViewAdapter.ViewHolder>(
        ContactListRecyclerViewAdapterCallback
    ) {
    object ContactListRecyclerViewAdapterCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.phoneNum == newItem.phoneNum
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

    private lateinit var binding: ItemContactListBinding

    class ViewHolder(private val binding: ItemContactListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Contact, position: Int) = with(binding) {
            if (data.isUri) {
                val uri = Uri.parse(data.imgResource)
                ivProfileImg.setImageURI(uri)
            } else if (data.imgResource.trim().isNotEmpty()) {
                ivProfileImg.setImageResource(data.imgResource.toInt())
            } else {
                ivProfileImg.setImageResource(R.drawable.ic_user)
            }

            tvName.text = data.name
            tvPhoneNum.text = data.phoneNum

            setBookmark(data, position)
            itemView.background.setBounds(0, 0, 0, 0)
            when(position%2) {
                0-> clParent.setBackgroundColor(Color.WHITE)
                1-> clParent.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.petal))
            }

            clParent.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("Contact", data.copy(
                    bookmark = ContactList.list[position].bookmark
                ))
                binding.root.context.startActivity(intent)
            }
        }

        private fun setBookmark(data: Contact, position: Int) = with(binding) {
            if (data.bookmark) {
                ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_fill)
            }

            ivBookmark.setOnClickListener {
                if (ContactList.list[position].bookmark) { // 북마크 되어 있을 경우
                    ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_str)
                    ContactList.list[position] = ContactList.list[position].copy(
                        bookmark = false
                    )
                } else {
                    ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_fill)
                    ContactList.list[position] = ContactList.list[position].copy(
                        bookmark = true
                    )
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemContactListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }
}