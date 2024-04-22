package com.example.teamproject9_contact

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.teamproject9_contact.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUi()
    }

    private fun setUi(){
        setSupportActionBar(binding.tbTop)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }

        with(binding) {
            vpMain.adapter = MainViewPagerAdapter(this@MainActivity)
            vpMain.isUserInputEnabled = false

            TabLayoutMediator(tabLayMain, vpMain) { tab, position ->
                when(position){
                    0->{tab.text = "연락처 목록"}
                    1->{tab.text = "마이 페이지"}
                }
            }.attach()
            tabLayMain.tabTextColors = ContextCompat.getColorStateList(this@MainActivity, R.color.tab_select_colors)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.toolbar_recyclerView ->{}
            R.id.toolbar_gridView -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}