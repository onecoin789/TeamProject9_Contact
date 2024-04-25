package com.example.contact_refac.presentation.main.fragment.detailPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contact_refac.R
import com.example.contact_refac.data.Contact
import com.example.contact_refac.data.ContactList
import com.example.contact_refac.databinding.FragmentDetailPageBinding
import com.example.contact_refac.presentation.main.fragment.editDialog.EditDialog

private const val ARG_PARAM = "Contact"

class DetailPageFragment : Fragment() {
    private lateinit var binding : FragmentDetailPageBinding
    private var data: Contact? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable(ARG_PARAM)
        }
    }
    fun getMyContact(){
        data= ContactList.myContact
        notifyDataChanged(data)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifyDataChanged(data)
    }

    private fun notifyDataChanged(data: Contact?){
        data?.let {
            with(binding){
                tvMyName.text = it.name
                tvMyNumber.text = it.phoneNum
                tvMyEmail.text = it.email

                if (it.isUri) {
                    val uri = Uri.parse(it.imgResource)
                    ivMyImg.setImageURI(uri)
                } else if (it.imgResource.isNotBlank()) {
                    ivMyImg.setImageResource(it.imgResource.toInt())
                } else {
                    ivMyImg.setImageResource(R.drawable.ic_user)
                }
                if(it != ContactList.myContact) {
                    fbDetailCall.visibility = View.VISIBLE
                    fbDetailSend.visibility = View.VISIBLE
                    ivBookmark.visibility = View.VISIBLE

                    val position = ContactList.list.indexOf(it)
                    if(ContactList.list[position].bookmark) ivBookmark.setImageResource(R.drawable.ic_contactfrag_star_fill)
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

                val phoneNum = it.phoneNum
                fbDetailCall.setOnClickListener {
                    val call = Uri.parse("tel:${phoneNum}")
                    root.context.startActivity(Intent(Intent.ACTION_DIAL, call))
                }
                fbDetailSend.setOnClickListener {
                    val call = Uri.parse("smsto:${phoneNum}")
                    root.context.startActivity(Intent(Intent.ACTION_SENDTO, call))
                }
                fbDetailEdit.setOnClickListener {
                    val fragmentManager = parentFragmentManager
                    val dialog = EditDialog(data!!)
                    dialog.show(fragmentManager, "editDialog")
                }

            }
        }


    }
    companion object{
        fun newInstance(data: Contact)= DetailPageFragment().apply{
            arguments = Bundle().apply {
                putParcelable(ARG_PARAM, data)
            }
        }
    }

}