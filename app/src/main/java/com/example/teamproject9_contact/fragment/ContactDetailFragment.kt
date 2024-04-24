package com.example.teamproject9_contact.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.R
import com.example.teamproject9_contact.databinding.FragmentContactDetailBinding

private const val ARG_PARAM1 = "param1"
class ContactDetailFragment : Fragment() {

    private var param1: Contact? = null


    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)

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
        imgDetailTitle.setImageResource(param1!!.imgResource)
        textDetailName.text = param1?.name
        textDetailPhoneNumber.text = param1?.phoneNum
        textDetailEmail.text = param1?.email


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
        fun newInstance(param1: Contact) =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}