package com.example.teamproject9_contact

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.teamproject9_contact.databinding.DialogLayoutBinding
import kotlin.ClassCastException

class AddDialog: DialogFragment() {
    private lateinit var listener: AddDialogListener
    private var _binding: DialogLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var pictureLauncher: ActivityResultLauncher<Intent>

    interface AddDialogListener {
        fun onContactAdd(contact: Contact)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AddDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context 오류")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLayoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        pictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK && it.data != null) {
                    val uri = it.data!!.data
                    val inputStream = requireContext().contentResolver.openInputStream(uri!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.dialogImg.setImageBitmap(bitmap)
                }
            }

        _binding?.apply {
            // 이미지 추가
            dialogImg.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pictureLauncher.launch(intent)
            }

            // 이름 필드 유효성 검사
            dialogName.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val english = Regex("^[a-zA-Z]{2,}\$")
                    val korean = Regex("^[가-힣]{2,}\$")

                    if(english.matches(dialogName.text) || korean.matches(dialogName.text)) {
                        dialogName.error = null
                    }
                    else {
                        dialogName.error = "이름을 2글자 이상 입력하세요"
                    }
                }
            })

            // 이메일 필드 유효성 검사
            dialogEmail.addTextChangedListener(object : TextWatcher {
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
                    val email = Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

                    if(email.matches(dialogEmail.text)) {
                        dialogEmail.error = null
                    }
                    else {
                        dialogEmail.error = "올바른 이메일 형식으로 입력하세요"
                    }
                }
            })

            // 전화번호 필드 유효성 검사
            dialogPhoneNumber.addTextChangedListener(object : TextWatcher{
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

                    if(phoneNumber.matches(dialogPhoneNumber.text)) {
                        dialogPhoneNumber.error = null
                    }
                    else {
                        dialogPhoneNumber.error = "올바른 전화번호 형식으로 입력하세요"
                    }
                }
            })

            // 확인 버튼 클릭
            dialogConfirmButton.setOnClickListener {
                val name = dialogName.text.toString()
                val phone = dialogPhoneNumber.text.toString()
                val email = dialogEmail.text.toString()
                val imgResource = R.drawable.dialog_circle

                val contact = Contact(name, phone, email, imgResource, false)
                listener.onContactAdd(contact)
                dismiss()
            }

            // 취소 버튼 클릭
            dialogCancelButton.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }
}