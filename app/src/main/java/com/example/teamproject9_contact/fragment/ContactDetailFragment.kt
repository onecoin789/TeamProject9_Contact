package com.example.teamproject9_contact.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment() {
    //    private val myContact: Contact? = null
    private var param1: Contact? = null


    lateinit var binding: FragmentContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
//            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailBinding.inflate(inflater)


//        데이터 키값넣기
        arguments?.let { binding.imgDetailTitle.setImageResource(param1!!.imgResource) }
        binding.textDetailName.text = param1?.name
        binding.textDetailPhoneNumber.text = param1?.phoneNum
        binding.textDetailEmail.text = param1?.email

//        binding.btnDetail.setOnClickListener {
//
//        }

        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: Contact) =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
//                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}