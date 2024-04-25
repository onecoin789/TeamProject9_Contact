package com.example.teamproject9_contact

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.teamproject9_contact.data.ContactList
import com.example.teamproject9_contact.databinding.DialogLayoutBinding
import com.example.teamproject9_contact.fragment.ContactListAdapter
import android.util.Log

class AddDialog: DialogFragment() {
    private var _binding: DialogLayoutBinding? = null
    private val data = ContactList.list
    private val adapter = ContactListAdapter(data)
    private val binding get() = _binding!!
    private lateinit var pictureLauncher: ActivityResultLauncher<Intent>

    private lateinit var uri: Uri

    fun addTask() {
        val imageUri = uri
        val name = binding.dialogName.text.toString()
        val phone = binding.dialogPhoneNumber.text.toString()
        val email = binding.dialogEmail.text.toString()

        val todo = Contact(name, phone, email, imageUri.toString(), false, true)
        data.add(todo)
        Log.d("debug333", data.toString())
        adapter.notifyDataSetChanged()
    }

    interface AddDialogListener {
        fun onContactAdd(contact: Contact)
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
                    uri = it.data!!.data!!
                    if (uri != null) {
                        val inputStream = requireContext().contentResolver.openInputStream(uri!!)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.dialogImg.setImageBitmap(bitmap)
                    }
                }
            }

        var nameCheck: Boolean? = null
        var emailCheck: Boolean? = null
        var numberCheck: Boolean? = null

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
                        nameCheck = true
                    }
                    else {
                        dialogName.error = "이름을 2글자 이상 입력하세요"
                        nameCheck = false
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
                        emailCheck = true
                    }
                    else {
                        dialogEmail.error = "올바른 이메일 형식으로 입력하세요"
                        emailCheck = false
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
                        numberCheck = true
                    }
                    else {
                        dialogPhoneNumber.error = "올바른 전화번호 형식으로 입력하세요"
                        numberCheck = false
                    }
                }
            })

            // 확인 버튼 클릭
            dialogConfirmButton.setOnClickListener {
                if (nameCheck == false || emailCheck == false || numberCheck == false) {
                    Toast.makeText(context, "입력창을 모두 올바른 형식으로 작성하세요", Toast.LENGTH_SHORT).show()
                } else {
                    addTask()
                    dismiss()
                }
            }

            // 취소 버튼 클릭
            dialogCancelButton.setOnClickListener {
                dismiss()
            }
        }
        return binding.root
    }

}