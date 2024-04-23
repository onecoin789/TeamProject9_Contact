package com.example.teamproject9_contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamproject9_contact.databinding.FragmentMyPageBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MyPageFragment : Fragment() {

    private var image: Int? = null
    private var name: String? = null
    private var phoneNumber: String? = null
    private var eMail: String? = null



    private var param1: String? = null
    private var param2: String? = null

   lateinit var binding: FragmentMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

//            image = it.getInt("")
//            name = it.getString("")
//            phoneNumber = it.getString("")
//            eMail = it.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentMyPageBinding.inflate(inflater)


//        데이터 입력
//        arguments?.let { binding.imgMyPageTitle.setImageResource(it.getInt("")) }
//        binding.textMyPageName.text = arguments?.getString("")
//        binding.textMyPagePhoneNumber.text = arguments?.getString("")
//        binding.textMyPageEmail.text = arguments?.getString("")

        binding.btnMyPage.setOnClickListener {
            floatingButton()
        }



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

    private fun floatingButton() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"${phoneNumber}"))
        startActivity(intent)
    }
}