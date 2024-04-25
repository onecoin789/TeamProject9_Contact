package com.example.teamproject9_contact.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.data.ContactList
import com.example.teamproject9_contact.databinding.FragmentContactDetailBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ContactDetailFragment : Fragment() {

    private var param1: Contact? = null
    private var param2: Int? = null


    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater)


        initView()


        return binding.root
    }

    private fun initView() = with(binding) {
        textDetailName.text = param1?.name
        textDetailPhoneNumber.text = param1?.phoneNum
        textDetailEmail.text = param1?.email

        if (param1!!.isUri) {
            val uri = Uri.parse(param1!!.imgResource)
            binding.imgDetailTitle.setImageURI(uri)
        } else {
            binding.imgDetailTitle.setImageResource(param1!!.imgResource.toInt())
        }

        imgDetailBookmark.isSelected = ContactList.list[param2!!].bookmark
        imgDetailBookmark.setOnClickListener {
            imgDetailBookmark.isSelected = imgDetailBookmark.isSelected != true
            ContactList.list[param2!!].bookmark = ContactList.list[param2!!].bookmark != true
        }

        binding.fbDetailCall.setOnClickListener {
            val phoneNum = textDetailPhoneNumber.text
            val call = Uri.parse("tel:${phoneNum}")
            startActivity(Intent(Intent.ACTION_DIAL, call))
        }

        binding.fbDetailSend.setOnClickListener {
            val phoneNum = textDetailPhoneNumber.text
            val send = Uri.parse("smsto:${phoneNum}")
            startActivity(Intent(Intent.ACTION_SENDTO, send))
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: Contact, param2: Int) =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}