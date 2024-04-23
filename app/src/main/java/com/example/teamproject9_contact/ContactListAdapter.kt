package com.example.teamproject9_contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject9_contact.databinding.LayoutContactListBgBinding
import com.example.teamproject9_contact.databinding.LayoutContactListDefBinding
import java.lang.RuntimeException

class ContactListAdapter(private val contactList: MutableList<DataClass>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Click {
        fun clicked(view: View, position: Int)
    }

    var click: Click? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingDef =
            LayoutContactListDefBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingBg =
            LayoutContactListBgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when {
            viewType % 2 == 0 -> ViewHolderDef(bindingDef)
            viewType % 2 == 1 -> ViewHolderBg(bindingBg)
            else -> throw RuntimeException("알 수 없는 뷰 타입")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when {
            position % 2 == 0 -> {
                (holder as ViewHolderDef).layout.setOnClickListener {
                    click?.clicked(it, position)
                }
//                holder.bind(contactList[position])
            }
            position % 2 == 1 -> {
                (holder as ViewHolderBg).layout.setOnClickListener {
                    click?.clicked(it, position)
                }
//                holder.bind(contactList[position])
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return 10 //contactList.size
    }

    inner class ViewHolderDef(binding: LayoutContactListDefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.layoutContact
        fun bind(info: DataClass) {
//            val priceContext = binding.tvPrice.context
//            binding.ivProductImg.setImageResource(info.image)
        }
    }

    inner class ViewHolderBg(binding: LayoutContactListBgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.layoutContact
        fun bind(info: DataClass) {
//            val priceContext = binding.tvPrice.context
//            binding.ivProductImg.setImageResource(info.image)
        }
    }

}