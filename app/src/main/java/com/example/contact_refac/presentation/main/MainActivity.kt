package com.example.contact_refac.presentation.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.contact_refac.R
import com.example.contact_refac.data.Contact
import com.example.contact_refac.data.ContactList
import com.example.contact_refac.data.EditProfileListener
import com.example.contact_refac.databinding.ActivityMainBinding
import com.example.contact_refac.presentation.main.fragment.addDialog.AddDialog
import com.example.contact_refac.presentation.main.fragment.contactList.ContactListFragment
import com.example.contact_refac.presentation.main.fragment.detailPage.DetailPageFragment
import com.google.android.material.tabs.TabLayoutMediator

private const val READ_CONTACTS_REQUEST_CODE = 101
class MainActivity : AppCompatActivity(), EditProfileListener {
    private val binding: ActivityMainBinding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUi()
        addContact()
    }

    private fun addContact() {
        binding.fbMain.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val addDialog = AddDialog()
            addDialog.show(fragmentManager, "AddDialog")
        }

    }

    private fun setUi() {
        setToolbarUi()
        setViewPagerTabLayoutUi()
    }

    private fun setToolbarUi() {
        setSupportActionBar(binding.tbTop)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }


        binding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.tbTop.menu.clear()
                        binding.tbTop.inflateMenu(R.menu.toolbar_main)
                        binding.fbMain.visibility = View.VISIBLE
                    }

                    1 -> {
                        binding.tbTop.menu.clear()
                        binding.tbTop.inflateMenu(R.menu.toolbar_mypage)
                        binding.fbMain.visibility = View.INVISIBLE
                    }
                }
            }
        })
    }

    private fun loadContact() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 사용자에게 권한 요청 대화상자를 표시합니다.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                READ_CONTACTS_REQUEST_CODE
            )
        } else {
            // 이미 권한이 허용되어 있습니다. 연락처 데이터를 읽어올 수 있습니다.
            val intent = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            loadContactLauncher.launch(intent)
        }
    }

    private fun setViewPagerTabLayoutUi() {
        with(binding) {
            vpMain.adapter = MainViewPagerAdapter(this@MainActivity)
            vpMain.isUserInputEnabled = false

            TabLayoutMediator(tabLayMain, vpMain) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "연락처 목록"
                    }
                    1 -> {
                        tab.text = "마이 페이지"
                    }
                }
            }.attach()
            tabLayMain.tabTextColors =
                ContextCompat.getColorStateList(this@MainActivity, R.color.tab_select_colors)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_recyclerView -> {
                ((binding.vpMain.adapter as MainViewPagerAdapter).fragments[0] as ContactListFragment).switchRecyclerView()
            }
            R.id.toolbar_gridView -> {
                ((binding.vpMain.adapter as MainViewPagerAdapter).fragments[0] as ContactListFragment).switchGridView()
            }
            R.id.toolbar_loadContact -> {
                loadContact()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun notifyDataChanged(data: Contact?) {
        data?.let{
            ((binding.vpMain.adapter as MainViewPagerAdapter).fragments[1] as DetailPageFragment).getMyContact()
        }
    }

    override fun onRestart() {
        super.onRestart()
        ((binding.vpMain.adapter as MainViewPagerAdapter).fragments[0] as ContactListFragment).getList()
    }

    private val loadContactLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val cursor = contentResolver.query(
                    it.data!!.data!!,
                    arrayOf(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    ),
                    null,
                    null,
                    null
                )

                cursor?.use { // Cursor를 사용하여 연락처 데이터를 반복합니다.
                    val nameColumnIndex =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val numberColumnIndex =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    if (cursor.moveToFirst()) {
                        val name = cursor.getString(nameColumnIndex)
                        val number = cursor.getString(numberColumnIndex)
                        val contact = Contact(
                            name = name,
                            phoneNum = number,
                            email = "",
                            imgResource = "",
                            bookmark = false,
                            isUri = false
                        )
                        ContactList.list.add(contact)
                    }
                }
            }
        }
}