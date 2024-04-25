package com.example.teamproject9_contact

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.teamproject9_contact.databinding.DialogMyPageBinding

class MyPageDialog : DialogFragment() {

    private var _binding: DialogMyPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var pictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogMyPageBinding.inflate(inflater, container, false)
        val view = binding.root

        pictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                    uri = it.data!!.data!!
                    if (uri != null) {
                        val inputStream = requireContext().contentResolver.openInputStream(uri!!)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.ivMyDialog.setImageBitmap(bitmap)
                        binding.ivMyDialog.clipToOutline = true
                    }
                }
            }


        var nameCheck: Boolean? = null
        var emailCheck: Boolean? = null
        var numberCheck: Boolean? = null


        _binding?.apply {
            // 이미지 추가
            ivMyDialog.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pictureLauncher.launch(intent)
            }

            etMyName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val english = Regex("^[a-zA-Z]{2,}\$")
                    val korean = Regex("^[가-힣]{2,}\$")

                    if (english.matches(etMyName.text) || korean.matches(etMyName.text)) {
                        etMyName.error = null
                        nameCheck = true
                    } else {
                        etMyName.error = "이름을 2글자 이상 입력하세요"
                        nameCheck = false
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            etMyEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val email =
                        Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

                    if (email.matches(etMyEmail.text)) {
                        etMyEmail.error = null
                        emailCheck = true
                    } else {
                        etMyEmail.error = "올바른 이메일 형식으로 입력하세요"
                        emailCheck = false
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }


            })
            etMyPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val phoneNumber = Regex("^[0-9]{11}\$")

                    if (phoneNumber.matches(etMyPhoneNumber.text)) {
                        etMyPhoneNumber.error = null
                        numberCheck = true
                    } else {
                        etMyPhoneNumber.error = "올바른 전화번호 형식으로 입력하세요"
                        numberCheck = false
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            btnMyCancel.setOnClickListener {
                dismiss()
            }
            btnMyConfirm.setOnClickListener {
                if (nameCheck == true && emailCheck == true && numberCheck == true) {
                    Toast.makeText(context, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                    addTask()
                    dismiss()
                }
            }
        }
        return view
    }

    private fun addTask() {
        val name = binding.etMyName.text.toString()
        val email = binding.etMyEmail.text.toString()
        val number = binding.etMyPhoneNumber.text.toString()
        val uri = uri

        val bundle1 = bundleOf("name" to name)
        val bundle2 = bundleOf("email" to email)
        val bundle3 = bundleOf("number" to number)
        val bundle4 = bundleOf("image" to uri.toString())

        setFragmentResult("name", bundle1)
        setFragmentResult("number", bundle3)
        setFragmentResult("email", bundle2)
        setFragmentResult("image", bundle4)

        Log.d("to", "${uri}")
    }
}
