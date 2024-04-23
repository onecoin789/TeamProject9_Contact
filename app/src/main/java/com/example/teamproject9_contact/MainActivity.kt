package com.example.teamproject9_contact

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.core.content.ContextCompat
import com.example.teamproject9_contact.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity(), AddDialog.AddDialogListener, FragmentDataListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUi()
//        setFragment(ContactListFragment())
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
                binding.ivBtnMultifunctional.setImageResource(
                    when (position) {
                        0 -> R.drawable.ic_main_person
                        else -> R.drawable.ic_main_phone
                    }
                )
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
    
    override fun onContactAdd(contact: Contacts) {
        // 연락처 추가 시 처리할 부분
    }

    override fun onDataReceived(infoData: DataClass) {
        TODO("Not yet implemented")
    }

//    private fun setFragment(fragment: Fragment) {
//        supportFragmentManager.commit {
//            replace(R.id.layout_fragment, fragment)
//            setReorderingAllowed(true)
//            addToBackStack("")
//        }
//    }

}