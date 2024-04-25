package com.example.contact_refac.presentation.main.fragment.addDialog

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import androidx.fragment.app.DialogFragment
import com.example.contact_refac.data.Contact
import com.example.contact_refac.data.ContactList
import com.example.contact_refac.databinding.DialogAddContactBinding

class AddDialog : DialogFragment() {
    private val data = ContactList.list
    private val binding: DialogAddContactBinding by lazy {
        DialogAddContactBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var pictureLauncher: ActivityResultLauncher<Intent>

    private var uri: Uri? = null

    private fun addTask() {
        val imageUri = uri
        val name = binding.etName.text.toString()
        val phone = convertToFormattedPhoneNumber(binding.etNumber.text.toString())
        val email = binding.etEmail.text.toString()

        ContactList.list.add(
            Contact(
                name = name,
                phoneNum = phone,
                email = email,
                imgResource = imageUri?.toString() ?: "",
                bookmark = false,
                isUri = true
            )
        )

    }
    private fun convertToFormattedPhoneNumber(number: String): String {
        return if (number.length == 11) {
            val formattedNumber = StringBuilder(number)
            formattedNumber.insert(3, "-")
            formattedNumber.insert(8, "-")
            formattedNumber.toString()
        } else {
            number
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        pictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                    uri = it.data!!.data!!
                    if (uri != null) {
                        val inputStream = requireContext().contentResolver.openInputStream(uri!!)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.ivProfile.setImageBitmap(bitmap)
                    }
                }
            }

        var nameCheck: Boolean? = null
        var emailCheck: Boolean? = null
        var numberCheck: Boolean? = null

        binding?.apply {
            // 이미지 추가
            ivProfile.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pictureLauncher.launch(intent)
            }

            // 이름 필드 유효성 검사
            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (etName.length() >= 2) {
                        etName.error = null
                        nameCheck = true
                    } else {
                        etName.error = "이름을 2글자 이상 입력하세요"
                        nameCheck = false
                    }
                }
            })

            // 이메일 필드 유효성 검사
            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val email =
                        Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

                    if (email.matches(etEmail.text) || etEmail.text.isEmpty()) {
                        etEmail.error = null
                        emailCheck = true
                    } else {
                        etEmail.error = "올바른 이메일 형식으로 입력하세요"
                        emailCheck = false
                    }
                }
            })

            // 전화번호 필드 유효성 검사
            etNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val phoneNumber = Regex("^[0-9]{11}\$")
                    val phoneNumber2 = Regex("^[0-9]{3}-[0-9]{4}-[0-9]{4}\$")

                    if (phoneNumber.matches(etNumber.text) || phoneNumber2.matches(etNumber.text)) {
                        etNumber.error = null
                        numberCheck = true
                    } else {
                        etNumber.error = "올바른 전화번호 형식으로 입력하세요"
                        numberCheck = false
                    }
                }
            })

            // 확인 버튼 클릭
            btnDialogConfirm.setOnClickListener {
                if (nameCheck == false || emailCheck == false || numberCheck == false) {
                    Toast.makeText(context, "입력창을 모두 올바른 형식으로 작성하세요", Toast.LENGTH_SHORT).show()
                } else {
                    addTask()
                    dismiss()
                }
            }

            // 취소 버튼 클릭
            btnDialogCancel.setOnClickListener {
                dismiss()
            }
        }
        return binding.root
    }

}