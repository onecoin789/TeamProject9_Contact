package com.example.teamproject9_contact

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
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
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class AddDialog: DialogFragment() {
    private var _binding: DialogLayoutBinding? = null
    private val data = ContactList.list
    private val adapter = ContactListAdapter(data)
    private val binding get() = _binding!!
    private lateinit var pictureLauncher: ActivityResultLauncher<Intent>

    private lateinit var uri: Uri

    private val handler = Handler(Looper.getMainLooper())
    private val notificationId = 1

    private fun notification(name: String) {
        val notificationManager =
            binding.root.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(binding.root.context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            binding.root.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
                // 알림 권한이 없다면, 사용자에게 권한 요청
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                }
                startActivity(intent)
            }
        }

        val notification = NotificationCompat.Builder(binding.root.context, "channel_id")
            .setContentTitle("어쩌고저쩌고 앱 알림")
            .setContentText("$name 님에게 연락할 시간입니다!")
            .setSmallIcon(R.drawable.call)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun cancelNotification(context: Context, notificationId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addTask() {
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
                    val phoneNumber = Regex("^01[0-9]-[0-9]{4}-[0-9]{4}\$")

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

            // 이벤트 알릶 클릭
            dialogOffBtn.setOnClickListener {
                cancelNotification(requireContext(), notificationId)
                Toast.makeText(context,"설정한 알림을 모두 해제합니다.", Toast.LENGTH_SHORT).show()
            }

            dialog5min.setOnClickListener {
                handler.postDelayed({ notification("5분 뒤 알림") }, 50)
                Toast.makeText(context,"${dialogName.text} 님께 5분 뒤 연락 알림 설정", Toast.LENGTH_SHORT).show()
            }

            dialog10min.setOnClickListener {
                handler.postDelayed({ notification("10분 뒤 알림") }, 10000)
                Toast.makeText(context,"${dialogName.text} 님께 10분 뒤 연락 알림 설정", Toast.LENGTH_SHORT).show()
            }

            dialog30min.setOnClickListener {
                handler.postDelayed({ notification("30분 뒤 알림") }, 30000)
                Toast.makeText(context,"${dialogName.text} 님께 30분 뒤 연락 알림 설정", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

}