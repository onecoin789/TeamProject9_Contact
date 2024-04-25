package com.example.teamproject9_contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.key
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.teamproject9_contact.databinding.FragmentMyPageBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class MyPageFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(inflater)

        setFragmentResultListener("name") { key, bundle ->
            bundle.getString("name")?.let {
                binding.textMyPageName.text = bundle.getString("name")
            }
        }

        setFragmentResultListener("number") { key, bundle ->
            bundle.getString("number")?.let {
                binding.textMyPagePhoneNumber.text = bundle.getString("number")
            }
        }

        setFragmentResultListener("email") { key, bundle ->
            bundle.getString("email")?.let {
                binding.textMyPageEmail.text = bundle.getString("email")
            }
        }

//        setFragmentResultListener("image") { key, bundle ->
//            val image = bundle.getString("image")
//                binding.imgMyPageTitle.setImageURI(bundle.getString("image"))
//            }


        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}