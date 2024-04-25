package com.example.teamproject9_contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.viewpager2.widget.ViewPager2
import com.example.teamproject9_contact.databinding.ActivityMainBinding
import com.example.teamproject9_contact.fragment.ContactDetailFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), AddDialog.AddDialogListener, FragmentDataListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUi()
        addContact()
    }

    private fun addContact() {
        binding.ivMain.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val addDialog = AddDialog()
            addDialog.show(fragmentManager, "AddDialog")

            val myDialog = MyPageDialog()
            myDialog.show(fragmentManager, "MyPageDialog")

        }
    }

    private fun setUi() {
        setToolbarUi()
        setViewPagerTabLayoutUi()
        setMultifunctionalButtonUi()

    }

    private fun setToolbarUi() {
        setSupportActionBar(binding.tbTop)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
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

    private fun setMultifunctionalButtonUi() {
        binding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.ivMain.setImageResource(
                    when (position) {
                        0 -> R.drawable.add_user
                        else -> R.drawable.modify
                    }
                )
                binding.ivMain.setOnClickListener {
                    when (position) {
                        0 -> {
                            val dialog = AddDialog()
                            dialog.show(this@MainActivity.supportFragmentManager, "AddDialog")
                        }
                        else -> {
                           val dialog = MyPageDialog()
                            dialog.show(this@MainActivity.supportFragmentManager, "MyPageDialog")

                        }
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_recyclerView -> {}
            R.id.toolbar_gridView -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContactAdd(contact: Contact) {
        // 연락처 추가 시 처리할 부분
    }

    override fun onDataReceived(infoData: Contact) = with(binding) {
//        vpMain.currentItem = 1
//        if (vpMain.adapter is MainViewPagerAdapter)
//            (vpMain.adapter as MainViewPagerAdapter).setFragment(
//                ContactDetailFragment.newInstance(
//                    infoData
//                ), 0
//            )

//    private fun setFragment(fragment: Fragment) {
//        supportFragmentManager.commit {
//            replace(R.id.layout_fragment, fragment)
//            setReorderingAllowed(true)
//            addToBackStack("")
//        }
    }
}